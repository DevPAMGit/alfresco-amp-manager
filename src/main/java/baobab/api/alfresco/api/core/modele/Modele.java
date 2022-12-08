package baobab.api.alfresco.api.core.modele;

import baobab.api.alfresco.api.core.exception.ModeleException;
import baobab.api.alfresco.api.core.modele.data.ProjetDataModele;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle de l'application.
 */
public class Modele extends ModeleSQLite implements IModele{
    /**
     * Initialise une nouvelle instance de la classe {@link ModeleSQLite}.
     * @param cheminDossier     Le chemin vers le dossier de la base de données.
     * @throws ModeleException  Si le chemin vers le dossier de la base de données est null, vide ou n'existe pas ou si
     *                          le nom de fichier est null ou vide.
     */
    public Modele(String cheminDossier) throws ModeleException {
        super(cheminDossier, "alfresco-manager-db");
    }

    @Override
    protected void initialiser() throws ModeleException {
        this.executer("create table if not exists lic_licence(" +
                           "lic_id integer autoincrement, " +
                           "lic_chemin text not null, " +
                           "lic_date_debut date not null, " +
                           "lic_date_fin date not null," +
                           "constraint pk_licence primary key(lic_id)" +
                       ");");

        this.executer("create table if not exists pro_projet(" +
                            "pro_id integer autoincrement," +
                            "pro_groupe text not null," +
                            "pro_artifact text not null," +
                            "pro_version text not null," +
                            "pro_chemin text not null, " +
                            "constraint pk_projet primary key(pro_id)" +
                            ");");
    }

    @Override
    public boolean existerProjet(String groupe, String nom, String version) throws ModeleException {
        CachedRowSet projet = this.executer(String.format(
                "select * from pro_projet pro " +
                "where pro.pro_groupe = '%s' " +
                "and pro.pro_nom = '%s' " +
                "and pro.pro_version = '%s';", groupe.toLowerCase(), nom.toLowerCase(), version.toLowerCase()
        ));
        return (projet != null && projet.size() > 0);
    }

    @Override
    public void ajouterProjet(String groupe, String nom, String version) throws ModeleException {
        this.executer(String.format("insert into pro_projet(pro_group, pro_nom, pro_version)" +
                                    "values('%s', '%s', '%s');",
                groupe.toLowerCase(), nom.toLowerCase(), version.toLowerCase()));
    }

    @Override
    public List<ProjetDataModele> listerProjets() throws ModeleException {
        CachedRowSet requestResult = this.executer("select * from pro_projet;");
        ArrayList<ProjetDataModele> result = new ArrayList<>();

        try {

            while (requestResult.next()) {
                result.add(new ProjetDataModele(
                        requestResult.getInt("pro_id"),
                        requestResult.getString("pro_groupe"),
                        requestResult.getString("pro_nom"),
                        requestResult.getString("pro_version"),
                        requestResult.getString("pro_chemin"))
                );
            }

            return result;

        }catch (Exception e) {
            throw new ModeleException(
                    String.format(
                            "Une erreur est survenue lors de l'exécution d'une requête.\nEn voici la cause: \n%s",
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public ProjetDataModele obtenirProjet(String groupe, String nom, String version) throws ModeleException {
        CachedRowSet requestResult = this.executer(String.format(
                "select * from pro_projet where pro_groupe = '%s' " +
                "and pro_nom = '%s' and pro_version = '%s';",
                groupe.toLowerCase(), nom.toLowerCase(), version.toLowerCase()));

        try {

            if(requestResult.size() == 0 || !requestResult.next())
                return null;

            return new ProjetDataModele(
                    requestResult.getInt("pro_id"), requestResult.getString("pro_groupe"),
                    requestResult.getString("pro_nom"), requestResult.getString("pro_version"),
                    requestResult.getString("pro_chemin")
            );

        } catch (SQLException e) {
            throw new ModeleException(
                    String.format(
                            "Une erreur est survenue lors de l'exécution d'une requête.\nEn voici la cause: \n%s",
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public ProjetDataModele obtenirProjet(int id) throws ModeleException {
        CachedRowSet requestResult = this.executer(String.format("select * from pro_projet where pro_id = %d;", id));

        try {

            if (requestResult.size() == 0 || !requestResult.next())
                return null;

            return new ProjetDataModele(
                    requestResult.getInt("pro_id"), requestResult.getString("pro_groupe"),
                    requestResult.getString("pro_nom"), requestResult.getString("pro_version"),
                    requestResult.getString("pro_chemin")
            );

        } catch (SQLException e) {
            throw new ModeleException(
                    String.format(
                            "Une erreur est survenue lors de l'exécution d'une requête.\nEn voici la cause: \n%s",
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public void supprimerProjet(int id) throws ModeleException {
        this.executer(String.format("delete from pro_projet where pro_id = %d;", id));
    }


}
