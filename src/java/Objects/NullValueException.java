package Objects;
import javax.servlet.ServletException;

public class NullValueException extends ServletException {
    public NullValueException(String message){
        super(message);
    }
}

