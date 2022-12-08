package baobab.api.alfresco.api.core.controleur;

import baobab.api.alfresco.api.core.exception.ControleurException;
import baobab.api.alfresco.api.core.exception.ModeleException;

/**
 * Contrat d'interface pour les contrôleurs de l'application.
 */
public interface IControleur {
    /**
     * Créé un projet alfresco.
     * @param groupe                Le groupe du projet.
     * @param nom                   le nom du projet.
     * @param version               La version du projet.
     * @throws ControleurException
     */
    void creerProjet(String groupe, String nom, String version) throws ControleurException, ModeleException;

    /**
     * Liste les projets alfresco disponible dans le module.
     */
    void listerProjets() throws ModeleException;

    /**
     * Supprime un projet alfresco.
     * @param id L'identifiant du projet à supprimer.
     */
    void supprimerProjet(int id) throws ModeleException, ControleurException;
}
