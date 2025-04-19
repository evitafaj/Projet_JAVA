package dao.impl;

import dao.CommandeDAO;
import model.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAOImpl implements CommandeDAO {

    private final Connection conn;

    public CommandeDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean ajouterCommande(Commande commande) {
        String sql = "INSERT INTO Commande (date, statut, total, idClient) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(commande.getDate().getTime()));
            stmt.setString(2, commande.getStatut());
            stmt.setDouble(3, commande.getTotal());
            stmt.setInt(4, commande.getIdClient());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifierCommande(Commande commande) {
        String sql = "UPDATE Commande SET date = ?, statut = ?, total = ?, idClient = ? WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(commande.getDate().getTime()));
            stmt.setString(2, commande.getStatut());
            stmt.setDouble(3, commande.getTotal());
            stmt.setInt(4, commande.getIdClient());
            stmt.setInt(5, commande.getIdCommande());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur modification commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean supprimerCommande(int idCommande) {
        String sql = "DELETE FROM Commande WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Commande getCommandeById(int idCommande) {
        String sql = "SELECT * FROM Commande WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Commande(
                        rs.getInt("idCommande"),
                        rs.getDate("date"),
                        rs.getString("statut"),
                        rs.getDouble("total"),
                        rs.getInt("idClient")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération commande : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Commande> getCommandesByClient(int idClient) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande WHERE idClient = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                commandes.add(new Commande(
                        rs.getInt("idCommande"),
                        rs.getDate("date"),
                        rs.getString("statut"),
                        rs.getDouble("total"),
                        rs.getInt("idClient")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération commandes client : " + e.getMessage());
        }
        return commandes;
    }

    @Override
    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                commandes.add(new Commande(
                        rs.getInt("idCommande"),
                        rs.getDate("date"),
                        rs.getString("statut"),
                        rs.getDouble("total"),
                        rs.getInt("idClient")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération commandes : " + e.getMessage());
        }
        return commandes;
    }
}
