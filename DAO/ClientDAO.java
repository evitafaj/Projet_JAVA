package DAO;

import Modele.Client;
import java.util.ArrayList;

public interface ClientDAO {
    /**
     * Récupère tous les clients dans la base de données.
     * @return liste de tous les clients.
     */
    ArrayList<Client> getAll();

    /**
     * Ajoute un nouveau client dans la base de données.
     * @param client objet Client à ajouter.
     */
    void ajouter(Client client);

    /**
     * Recherche un client par son identifiant.
     * @param id identifiant du client à rechercher.
     * @return l'objet Client trouvé ou null.
     */
    Client chercher(int id);

    /**
     * Met à jour les informations d'un client.
     * @param client le client avec les nouvelles données.
     * @return le client modifié.
     */
    Client modifier(Client client);

    /**
     * Supprime un client (et ses commandes associées).
     * @param client le client à supprimer.
     */
    void supprimer(Client client);

    //Méthode à ajouter pour l'authentification :
    Client seConnecter(String email, String motDePasse);
}
