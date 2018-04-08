package tutoapp.com.tutoappstudent.Objects;

/**
 * Created by Juan on 21/03/2018.
 */

public class Request {
    String usr_id;
    String type_request;
    public String getUsr_id() {
        return usr_id;
    }

    public String getType_request() {
        return type_request;
    }

    public Request(String usr_id, String type_request) {
        this.usr_id = usr_id;
        this.type_request = type_request;
    }
}
