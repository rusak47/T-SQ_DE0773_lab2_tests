package org.rusak.rtu.ditef.ai.tsq.hw2.exceptions;

/**
 * Exception thrown when the email is already registered.
 */
public class DuplicateEmailException extends ValidationException {
    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}