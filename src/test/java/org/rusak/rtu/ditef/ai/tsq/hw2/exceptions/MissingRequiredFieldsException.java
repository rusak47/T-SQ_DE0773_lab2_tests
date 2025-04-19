package org.rusak.rtu.ditef.ai.tsq.hw2.exceptions;

/**
 * Exception thrown when the mandatory fields are not filled in.
 */
public class MissingRequiredFieldsException extends ValidationException {
    public MissingRequiredFieldsException(String message) {
        super(message);
    }

    public MissingRequiredFieldsException(String message, Throwable cause) {
        super(message, cause);
    }
}