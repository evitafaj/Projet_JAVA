package Controleur;

import DAO.ProduitDAO;
import Modele.Produit;
import java.util.List;

public class ProduitControleur {
    private ProduitDAO produitDAO;

    public ProduitControleur(ProduitDAO produitDAO) {
        this.produitDAO = produitDAO;
    }

    public void ajouterProduit(Produit produit) {
        produitDAO.ajouter(produit);
    }

    public void supprimerProduit(Produit produit) {
        produitDAO.supprimer(produit);
    }

    public List<Produit> getTousProduits() {
        return produitDAO.getAll();
    }
}