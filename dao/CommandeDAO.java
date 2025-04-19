package dao;

import model.Commande;

import java.util.List;

public interface CommandeDAO {
    boolean ajouterCommande(Commande commande);
    boolean modifierCommande(Commande commande);
    boolean supprimerCommande(int idCommande);
    Commande getCommandeById(int idCommande);
    List<Commande> getCommandesByClient(int idClient);
    List<Commande> getAllCommandes();
}
