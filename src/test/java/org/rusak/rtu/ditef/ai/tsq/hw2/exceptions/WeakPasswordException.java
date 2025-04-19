package org.rusak.rtu.ditef.ai.tsq.hw2.exceptions;

/**
 * Exception thrown when the password strength is not sufficient.
 */
public class WeakPasswordException extends ValidationException {
    public WeakPasswordException(String message) {
        super(message);
    }

    public WeakPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}