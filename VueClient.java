package Vue;

import Modele.Client;
import java.util.ArrayList;

public class VueClient {

    /**
     * Affiche les informations d’un client
     * @param client objet de la classe Client
     */
    public void afficherClient(Client client) {
        System.out.println("=== Informations Client ===");
        System.out.println("ID       : " + client.getIdClient());
        System.out.println("Nom      : " + client.getNom());
        System.out.println("Prénom   : " + client.getPrenom());
        System.out.println("Adresse  : " + client.getAdresse());
        System.out.println("Email    : " + client.getEmail());
        System.out.println("Mot de passe : " + client.getMotDePasse());
        System.out.println("-------------------------------");
    }

    /**
     * Affiche la liste des clients
     * @param clients liste d’objets de type Client
     */
    public void afficherListeClients(ArrayList<Client> clients) {
        System.out.println("=== Liste des clients ===");
        for (Client c : clients) {
            afficherClient(c);
        }
    }
}
