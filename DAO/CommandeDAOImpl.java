package DAO;

import Modele.Commande;
import java.sql.*;
import java.util.*;

public class CommandeDAOImpl implements CommandeDAO {

    private DB db;

    public CommandeDAOImpl() {
        this.db = DB.getInstance();
    }

    @Override
    public ArrayList<Commande> getAll() {
        ArrayList<Commande> commandes = new ArrayList<>();
        List<Map<String, Object>> results = db.table("commande").select(null);

        for (Map<String, Object> row : results) {
            commandes.add(mapRowToCommande(row));
        }

        return commandes;
    }

    @Override
    public Commande findById(int id) {
        Map<String, Object> where = new HashMap<>();
        where.put("idCommande", id);

        Map<String, Object> result = db.table("commande").selectOne(where);

        if (result != null) {
            return mapRowToCommande(result);
        }
        return null;
    }

    @Override
    public void add(Commande commande) {
        Map<String, Object> data = new HashMap<>();
        data.put("date", commande.getDate());
        data.put("statut", commande.getStatut());
        data.put("total", commande.getTotal());
        data.put("idClient", commande.getIdClient());

        Map<String, Object> inserted = db.table("commande").insert(data);

        if (inserted != null) {
            commande.setId((Integer) inserted.get("idCommande"));
        }
    }

    @Override
    public Commande update(Commande commande) {
        Map<String, Object> data = new HashMap<>();
        data.put("date", commande.getDate());
        data.put("statut", commande.getStatut());
        data.put("total", commande.getTotal());
        data.put("idClient", commande.getIdClient());

        db.table("commande").update(data, Map.of("idCommande", commande.getId()));

        return commande;
    }

    @Override
    public void delete(Commande commande) {
        db.table("commande").delete(Map.of("idCommande", commande.getId()));
    }

    @Override
    public Map<String, List<Commande>> regroupeCommandeByDate() {
        Map<String, List<Commande>> grouped = new HashMap<>();
        List<Map<String, Object>> results = db.table("commande").select(null);

        for (Map<String, Object> row : results) {
            Commande commande = mapRowToCommande(row);
            String dateStr = commande.getDate().toLocalDateTime().toLocalDate().toString();
            grouped.computeIfAbsent(dateStr, k -> new ArrayList<>()).add(commande);
        }

        return grouped;
    }

    @Override
    public Commande getCommandeById(int id) {
        return findById(id);
    }

    @Override
    public List<Map<String, Object>> getCommandeLineByCommandeId(int id) {
        return db.executeQuery("SELECT * FROM lignecommande WHERE idCommande = ?", id);
    }

    @Override
    public List<Commande> getCommandesByClientId(int clientId) {
        List<Commande> commandes = new ArrayList<>();
        Map<String, Object> where = new HashMap<>();
        where.put("idClient", clientId);

        List<Map<String, Object>> results = db.table("commande").select(where);

        for (Map<String, Object> row : results) {
            commandes.add(mapRowToCommande(row));
        }

        return commandes;
    }

    private Commande mapRowToCommande(Map<String, Object> row) {
        Integer idCommande = row.get("idCommande") != null ? (Integer) row.get("idCommande") : 0;
        Timestamp date = (Timestamp) row.get("date");
        String statut = (String) row.get("statut");
        Double total = row.get("total") != null ? (Double) row.get("total") : 0.0;
        Integer idClient = row.get("idClient") != null ? (Integer) row.get("idClient") : 0;

        return new Commande(
                idCommande,
                date,
                statut,
                total,
                idClient
        );
    }
}
