package baobab.api.alfresco.api.core.controleur;

import baobab.api.alfresco.api.core.exception.ControleurException;
import baobab.api.alfresco.api.core.exception.ModeleException;
import baobab.api.alfresco.api.core.modele.Modele;

import baobab.api.alfresco.api.core.modele.data.ProjetDataModele;
import baobab.api.alfresco.api.core.vue.IVue;
import baobab.api.alfresco.api.core.vue.Vue;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.util.stream.Collectors;

/**
 * Controleur de l'application.
 */
public class ApiControleur implements IControleur {
    /**
     * Chemin absolu vers le fichier de base de données.
     */
    private static final String CHEMIN_BDD = String.format("%s%sdb%s", System.getProperty("user.dir"));

    /**
     * Le modèle de l'application.
     */
    private final Modele modele;

    /**
     * La vie de l'application.
     */
    private final IVue vue;

    /**
     * Initialise une nouvelle instance de la classe {@link ApiControleur}.
     * @param vue               La vue du controleur.
     * @throws ModeleException  Si le chemin vers le dossier de la base de données est null, vide ou n'existe pas ou si
     *                          le nom de fichier est null ou vide.
     */
    public ApiControleur(IVue vue) throws ModeleException {
        this.vue = vue;
        File dbFolder = new File(CHEMIN_BDD);

        if(!dbFolder.exists() || !dbFolder.isDirectory())
            //noinspection ResultOfMethodCallIgnored
            dbFolder.mkdirs();

        this.modele = new Modele(CHEMIN_BDD);
    }

    /**
     * Execute une commande sur le système.
     * @param commande La commande à executer.
     * @return <c>true</c> si la commande s'est exécuté avec succès. Sinon <c>false</c>.
     * @throws ControleurException Si la commande lance une exception.
     */
    private boolean executerCommandeSysteme(String[] commande) throws ControleurException {
        Process process;

        try {
            process = Runtime.getRuntime().exec(commande);

            BufferedReader lecteur = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = lecteur.lines().collect(Collectors.joining("\n"));

            System.out.println(result + "\n");

            int exitCode = process.waitFor();
            process.destroy();

            return (exitCode == 1);

        } catch (Exception e) {
            throw new ControleurException("Une erreur inattendue a interrompu l'exécution de la commande.\n" +
                    "Veuillez vous référer au logs de l'application.", e);
        }
    }

    @Override
    public void creerProjet(String groupe, String nom, String version) throws ControleurException, ModeleException {
        if(groupe == null || groupe.trim().isEmpty())
            throw new ControleurException("Le groupe du projet à créer en peut être null.");

        else if(nom == null || nom.trim().isEmpty())
            throw new ControleurException("Le nom du projet à créer en peut être null.");

        else if(version == null || version.trim().isEmpty())
            throw new ControleurException("La version du projet à créer en peut être null.");

        if(this.modele.existerProjet(groupe, nom, version))
            throw new ControleurException(
                    String.format("Le projet %s:%s:%s existe déjà en base.", groupe, nom, version)
            );

        if(!this.executerCommandeSysteme(new String[]{ "cmd", "c",
            String.format(
                    "mvn archetype:generate -DarchetypeGroupId=\"org.alfresco.maven.archetype\" " +
                            "-DarchetypeArtifactId=\"alfresco-allinone-archetype\" -DarchetypeVersion=\"%s\" " +
                            "-DgroupId=\"%s\" DartifactId=\"%s\" -DinteractiveMode=\"false\"", version, groupe, nom
            )
        })) return;

        this.modele.ajouterProjet(groupe, nom, version);
        this.vue.afficherCreationProjet(this.modele.obtenirProjet(groupe, nom, version));
    }

    @Override
    public void listerProjets() throws ModeleException {
        this.vue.afficherListeProjets(this.modele.listerProjets());
    }

    @Override
    public void supprimerProjet(int id) throws ModeleException, ControleurException {
        ProjetDataModele projet = this.modele.obtenirProjet(id);
        if(projet == null) {
            this.vue.afficherMessage(String.format("L'identifiant %d ne correspond à aucun projet en base.", id));
            return;
        }

        // Suppression du dossier de projet.
        File dossierProjet = new File(projet.chemin);
        if(!dossierProjet.exists()) {
            this.modele.supprimerProjet(id);
            throw new ControleurException(
                    String.format(
                        "Le dossier du projet '%s' du groupe '%s' n'existe sur le chemin indiqué.\n" +
                        "En conséquence, nous l'avons supprimé de la base de données.", projet.nom, projet.groupe)
            );
        }else if(!dossierProjet.delete()){
            throw new ControleurException(
                    String.format("Nous n'avons pas réussi à supprimer le dossier se situant sur '%s'.", projet.chemin)
            );
        }else this.modele.supprimerProjet(id);
    }
}
