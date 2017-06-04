package challenge.service;
import challenge.bean.PopularFollower;
import challenge.bean.Response;
import challenge.bean.User;
import challenge.bean.Message;
import java.util.List;
/**
 * Created by pyadav on 6/1/17.
 */
public interface MessagingService {
    public List<User> findMyFollowers(Long id);
    public List<User> findMyFriends(Long id);
    public List<Message> printMyTimeLine(Long id, String keyword);
    public List<User> followUser(Long followerId, Long personId);
    public List<User> unFollowUser(Long followerId, Long personId);
    public List<PopularFollower> popularFollower();
    public Response sendMessage(Long id, String content);
}