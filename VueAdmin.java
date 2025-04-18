package Vue;

import Modele.Admin;
import java.util.ArrayList;

public class VueAdmin {

    /**
     * Affiche les informations d’un administrateur
     * @param admin objet de la classe Admin
     */
    public void afficherAdmin(Admin admin) {
        System.out.println("=== Informations Admin ===");
        System.out.println("ID       : " + admin.getIdAdmin());
        System.out.println("Nom      : " + admin.getNom());
        System.out.println("Prénom   : " + admin.getPrenom());
        System.out.println("Email    : " + admin.getEmail());
        System.out.println("Mot de passe : " + admin.getMotDePasse());
        System.out.println("-------------------------------");
    }

    /**
     * Affiche la liste des administrateurs
     * @param admins liste d’objets de type Admin
     */
    public void afficherListeAdmins(ArrayList<Admin> admins) {
        System.out.println("=== Liste des administrateurs ===");
        for (Admin a : admins) {
            afficherAdmin(a);
        }
    }
}
