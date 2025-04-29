/** Author Nina */
package DAO;

import Modele.Commande;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommandeDAO {

    ArrayList<Commande> getAll();

    Commande findById(int idCommande);

    void add(Commande commande);

    Commande update(Commande commande);

    void delete(Commande commande);

    Map<String, List<Commande>> regroupeCommandeByDate();
    Commande getCommandeById(int id);
    List<Map<String, Object>> getCommandeLineByCommandeId(int id);
    List<Commande> getCommandesByClientId(int clientId);
}