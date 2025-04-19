package dao;

import model.LigneCommande;
import java.util.List;

public interface LigneCommandeDAO {
    boolean ajouterLigneCommande(LigneCommande ligne);
    boolean supprimerLigneCommande(int idLigneCommande);
    double getPrixUnitaireProduit(int idProduit);
    double getPrixVracProduit(int idProduit);
    int getSeuilVracProduit(int idProduit);
    List<LigneCommande> getLignesByCommande(int idCommande);
}
