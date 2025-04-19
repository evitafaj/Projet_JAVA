package DAO;

// import des packages
import Modele.Client;
import java.sql.*;
import java.util.ArrayList;
import DAO.DaoFactory;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * ClientDao.
 */
public class ClientDAOImpl implements ClientDAO {
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ClientDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Client> getAll() {
        ArrayList<Client> listeClients = new ArrayList<Client>();

        /*
            Récupérer la liste des clients de la base de données dans listeClients
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from Client");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int id= resultats.getInt("idClient");
                String nom = resultats.getString("nom");
                String prenom = resultats.getString("prenom");
                String adresse = resultats.getString("adresse");
                String email = resultats.getString("email");
                String motDePasse = resultats.getString("motDePasse");

                // instancier un objet de Produit avec ces 3 champs en paramètres
                Client client = new Client(id, nom, prenom, adresse, email,motDePasse );
                // ajouter ce produit à listeProduits
                listeClients.add(client);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Impossicle de récupérer la liste des clients.");
        }

        return listeClients;
    }

    /**
     Ajouter un nouveau client en paramètre dans la base de données
     @params : client = objet de Client à insérer dans la base de données
     */
    @Override
    public void ajouter(Client client) {
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "INSERT INTO Client(nom, prenom, adresse, email, motDePasse) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getMotDePasse());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du client impossible");
        }
    }

    /**
     * Permet de chercher et récupérer un objet de Client dans la base de données via son id en paramètre
     * @param : id
     * @return : objet de classe Client cherché et retourné
     */

    @Override
    public Client chercher(int id)  {
        Client client = null;

        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM Client WHERE idClient = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("idClient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("motDePasse")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client non trouvé dans la base de données");
        }
        return client;
    }

    /**
     * Permet de modifier les données du nom de l'objet de la classe Client en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     * @param : client = objet en paramètre de la classe Client à mettre à jour à partir de son id
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    @Override
    public Client modifier(Client client) {
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "UPDATE Client SET nom = ?, prenom = ?, adresse = ?, email = ?, motDePasse = ? WHERE idClient = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getMotDePasse());
            ps.setInt(6, client.getIdClient());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification du client impossible");
        }
        return client;
    }

    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    @Override
    public void supprimer(Client client) {
        try {
            Connection connexion = daoFactory.getConnection();

            // Supprimer d'abord toutes les commandes liées à ce client
            String deleteCommandes = "DELETE FROM Commande WHERE idClient = ?";
            PreparedStatement ps1 = connexion.prepareStatement(deleteCommandes);
            ps1.setInt(1, client.getIdClient());
            ps1.executeUpdate();

            // Puis supprimer le client
            String deleteClient = "DELETE FROM Client WHERE idClient = ?";
            PreparedStatement ps2 = connexion.prepareStatement(deleteClient);
            ps2.setInt(1, client.getIdClient());
            ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du client impossible");
        }
    }

    @Override
    public Client seConnecter(String email, String motDePasse) {
        Client client = null;
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM Client WHERE email = ? AND motDePasse = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("idClient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("motDePasse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la tentative de connexion du client.");
        }

        return client;
    }
}


