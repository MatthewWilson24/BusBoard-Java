package training.busboard;

import javax.ws.rs.NotFoundException;

public class InvalidPostcodeException extends NotFoundException {
    public InvalidPostcodeException(String s) {
        super(s);
    }
}