package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private static String url;
    private String username;
    private String password;

    // Constructeur
    public DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // Méthode pour obtenir une instance de DaoFactory
    public static DaoFactory getInstance(String database, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de connexion au driver JDBC");
        }

        url = "jdbc:mysql://localhost:8889/" + database + "?useSSL=false&serverTimezone=UTC";
        return new DaoFactory(url, username, password);
    }

    // Méthode pour récupérer une connexion
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Récupération des différents DAO
    public ClientDAO getClientDAO() {
        return new ClientDAOImpl(this);
    }

    public AdminDAO getAdminDAO() {
        return new AdminDAOImpl(this);
    }

    public ProduitDAO getProduitDAO() {
        return new ProduitDAOImpl(this);
    }

    public CommandeDAO getCommandeDAO() {
        return new CommandeDAOImpl();
    }

    public PaymentDAO getPaymentDAO() {
        return new PaymentDAOImpl();
    }

    public TransportDAO getTransportDAO() {
        return new TransportDAOImpl();
    }

    // Déconnexion propre
    public void disconnect() {
        try (Connection connexion = this.getConnection()) {
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la déconnexion.");
        }
    }
}
