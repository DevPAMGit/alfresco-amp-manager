package baobab.api.alfresco.api.core.vue;

import baobab.api.alfresco.api.core.modele.data.ProjetDataModele;

import java.util.List;

/**
 * Contrat d'interface pour la vue de l'application.
 */
public interface IVue {

    /**
     * Affiche la création d'un projet sur la vue.
     * @param projet Le projet à afficher.
     */
    void afficherCreationProjet(ProjetDataModele projet);

    /**
     * Affiche une ligne de données d'un projet.
     * @param projet Le projet à afficher.
     */
    void afficherLigneProjet(ProjetDataModele projet);

    /**
     * Affiche sur la sortie standard la
     * @param listerProjets la liste des projets à afficher.
     */
    void afficherListeProjets(List<ProjetDataModele> listerProjets);

    /**
     * Affiche un message sur la sortie standard.
     * @param message Le message à afficher.
     */
    void afficherMessage(String message);
}
