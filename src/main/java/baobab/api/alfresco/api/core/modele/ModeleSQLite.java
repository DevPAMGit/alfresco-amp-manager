package baobab.api.alfresco.api.core.modele;

import baobab.api.alfresco.api.core.exception.ModeleException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe modèle pour un modèle se basant sur une base de données SQLite.
 */
public abstract class ModeleSQLite {
    /**
     * Début de la chaîne de connexion.
     */
    private static final String BASE_CHAINE_CONNEXION = "jdbc:sqlite";

    /**
     * Chaine de connexion vers la base de données.
     */
    private final String chaineDeConnexion;

    /**
     * Initialise une nouvelle instance de la classe {@link ModeleSQLite}.
     * @param cheminDossier     Le chemin vers le dossier de la base de données.
     * @param nomFichier        Le nom du fichier de nase de données.
     * @throws ModeleException  Si le chemin vers le dossier de la base de données est null, vide ou n'existe pas ou si
     *                          le nom de fichier est null ou vide.
     */
    public ModeleSQLite(String cheminDossier, String nomFichier) throws ModeleException {
        this.chaineDeConnexion = String.format(
                "%s:%s", BASE_CHAINE_CONNEXION, this.construireCheminFichierBdd(cheminDossier, nomFichier)
        );
        this.initialiser();
    }

    /**
     * Execute une requête sur la base de données.
     * @param requete La requete.
     * @return Le resultat de la requête.
     * @throws ModeleException Si la requête est null ou vide.
     */
    public CachedRowSet executer(String requete) throws ModeleException {
        // Vérification des préconditions.
        if(requete == null || requete.isEmpty())
            throw new ModeleException("Le requête ne peut être null ou vide.");

        CachedRowSet resultat = null;

        synchronized (this.chaineDeConnexion) {
            try (Connection connection = DriverManager.getConnection(this.chaineDeConnexion)) {

                Statement statement = connection.createStatement();

                // Récupération du résultat.
                if(statement.execute(requete)) {
                    resultat = RowSetProvider.newFactory().createCachedRowSet();
                    resultat.populate(statement.getResultSet());
                }

            }catch (SQLException e) {
                throw new ModeleException(
                        String.format(
                                "Une erreur est survenue lors de l'exécution d'une requête.\nEn voici la cause: \n%s",
                                e.getMessage()
                        )
                );
            }
        }

        return resultat;
    }

    /**
     * Initialise la base de données en créant les tables et autres besoins pour le modèle.
     * @throws ModeleException Si une requête exécutée est null ou vide.
     */
    protected abstract void initialiser() throws ModeleException;

    /**
     * Construit le chemin vers le fichier de base de données.
     *@param cheminDossier Le chemin vers le dossier de la base de données.
     *@param nomFichier    Le nom du fichier de nase de données.
     *@throws ModeleException Si le chemin vers le dossier de la base de données est null, vide ou n'existe pas ou si
     * le nom de fichier est null ou vide.
     */
    public String construireCheminFichierBdd(String cheminDossier, String nomFichier) throws ModeleException {
        this.verifierNomFichier(nomFichier);
        this.verifierCheminDossier(cheminDossier);

        // Initialisation de la chaine de connexion.
        // Vérification de la terminaison du chemin dossier.
        StringBuilder resultat = new StringBuilder(cheminDossier);
        if(cheminDossier.lastIndexOf(File.separator) != cheminDossier.charAt(cheminDossier.length()-1))
            // Ajout du séparateur de chemin si nécessaire.
            resultat.append(File.separator);

        // Ajout du nom de fichier.
        resultat.append(String.format("%s.sqlite", nomFichier));

        // retour du résultat.
        return resultat.toString();
    }

    /**
     * Vérifie que le chemin du dossier mis en paramètre est valide (non nul, non vide et existe).
     * @param cheminDossier Le chemin vers le dossier de la base de données.
     * @throws ModeleException Si le chemin est null, vide, ou n'existe pas.
     */
    private void verifierCheminDossier(String cheminDossier) throws ModeleException {
        // Vérification de l'argument.
        if(cheminDossier == null || cheminDossier.isEmpty())
            throw  new ModeleException("Le chemin vers le dossier du fichier ne peut être null ou vide.");

        // Vérification de son existence.
        File dossier = new File(cheminDossier);
        if(!dossier.exists())
            throw  new ModeleException(String.format("Le dossier %s n'existe pas dans l'arborescence.", cheminDossier));
        else if(!dossier.isDirectory())
            throw  new ModeleException(String.format("Le chemin %s n'est pas un dossier. " +
                    "Le fichier de base de données nécessite un chemin de dossier!", cheminDossier));
    }

    /**
     * Vérifie que le nom du fichier est valide (non null et non vide).
     * @param nomFichier Le nom du fichier.
     * @throws ModeleException Si le nom du fichier est null ou vide.
     */
    private void verifierNomFichier(String nomFichier) throws ModeleException {
        // Vérification de l'argument.
        if(nomFichier == null || nomFichier.isEmpty())
            throw  new ModeleException("Le nom de la base de données de ne peut être nul ou vide.");
    }

}
