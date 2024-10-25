package org.krainet.timetracker.exception;

public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException(){
        super("token refresh exception");
    }
}
