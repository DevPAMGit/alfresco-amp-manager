package baobab.api.alfresco.api.core.modele.data;

public class ProjetDataModele {
    /**
     * L'identifiant du projet en base.
     */
    public final int identifiant;

    /**
     * Le groupe du projet en base.
     */
    public final String groupe;

    /**
     * Le nom du projet en base.
     */
    public final String nom;

    /**
     * La version du projet en base de données.
     */
    public final String version;

    /**
     * Le chemin vers le projet.
     */
    public final String chemin;

    /**
     * Initialise une nouvelle instance de la classe {@link ProjetDataModele}.
     * @param identifiant   L'identifiant du projet en base.
     * @param groupe        Le groupe du projet en base.
     * @param nom           Le nom du projet en base.
     * @param version       La version du projet en base de données.
     * @param chemin        Le chemin vers le projet.
     */
    public ProjetDataModele(int identifiant, String groupe, String nom, String version, String chemin) {
        this.identifiant = identifiant;
        this.version = version;
        this.groupe = groupe;
        this.chemin = chemin;
        this.nom = nom;
    }
}
