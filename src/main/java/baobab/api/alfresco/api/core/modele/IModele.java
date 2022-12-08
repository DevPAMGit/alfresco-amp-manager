package baobab.api.alfresco.api.core.modele;

import baobab.api.alfresco.api.core.exception.ModeleException;
import baobab.api.alfresco.api.core.modele.data.ProjetDataModele;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IModele {
    /**
     * Indique si le projet existe en base.
     * @param groupe Le groupe du projet.
     * @param nom Le nom du projet.
     * @param version La version du projet.
     * @return <c>true</c> si le projet existe. Sinon <c>false</c>.
     * @throws ModeleException Si une erreur survient lors de l'exécution de la requête de vérification.
     */
    boolean existerProjet(String groupe, String nom, String version) throws ModeleException;

    /**
     * Ajoute un projet en base de données.
     * @param groupe Le groupe du projet.
     * @param nom Le nom du projet.
     * @param version La version du projet.
     * @throws ModeleException Si une erreur survient lors de l'exécution de la requête d'insertion.
     */
    void ajouterProjet(String groupe, String nom, String version) throws ModeleException;

    /**
     * Retourne la liste des projets en cours en base de données.
     * @return La liste des projets en cours en base de données.
     * @throws ModeleException Si une erreur survient lors de l'exécution d'une requête ou à la récupération du résultat.
     */
    List<ProjetDataModele> listerProjets() throws ModeleException;

    /**
     * Méthode permettant d'obtenir un projet enregistré en base de données.
     * @param groupe            Le groupe du projet.
     * @param nom               Le nom du projet.
     * @param version           La version du projet.
     * @return                  Une instance de type {@link ProjetDataModele}.
     * @throws ModeleException  Si une erreur survient lors de l'exécution d'une requete ou à la récupération du
     *                          résultat.
     */
    ProjetDataModele obtenirProjet(String groupe, String nom, String version) throws ModeleException;

    /**
     * Méthode permettant d'obtenir un projet enregistré en base de données.
     * @param id L'identifiant en base de donnée du projet.
     */
    ProjetDataModele obtenirProjet(int id) throws ModeleException;

    /**
     * Supprime un projet grâce à son identifiant.
     * @param id L'identifiant du projet à supprimer.
     */
    void supprimerProjet(int id) throws ModeleException;
}
