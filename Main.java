package Controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.*;
import Modele.*;
import Vue.*;

public class Main {
    public static void main(String[] args) {
        // Instancier la DAO
        DaoFactory dao = DaoFactory.getInstance("model_shopping", "root", "root");

        // DAO et Vues
        ClientDAOImpl clientDAO = new ClientDAOImpl(dao);
        VueClient vueClient = new VueClient();
        AdminDAOImpl adminDAO = new AdminDAOImpl(dao);
        VueAdmin vueAdmin = new VueAdmin();
        new Vue.FenetreAccueil(dao);

        Scanner scanner = new Scanner(System.in);

        // --- MENU SIMPLIFIÉ POUR TESTER LA PARTIE UTILISATEUR ---
        System.out.println("=== MENU UTILISATEURS ===");
        System.out.println("1. Afficher tous les clients");
        System.out.println("2. Ajouter un nouveau client");
        System.out.println("3. Chercher un client par ID");
        System.out.println("4. Modifier un client");
        System.out.println("5. Supprimer un client");
        System.out.println("6. Afficher tous les administrateurs");
        System.out.println("7. Connexion (Client ou Admin)");
        System.out.print("Choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // éviter bugs entrée

        switch (choix) {
            case 1:
                ArrayList<Client> clients = clientDAO.getAll();
                vueClient.afficherListeClients(clients);
                break;

            case 2:
                System.out.print("Nom : ");
                String nom = scanner.nextLine();
                System.out.print("Prénom : ");
                String prenom = scanner.nextLine();
                System.out.print("Adresse : ");
                String adresse = scanner.nextLine();
                System.out.print("Email : ");
                String email = scanner.nextLine();
                System.out.print("Mot de passe : ");
                String mdp = scanner.nextLine();

                Client newClient = new Client(0, nom, prenom, adresse, email, mdp);
                clientDAO.ajouter(newClient);
                System.out.println("Client ajouté !");
                break;

            case 3:
                System.out.print("ID du client : ");
                int id = scanner.nextInt();
                Client client = clientDAO.chercher(id);
                if (client != null) {
                    vueClient.afficherClient(client);
                } else {
                    System.out.println("Client introuvable !");
                }
                break;

            case 4:
                System.out.print("ID du client à modifier : ");
                int idModif = scanner.nextInt();
                scanner.nextLine();
                Client modif = clientDAO.chercher(idModif);
                if (modif != null) {
                    System.out.print("Nouveau nom : ");
                    modif.setNom(scanner.nextLine());
                    System.out.print("Nouveau prénom : ");
                    modif.setPrenom(scanner.nextLine());
                    System.out.print("Nouvelle adresse : ");
                    modif.setAdresse(scanner.nextLine());
                    System.out.print("Nouvel email : ");
                    modif.setEmail(scanner.nextLine());
                    System.out.print("Nouveau mot de passe : ");
                    modif.setMotDePasse(scanner.nextLine());

                    clientDAO.modifier(modif);
                    vueClient.afficherClient(modif);
                } else {
                    System.out.println("Client introuvable.");
                }
                break;

            case 5:
                System.out.print("ID du client à supprimer : ");
                int idSupp = scanner.nextInt();
                Client aSupprimer = clientDAO.chercher(idSupp);
                if (aSupprimer != null) {
                    clientDAO.supprimer(aSupprimer);
                    System.out.println("Client supprimé.");
                } else {
                    System.out.println("Client introuvable.");
                }
                break;

            case 6:
                ArrayList<Admin> admins = adminDAO.getAll();
                vueAdmin.afficherListeAdmins(admins);
                break;

            case 7:
                System.out.println("=== Connexion ===");
                System.out.print("Email : ");
                String loginEmail = scanner.nextLine();
                System.out.print("Mot de passe : ");
                String loginMdp = scanner.nextLine();

                // Tester si c'est un client
                Client clientConnecte = clientDAO.seConnecter(loginEmail, loginMdp);
                if (clientConnecte != null) {
                    System.out.println("Connexion client réussie : Bonjour " + clientConnecte.getPrenom() + " !");
                    break;
                }

                // Sinon, tester si c'est un admin
                Admin adminConnecte = adminDAO.seConnecter(loginEmail, loginMdp);
                if (adminConnecte != null) {
                    System.out.println("Connexion admin réussie : Bonjour " + adminConnecte.getPrenom() + " !");
                    break;
                }

                System.out.println("Identifiants incorrects !");
                break;

            default:
                System.out.println("Choix invalide.");
        }

        // Fermer la connexion
        dao.disconnect();
    }
}
