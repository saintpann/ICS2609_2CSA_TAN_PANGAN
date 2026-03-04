package Objects;
// good for error_3.jsp
public class IncorrectUserPassException extends AuthenticationException{
    public IncorrectUserPassException(String message){
        super(message);
    }
}

