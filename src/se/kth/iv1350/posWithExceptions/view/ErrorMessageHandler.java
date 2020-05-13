package se.kth.iv1350.posWithExceptions.view;

import se.kth.iv1350.posWithExceptions.util.TimeUtil;

/**
 * This class is responsible for printing error messages to the user.
 */
class ErrorMessageHandler {

    ErrorMessageHandler() {

    }

    /**
     * Shows an error to the user.
     *
     * @param message The message to be shown.
     */
    void showErrorMessage(String message) {
        String errorMessage = TimeUtil.getLocalizedTimeNow() + " ERROR: " + message;
        System.out.println(errorMessage);
    }
}
