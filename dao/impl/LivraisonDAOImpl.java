package dao.impl;

import dao.LivraisonDAO;
import model.Livraison;

import java.sql.*;

public class LivraisonDAOImpl implements LivraisonDAO {

    private final Connection conn;

    public LivraisonDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean ajouterLivraison(Livraison livraison) {
        String sql = "INSERT INTO Livraison (adresse, dateLivraison, statut, idCommande) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livraison.getAdresse());
            stmt.setDate(2, new java.sql.Date(livraison.getDateLivraison().getTime()));
            stmt.setString(3, livraison.getStatut());
            stmt.setInt(4, livraison.getIdCommande());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout livraison : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Livraison getLivraisonByCommande(int idCommande) {
        String sql = "SELECT * FROM Livraison WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Livraison(
                        rs.getInt("idLivraison"),
                        rs.getString("adresse"),
                        rs.getDate("dateLivraison"),
                        rs.getString("statut"),
                        rs.getInt("idCommande")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération livraison : " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean mettreAJourStatutLivraison(int idCommande, String nouveauStatut) {
        String sql = "UPDATE Livraison SET statut = ? WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idCommande);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour statut livraison : " + e.getMessage());
            return false;
        }
    }
}
