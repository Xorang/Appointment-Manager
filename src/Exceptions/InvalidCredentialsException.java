package Exceptions;

/**
 * Special exception for when a user provides bad credentials for logging in
 * Part of REQUIREMENT F
 */

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(Exception e) {
        super(e);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}