// main exception class for authentication
package Objects;

import javax.servlet.ServletException;

public class AuthenticationException extends ServletException{
    public AuthenticationException(String message){
        super(message);
    }
}
