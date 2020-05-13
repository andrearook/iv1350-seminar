package se.kth.iv1350.posWithExceptions.controller;

/**
 * Thrown when an operation fails due to an unknown reason.
 */
public class OperationFailedException extends Exception {

    /**
     * Creates a new instance containing the specified message and root cause.
     *
     * @param message The exception message.
     * @param cause   The exception that caused this exception to be thrown.
     */
    public OperationFailedException(String message, Exception cause) {
        super(message, cause);
    }
}
