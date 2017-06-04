package challenge.bean;

/**
 * Created by pyadav on 6/4/17.
 */
public class PopularFollower {
    private Long id;
    private String handle;
    private String name;
    private Long followerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getFollowerHandle() {
        return followerHandle;
    }

    public void setFollowerHandle(String followerHandle) {
        this.followerHandle = followerHandle;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    private String followerHandle;
    private String followerName;



}
