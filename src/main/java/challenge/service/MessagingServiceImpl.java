package challenge.service;
import challenge.service.MessagingService;
import challenge.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by pyadav on 6/1/17.
 */
@Service
public class MessagingServiceImpl implements MessagingService {
    @Autowired
    private JdbcTemplate jtm;

    public List<User> findMyFollowers(Long id) {

        String sql = "select p.id, p.handle, p.name " +
                "from people p join followers f " +
                "on p.id = f.follower_person_id and f.person_id = ?";

        return jtm.query(sql, new Object[] { (Object) id}, new ResultSetExtractor<List<User>>(){
            @Override
            public List<User> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                List<User> list=new ArrayList<User>();
                while(rs.next()){
                    User user = new User();
                    user.setId(rs.getLong(1));
                    user.setHandle(rs.getString(2));
                    user.setName(rs.getString(3));
                    list.add(user);
                }
                return list;
            }
        });
    }

    public List<User> findMyFriends(Long id) {
        String sql = "select p.id, p.handle, p.name " +
                "from people p join followers f " +
                "on p.id = f.person_id and f.follower_person_id= ?";

        return jtm.query(sql, new Object[] { (Object) id}, new ResultSetExtractor<List<User>>(){
            @Override
            public List<User> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {

                List<User> list=new ArrayList<User>();
                while(rs.next()){
                    User user = new User();
                    user.setId(rs.getLong(1));
                    user.setHandle(rs.getString(2));
                    user.setName(rs.getString(3));
                    list.add(user);
                }
                return list;
            }
        });
    }

    public List<Message> printMyTimeLine(Long id, String keyword) {
        String sql = (keyword == null ) ?
                "(select p.id, p.handle, p.name, m.content " +
                "from people p join messages m " +
                "on p.id = m.person_id AND m.person_id = ? "+
                "order by p.id)"+
                "UNION " +
                "(select p.id, p.handle, p.name, m.content " +
                "from people p JOIN messages m " +
                "on p.id = m.person_id AND m.person_id in (" +
                "select p1.id from people p1 join followers f " +
                "on p1.id = f.person_id and f.follower_person_id = ? "+ " ) "+
                "order by p.id)"
                :
                "(select p.id, p.handle, p.name, m.content " +
                "from people p join messages m " +
                "on p.id = m.person_id AND m.person_id=?"+
                " AND m.content LIKE '%"+ keyword +"%' order by p.id) "+
                "UNION " +
                "(select p.id, p.handle, p.name, m.content " +
                "from people p JOIN messages m " +
                "on p.id = m.person_id " +
                "AND m.content LIKE '%"+ keyword +"%' "+
                "AND m.person_id in (" +
                "select p1.id from people p1 join followers f " +
                "on p1.id = f.person_id and f.follower_person_id=?"+") "+
                "order by p.id)";

        return jtm.query(sql, new Object[] { (Object) id, (Object) id}, new ResultSetExtractor<List<Message>>(){
            @Override
            public List<Message> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {

                List<Message> list=new ArrayList<Message>();
                while(rs.next()){
                    Message message = new Message();
                    User user = new User();
                    user.setId(rs.getLong(1));
                    user.setHandle(rs.getString(2));
                    user.setName(rs.getString(3));
                    message.setContent(rs.getString(4));
                    message.setUser(user);
                    list.add(message);
                }
                return list;
            }
        });
    }


    public List<User> followUser(Long followerId, Long personId) {
        String sql = "INSERT INTO followers(person_id, follower_person_id) " +
                "VALUES (?, ?) ";
        try {
            jtm.update(sql, new Object[]{(Object) personId, (Object)followerId});
        }
        catch (Exception e) {
            return null;
        }
        return findMyFriends(followerId);
    }

    public List<User> unFollowUser(Long followerId, Long personId) {
        try {
            jtm.update("DELETE FROM followers WHERE person_id =? AND follower_person_id = ?",
                    new Object[] { (Object) personId, (Object)followerId });

        }
        catch (Exception e) {
            return null;
        }
        return findMyFriends(followerId);
    }

    public List<PopularFollower> popularFollower() {
        String sql = "select t.person_id, t.handle, t.name, " +
                "t.popular_id, p2.HANDLE, p2.NAME " +
                "from ( " +
                "SELECT person_id , p.HANDLE, p.NAME, " +
                "( SELECT self.follower_person_id " +
                "FROM followers self " +
                "WHERE self.person_id = f.person_id " +
                "GROUP BY self.follower_person_id " +
                "ORDER BY COUNT( self.follower_person_id ) DESC " +
                "LIMIT 1" +
                ") as popular_id " +
                "FROM followers f " +
                "JOIN PEOPLE p " +
                "ON p.ID = PERSON_ID " +
                "GROUP BY person_id" +
                ") as t " +
                "join PEOPLE p2 " +
                "on p2.ID = t.popular_id";

        return jtm.query(sql, new ResultSetExtractor<List<PopularFollower>>(){
            @Override
            public List<PopularFollower> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {

                List<PopularFollower> list=new ArrayList<PopularFollower>();
                while(rs.next()){
                    PopularFollower popularFollowerObj = new PopularFollower();
                    popularFollowerObj.setId(rs.getLong(1));
                    popularFollowerObj.setHandle(rs.getString(2));
                    popularFollowerObj.setName(rs.getString(3));
                    popularFollowerObj.setFollowerId(rs.getLong(4));
                    popularFollowerObj.setFollowerHandle(rs.getString(5));
                    popularFollowerObj.setFollowerName(rs.getString(6));
                    list.add(popularFollowerObj);
                }
                return list;
            }
        });
    }

    public Response sendMessage(Long id, String content) {
        String sql = "INSERT INTO MESSAGES (PERSON_ID, CONTENT) " +
                "VALUES (?, ?) ";
        try {
            jtm.update(sql, new Object[]{(Object) id, (Object)content});
            Response success = new Response();
            success.setMessage("Message posted to your timeline");
            success.setHttpCode("200");
            return success;
        }
        catch (Exception e) {
            //e.getMessage();
            Response error = new Response();
            error.setMessage("Error while posting the message");
            error.setHttpCode("500");
            return error;
        }
    }
}
