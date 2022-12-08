package baobab.api.alfresco.api.core;

import baobab.api.alfresco.api.core.controleur.ApiControleur;
import baobab.api.alfresco.api.core.controleur.IControleur;
import baobab.api.alfresco.api.core.exception.ApiException;
import baobab.api.alfresco.api.core.exception.ModeleException;
import baobab.api.alfresco.api.core.vue.Vue;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

/**
 * Classe de l'api.
 */
public class Api {
    /**
     * Liste des services disponibles.
     */
    private HashMap<String, Method> services;

    /**
     * La vue de l'application.
     */
    private final Vue vue;

    /**
     * Le controleur de l'application.
     */
    private IControleur controleur;

    /**
     * Initialise une nouvelle instance de la classe {@link Api}.
     */
    public Api() throws ModeleException {
        this.vue = new Vue();
        this.services = new HashMap<>();
        this.controleur = new ApiControleur(this.vue);
    }

    /**
     * Execute la commande en paramètre.
     * @param commande La commande à executer.
     */
    public void executer(String[] commande) throws ApiException {
        if(commande.length == 0)
            throw new ApiException("Aucun appel service n'a été détecté.");

        if(!this.services.containsKey(commande[0]))
            throw new ApiException(String.format("La commande '%s' n'est pas présente dans l'API.", commande[0]));

        Method method = this.services.get(commande[0]);
        Parameter[] parameters = method.getParameters();

        // if( parameters.length != (commande.length-1))

    }

    public static void main(String[] args) {
    }
}
