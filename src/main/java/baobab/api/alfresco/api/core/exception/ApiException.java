package baobab.api.alfresco.api.core.exception;

/**
 * Exception personnalisée pour l'application.
 */
public class ApiException extends Exception {
    /**
     * Initialise une nouvelle instance de la classe {@link ApiException}.
     * @param message Le message de l'exception personnalisée.
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * Initialise une nouvelle instance de la classe {@link ApiException}.
     * @param message Le message de l'exception personnalisée.
     * @param exception L'exception ayant causér celle-ci.
     */
    public ApiException(Exception exception, String message) {
        super(message, exception);
    }
}
