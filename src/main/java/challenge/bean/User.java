package challenge.bean;

import challenge.bean.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
/**
 * Created by pyadav on 6/1/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Long id;
    private String handle;
    private String name;

    public User() {
    }

    public User(Long id, String handle, String name) {
        this.id = id;
        this.handle = handle;
        this.name = name;
    }

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




}
