package DAO;

import Modele.Reduction;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReductionDAOImpl implements ReductionDAO {
    private DaoFactory daoFactory;

    public ReductionDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouterReduction(Reduction red) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO reduction(pourcentage, dateDebut, dateFin, idProduit) VALUES(?, ?, ?, ?)")) {
            stmt.setDouble(1, red.getPourcentage());
            stmt.setDate(2, Date.valueOf(red.getDateDebut()));
            stmt.setDate(3, Date.valueOf(red.getDateFin()));
            stmt.setInt(4, red.getIdProduit());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerReduction(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM reduction WHERE idReduction=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierReduction(Reduction red) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE reduction SET pourcentage=?, dateDebut=?, dateFin=?, idProduit=? WHERE idReduction=?")) {
            stmt.setDouble(1, red.getPourcentage());
            stmt.setDate(2, Date.valueOf(red.getDateDebut()));
            stmt.setDate(3, Date.valueOf(red.getDateFin()));
            stmt.setInt(4, red.getIdProduit());
            stmt.setInt(5, red.getIdReduction());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reduction getReductionById(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reduction WHERE idReduction=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reduction(
                        rs.getInt("idReduction"),
                        rs.getDouble("pourcentage"),
                        rs.getDate("dateDebut").toLocalDate(),
                        rs.getDate("dateFin").toLocalDate(),
                        rs.getInt("idProduit")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reduction> getAllReductions() {
        List<Reduction> liste = new ArrayList<>();
        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM reduction")) {
            while (rs.next()) {
                liste.add(new Reduction(
                        rs.getInt("idReduction"),
                        rs.getDouble("pourcentage"),
                        rs.getDate("dateDebut").toLocalDate(),
                        rs.getDate("dateFin").toLocalDate(),
                        rs.getInt("idProduit")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
