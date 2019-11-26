package training.busboard;

import javax.ws.rs.BadRequestException;

public class NoPostcodeException extends BadRequestException {
    public NoPostcodeException(String s) {
        super(s);
    }
}