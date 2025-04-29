package DAO;

import Modele.Avis;
import java.util.List;

public interface AvisDAO {
    void ajouterAvis(Avis avis);
    void supprimerAvis(int id);
    Avis getAvisById(int id);
    List<Avis> getAvisByProduit(int idProduit);
    List<Avis> getAllAvis();
}
