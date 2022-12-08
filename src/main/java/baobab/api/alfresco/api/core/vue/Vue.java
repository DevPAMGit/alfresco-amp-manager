package baobab.api.alfresco.api.core.vue;

import baobab.api.alfresco.api.core.modele.data.ProjetDataModele;

import java.util.List;

/**
 * Vue pour le projet.
 */
public class Vue implements IVue {
    @Override
    public void afficherCreationProjet(ProjetDataModele projet) {
        System.out.printf("%d projet a été créé.%n", projet!=null ? 1 : 0);
        if(projet != null) this.afficherLigneProjet(projet);
    }

    @Override
    public void afficherLigneProjet(ProjetDataModele projet) {
        System.out.printf("ID: %d GROUP: %s NOM: %s VERSION: %s\n", projet.identifiant, projet.groupe, projet.nom,
                projet.version
        );
    }

    @Override
    public void afficherListeProjets(List<ProjetDataModele> projets) {
        String pluriel = projets.size() > 1 ? "s" : "";
        System.out.printf("%d projet%s trouvé%s.\n", projets.size(), pluriel, pluriel);
        for (ProjetDataModele projet: projets) this.afficherLigneProjet(projet);
    }

    @Override
    public void afficherMessage(String message) {
        System.out.println(message);
    }
}
