package DAO;

import Modele.Avis;
import java.sql.*;
import java.util.*;

public class AvisDAOImpl implements AvisDAO {
    private DaoFactory daoFactory;

    public AvisDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouterAvis(Avis avis) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO avis(idProduit, commentaire, note) VALUES (?, ?, ?)")) {
            stmt.setInt(1, avis.getProduitId());
            stmt.setString(2, avis.getCommentaire());
            stmt.setInt(3, avis.getNote());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerAvis(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM avis WHERE idAvis=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Avis getAvisById(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM avis WHERE idAvis=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Avis(
                        rs.getInt("idAvis"),
                        rs.getInt("idProduit"),
                        rs.getString("commentaire"),
                        rs.getInt("note"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Avis> getAvisByProduit(int produitId) {
        System.out.println("🔍 Chargement des avis pour idProduit = " + produitId); // pour debug
        List<Avis> liste = new ArrayList<>();
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM avis WHERE idProduit=?")) {
            stmt.setInt(1, produitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                liste.add(new Avis(
                        rs.getInt("idAvis"),
                        rs.getInt("idProduit"),
                        rs.getString("commentaire"),
                        rs.getInt("note")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public List<Avis> getAllAvis() {
        List<Avis> liste = new ArrayList<>();
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM avis")) {
            while (rs.next()) {
                liste.add(new Avis(
                        rs.getInt("idAvis"),
                        rs.getInt("idProduit"),
                        rs.getString("commentaire"),
                        rs.getInt("note")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
