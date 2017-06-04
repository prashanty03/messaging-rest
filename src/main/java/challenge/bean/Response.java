package challenge.bean;

/**
 * Created by pyadav on 6/4/17.
 */
public class Response {
    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String httpCode;
    private String message;

}
