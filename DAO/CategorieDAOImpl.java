package DAO;

import Modele.Categorie;
import java.sql.*;
import java.util.*;

public class CategorieDAOImpl implements CategorieDAO {
    private DaoFactory daoFactory;

    public CategorieDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void ajouterCategorie(Categorie cat) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO categorie(nom) VALUES(?)")) {
            stmt.setString(1, cat.getNom());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerCategorie(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorie WHERE idCategorie=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierCategorie(Categorie cat) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE categorie SET nom=? WHERE idCategorie=?")) {
            stmt.setString(1, cat.getNom());
            stmt.setInt(2, cat.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Categorie getCategorieById(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categorie WHERE idCategorie=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("idCategorie"), rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Categorie> getAllCategories() {
        List<Categorie> liste = new ArrayList<>();
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categorie")) {
            while (rs.next()) {
                System.out.println("Catégorie trouvée : id=" + rs.getInt("idCategorie") + ", nom=" + rs.getString("nom")); // ✅ correction ici
                liste.add(new Categorie(rs.getInt("idCategorie"), rs.getString("nom")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
