// main exception class for authentication
package miscs;

import javax.servlet.ServletException;

public class AuthenticationException extends ServletException{
    public AuthenticationException(String message){
        super(message);
    }
}
