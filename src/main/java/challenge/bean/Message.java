package challenge.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by pyadav on 6/1/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Message {
    private Long id;
    private String content;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
