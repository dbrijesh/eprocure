package com.eprocure.project.exception;

/**
 * Exception thrown when project data is invalid.
 */
public class InvalidProjectDataException extends RuntimeException {

    public InvalidProjectDataException(String message) {
        super(message);
    }

    public InvalidProjectDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
