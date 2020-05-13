package se.kth.iv1350.posWithExceptions.model;

/**
 * Thrown when trying to add an item whose identifier can not be found in inventory.
 */
public class ItemNotFoundException extends Exception {
    private int notFoundIdentifier;

    /**
     * Creates a new instance with a message specifying the identifier which could not be found.
     *
     * @param notFoundIdentifier The identifier that could not be found.
     */
    public ItemNotFoundException(int notFoundIdentifier) {
        super("Unable to add item with identifier " + notFoundIdentifier +
                " since there is no such item in inventory.");
        this.notFoundIdentifier = notFoundIdentifier;
    }

    /**
     * Get the identifier that could not be found.
     *
     * @return the identifier that could not be found.
     */
    public int getNotFoundIdentifier() {
        return notFoundIdentifier;
    }
}
