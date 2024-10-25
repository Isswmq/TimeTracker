package org.krainet.timetracker.exception;

public class UserWithEmailAlreadyExistException extends RuntimeException{
    public UserWithEmailAlreadyExistException() {
        super("user with email already exist");
    }
}
