package dao.impl;

import dao.PaiementDAO;
import model.Paiement;

import java.sql.*;

public class PaiementDAOImpl implements PaiementDAO {

    private final Connection conn;

    public PaiementDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean ajouterPaiement(Paiement paiement) {
        String sql = "INSERT INTO Paiement (montant, date, mode, idCommande) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, paiement.getMontant());
            stmt.setDate(2, new java.sql.Date(paiement.getDate().getTime()));
            stmt.setString(3, paiement.getMode());
            stmt.setInt(4, paiement.getIdCommande());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout paiement : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Paiement getPaiementByCommande(int idCommande) {
        String sql = "SELECT * FROM Paiement WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paiement(
                        rs.getInt("idPaiement"),
                        rs.getDouble("montant"),
                        rs.getDate("date"),
                        rs.getString("mode"),
                        rs.getInt("idCommande")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération paiement : " + e.getMessage());
        }
        return null;
    }
}

