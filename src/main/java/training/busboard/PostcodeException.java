package training.busboard;

import javax.ws.rs.NotFoundException;

public class PostcodeException extends NotFoundException {
    public PostcodeException(String s) {
        super(s);
    }
}