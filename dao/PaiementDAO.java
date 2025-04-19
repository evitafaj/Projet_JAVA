package dao;

import model.Paiement;

public interface PaiementDAO {
    boolean ajouterPaiement(Paiement paiement);
    Paiement getPaiementByCommande(int idCommande);
}
