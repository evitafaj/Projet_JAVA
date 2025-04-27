package DAO;

import Modele.Admin;
import java.util.ArrayList;

public interface AdminDAO {

    ArrayList<Admin> getAll();
    void ajouter(Admin admin);
    Admin chercher(int id);
    Admin modifier(Admin admin);
    void supprimer(Admin admin);

    // Méthode pour l'authentification des admins :
    Admin seConnecter(String email, String motDePasse);
}
