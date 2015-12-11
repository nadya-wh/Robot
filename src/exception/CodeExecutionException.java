package exception;

import java.security.PrivilegedActionException;

/**
 * Created by User on 30.11.2015.
 */
public class CodeExecutionException extends Exception {

    public CodeExecutionException() {
    }

    public CodeExecutionException(String message) {
        super(message);
    }

    public CodeExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeExecutionException(Throwable cause) {
        super(cause);
    }

    public CodeExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
