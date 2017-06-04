package challenge;

/**
 * Created by pyadav on 6/1/17.
 */

import java.util.List;
import challenge.bean.Response;
import challenge.bean.PopularFollower;
import challenge.bean.User;
import challenge.bean.Message;
import challenge.constant.ApplicationConstant;
import challenge.service.MessagingService;
import challenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class MessagingController {
    @Autowired
    private MessagingService messagingService;
    @Autowired
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/followers")
    public ResponseEntity<String> findFollowers() throws JsonProcessingException {
        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            List<User> users = messagingService.findMyFollowers(loggedInUser.getId());
            return new ResponseEntity<>(objectMapper.writeValueAsString(users), HttpStatus.OK);
        }
        catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public ResponseEntity<String> findFriends() throws JsonProcessingException {
        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            List<User> users = messagingService.findMyFriends(loggedInUser.getId());
            return new ResponseEntity<>(objectMapper.writeValueAsString(users), HttpStatus.OK);
        }
        catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/friends", method = RequestMethod.POST)
    public  ResponseEntity<String> followUser(@RequestBody String personJson) throws JsonProcessingException {
        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            User personToFollow = new ObjectMapper().readValue(personJson, User.class);
            if(personToFollow.getId()==null)
                throw new Exception(ApplicationConstant.NO_SUCH_USER);
            else {
                User user = userService.findUserById(personToFollow.getId());
                if(user.getId() == null)
                    throw new Exception(ApplicationConstant.NO_SUCH_USER);
                else {
                    List<User> users = messagingService.followUser(loggedInUser.getId(), personToFollow.getId());
                    return new ResponseEntity<>(objectMapper.writeValueAsString(users), HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/friends", method = RequestMethod.DELETE)
    public  ResponseEntity<String> unFollowUser(@RequestBody String personJson) throws JsonProcessingException {
        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            User personToUnFollow = new ObjectMapper().readValue(personJson, User.class);
            if(personToUnFollow.getId()==null)
                throw new Exception(ApplicationConstant.NO_SUCH_USER);
            else {
                User user = userService.findUserById(personToUnFollow.getId());
                if(user.getId() == null)
                    throw new Exception(ApplicationConstant.NO_SUCH_USER);
                else {
                    List<User> users = messagingService.unFollowUser(loggedInUser.getId(), personToUnFollow.getId());
                    return new ResponseEntity<>(objectMapper.writeValueAsString(users), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public  ResponseEntity<String> sendMessage(@RequestBody String messageJson) throws JsonProcessingException {
        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            Message message = new ObjectMapper().readValue(messageJson, Message.class);
            if(message.getContent()==null || "".equalsIgnoreCase(message.getContent()))
                throw new Exception(ApplicationConstant.INVALID_MESSAGE);
            Response response = messagingService.sendMessage(loggedInUser.getId(), message.getContent());
            if("500".equalsIgnoreCase(response.getHttpCode()))
                throw new Exception(response.getMessage());
            else
                return new ResponseEntity<>(objectMapper.writeValueAsString(response), HttpStatus.OK);
        } catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/timeline")
    public ResponseEntity<String> myTimeLine(@RequestParam(value="search", required=false) String keyword) throws JsonProcessingException {

        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            List<Message> messageList = messagingService.printMyTimeLine(loggedInUser.getId(), keyword);
            return new ResponseEntity<>(objectMapper.writeValueAsString(messageList), HttpStatus.OK);
        }
        catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/popularfollower")
    public ResponseEntity<String> popularFollowerList() throws JsonProcessingException {
        try {
            User loggedInUser = getAuthenticatedUser();
            if(loggedInUser == null)
                throw new Exception(ApplicationConstant.INVALID_SESSION);
            List<PopularFollower> popularFollower = messagingService.popularFollower();
            return new ResponseEntity<>(objectMapper.writeValueAsString(popularFollower), HttpStatus.OK);
        }
        catch (Exception e) {
            printStackTrace(e.getMessage());
            Response error = new Response();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(objectMapper.writeValueAsString(error), HttpStatus.BAD_REQUEST);
        }
    }


    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findUserByHandle(name);
        return user;
    }

    private void printStackTrace(String msg) {
        System.out.println(msg);
    }

}
