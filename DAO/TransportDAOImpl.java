/** Author Nina */
package DAO;

import Modele.Transport;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

public class TransportDAOImpl implements TransportDAO {
    @Override
    public Map<String, Object> add(Transport transport) {
        Map<String, Object> data = new HashMap<>();
        data.put("adresse", transport.getAdresse());
        data.put("dateLivraison", transport.getDateLivraison());
        data.put("statut", transport.getStatut());
        data.put("idCommande", transport.getIdCommande());

        Map<String, Object> where = new HashMap<>();
        where.put("idCommande", transport.getIdCommande());

        Map<String, Object> existingLivraison = DB.getInstance().table("livraison").selectOne(where);

        if (existingLivraison != null && !existingLivraison.isEmpty()) {
            int existingLivraisonId = (Integer) existingLivraison.get("id");
            data.put("id", existingLivraisonId);

            List<Map<String, Object>> updatedLivraison = DB.getInstance().table("livraison").update(data, where);

            if (updatedLivraison != null && !updatedLivraison.isEmpty()) {
                return updatedLivraison.get(0);
            } else {
                return null;
            }
        } else {
            return DB.getInstance().table("livraison").insert(data);
        }
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return DB.getInstance().table("livraison").select(null);
    }

    @Override
    public Map<String, Object> findById(int id) {
        Map<String, Object> where = new HashMap<>();
        where.put("id", id);
        return DB.getInstance().table("livraison").selectOne(where);
    }

    @Override
    public List<Map<String, Object>> update(Transport transport) {
        Map<String, Object> data = new HashMap<>();
        data.put("adresse", transport.getAdresse());
        data.put("dateLivraison", transport.getDateLivraison());
        data.put("statut", transport.getStatut());
        data.put("idCommande", transport.getIdCommande());

        Map<String, Object> where = new HashMap<>();
        where.put("id", transport.getId());

        return DB.getInstance().table("livraison").update(data, where);
    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> where = new HashMap<>();
        where.put("id", id);
        return DB.getInstance().table("livraison").delete(where);
    }

    @Override
    public Transport findByCommandeId(int id) {
        Map<String, Object> where = new HashMap<>();
        where.put("idCommande", id);

        Map<String, Object> result = DB.getInstance().table("livraison").selectOne(where);

        if (result == null || result.isEmpty()) {
            return null;
        }

        int transportId = (Integer) result.get("id");
        String adresse = (String) result.get("adresse");
        LocalDate dateLivraison = ((java.sql.Date) result.get("dateLivraison")).toLocalDate();
        String statut = (String) result.get("statut");
        int commandeId = (Integer) result.get("idCommande");

        return new Transport(transportId, adresse, dateLivraison, statut, commandeId);
    }
}