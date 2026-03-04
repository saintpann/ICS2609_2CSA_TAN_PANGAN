package Objects;

public class NoUsernameFoundException extends AuthenticationException{
    public NoUsernameFoundException(String message){
        super(message);
    }
}
