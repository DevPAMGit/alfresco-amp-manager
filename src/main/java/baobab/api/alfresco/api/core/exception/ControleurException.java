package baobab.api.alfresco.api.core.exception;

/**
 * Exception personnalisée pour les erreurs modèles.
 */
public class ControleurException extends ApiException {
    /**
     * Initialise une nouvelle instance de la classe {@link ControleurException}.
     * @param message Le message de l'exception personnalisée.
     */
    public ControleurException(String message) {
        super(message);
    }

    /**
     * Initialise une nouvelle instance de la classe {@link ControleurException}.
     * @param message Le message de l'exception personnalisée.
     * @param exception L'exception ayant causé celle-ci.
     */
    public ControleurException(String message, Exception exception) {
        super(exception, message);
    }
}
