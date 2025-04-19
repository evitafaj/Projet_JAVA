package dao;

import model.Livraison;

public interface LivraisonDAO {
    boolean ajouterLivraison(Livraison livraison);
    Livraison getLivraisonByCommande(int idCommande);
    boolean mettreAJourStatutLivraison(int idCommande, String nouveauStatut);
}
