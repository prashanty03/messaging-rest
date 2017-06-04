package challenge.service;

import challenge.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by pyadav on 6/3/17.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jtm;

    /**
     * Method to fectch User based on the handle
     * @param handle
     * @return
     */
    public User findUserByHandle(String handle) {
        String sql = "select p.id, p.handle, p.name " +
                "from people p join users u " +
                "on p.handle = u.username and p.handle like ? ";

        return jtm.query(sql,new Object[] { (Object) handle}, new ResultSetExtractor<User>(){
            @Override
            public User extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
               User user = new User();
                while(rs.next()){
                    user.setId(rs.getLong(1));
                    user.setHandle(rs.getString(2));
                    user.setName(rs.getString(3));
                }
                return user;
            }
        });
    }


    /**
     * Method to fectch the User based on Id
     * @param id
     * @return
     */
    public User findUserById(Long id) {
        String sql = "select ID, HANDLE, NAME " +
                "from PEOPLE " +
                "WHERE ID = ? ";

        return jtm.query(sql,new Object[] { (Object) id}, new ResultSetExtractor<User>(){
            @Override
            public User extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                User user = new User();
                while(rs.next()){
                    user.setId(rs.getLong(1));
                    user.setHandle(rs.getString(2));
                    user.setName(rs.getString(3));
                }
                return user;
            }
        });
    }
}
