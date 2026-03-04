package Objects;
// good for error_2.jsp
public class IncorrectPassException extends AuthenticationException {
    public IncorrectPassException(String message){
        super(message);
    }
}
