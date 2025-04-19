package controller;

import dao.CommandeDAO;
import dao.factory.DAOFactory;
import model.Commande;

import java.util.Date;
import java.util.List;

public class CommandeController {

    private final CommandeDAO commandeDAO;

    public CommandeController() {
        this.commandeDAO = DAOFactory.getCommandeDAO();
    }


    public boolean ajouterCommande(Date date, String statut, double total, int idClient) {
        Commande commande = new Commande(date, statut, total, idClient);
        return commandeDAO.ajouterCommande(commande);
    }


    public boolean modifierCommande(int idCommande, Date date, String statut, double total, int idClient) {
        Commande commande = new Commande(idCommande, date, statut, total, idClient);
        return commandeDAO.modifierCommande(commande);
    }


    public boolean supprimerCommande(int idCommande) {
        return commandeDAO.supprimerCommande(idCommande);
    }


    public Commande getCommandeById(int idCommande) {
        return commandeDAO.getCommandeById(idCommande);
    }


    public List<Commande> getCommandesByClient(int idClient) {
        return commandeDAO.getCommandesByClient(idClient);
    }

    public List<Commande> getAllCommandes() {
        return commandeDAO.getAllCommandes();
    }
}
