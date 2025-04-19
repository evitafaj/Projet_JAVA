package dao.impl;

import dao.LigneCommandeDAO;
import model.LigneCommande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeDAOImpl implements LigneCommandeDAO {



    private final Connection conn;

    public LigneCommandeDAOImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean ajouterLigneCommande(LigneCommande ligne) {
        String sql = "INSERT INTO LigneCommande (quantite, prixTotalLigne, idCommande, idProduit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ligne.getQuantite());
            stmt.setDouble(2, ligne.getPrixTotalLigne());
            stmt.setInt(3, ligne.getIdCommande());
            stmt.setInt(4, ligne.getIdProduit());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout ligne commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean supprimerLigneCommande(int idLigneCommande) {
        String sql = "DELETE FROM LigneCommande WHERE idLigneCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLigneCommande);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression ligne commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<LigneCommande> getLignesByCommande(int idCommande) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM LigneCommande WHERE idCommande = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lignes.add(new LigneCommande(
                        rs.getInt("idLigneCommande"),
                        rs.getInt("quantite"),
                        rs.getDouble("prixTotalLigne"),
                        rs.getInt("idCommande"),
                        rs.getInt("idProduit")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération lignes commande : " + e.getMessage());
        }
        return lignes;
    }

    @Override
    public double getPrixUnitaireProduit(int idProduit) {
        String sql = "SELECT prixUnitaire FROM Produit WHERE idProduit = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("prixUnitaire");
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération prix unitaire : " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public double getPrixVracProduit(int idProduit) {
        String sql = "SELECT prixVrac FROM Produit WHERE idProduit = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("prixVrac");
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération prix vrac : " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public int getSeuilVracProduit(int idProduit) {
        String sql = "SELECT seuilVrac FROM Produit WHERE idProduit = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("seuilVrac");
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération seuil vrac : " + e.getMessage());
        }
        return 0;
    }
}
