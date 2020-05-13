package se.kth.iv1350.posWithExceptions.integration;

/**
 * Thrown when there is no connection to the database.
 */
public class NoDatabaseConnectionException extends RuntimeException {

    /**
     * Creates a new instance containing the specified message.
     *
     * @param message The exception message.
     */
    public NoDatabaseConnectionException(String message) {
        super(message);
    }
}
