package DAO;

import Modele.Produit;
import java.util.ArrayList;

public interface ProduitDAO {
    ArrayList<Produit> getAll();
    void ajouter(Produit produit);
    Produit chercher(int id);
    Produit modifier(Produit produit);
    void supprimer(Produit produit);
}