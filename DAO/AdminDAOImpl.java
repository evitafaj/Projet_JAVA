package DAO;

// import des packages
import Modele.Admin;
import java.sql.*;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * AdminDao.
 */
public class AdminDAOImpl implements AdminDAO{
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public AdminDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Admin> getAll() {
        ArrayList<Admin> listeAdmins = new ArrayList<Admin >();

        /*
            Récupérer la liste des admins de la base de données dans listeAdmin
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * FROM Admin");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table admins dans la base de données
                int id= resultats.getInt("idAdmin");
                String nom = resultats.getString("nom");
                String prenom = resultats.getString("prenom");
                String adresse = resultats.getString("adresse");
                String email = resultats.getString("email");
                String motDePasse = resultats.getString("motDePasse");

                // instancier un objet de Admin avec ces 3 champs en paramètres
                Admin admins = new Admin(id, nom, prenom, adresse, email,motDePasse );
                // ajouter ce admin à listeAdmins
                listeAdmins.add(admins);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Impossicle de récupérer la liste des administrateurs.");
        }

        return listeAdmins;
    }

    /**
     Ajouter un nouveau admin en paramètre dans la base de données
     @params : admin = objet de Admin à insérer dans la base de données
     */
    @Override
    public void ajouter(Admin admin) {
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "INSERT INTO Admin(nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getMotDePasse());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'administrateur impossible");
        }
    }

    @Override
    public Admin chercher(int id)  {
        Admin admin = null;

        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM Admin WHERE idAdmin = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("idAdmin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Administrateur non trouvé dans la base de données");
        }
        return admin;
    }

    @Override
    public Admin modifier(Admin admin) {
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "UPDATE Admin SET nom = ?, prenom = ?, email = ?, motDePasse = ? WHERE idAdmin = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getMotDePasse());
            ps.setInt(5, admin.getIdAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification du client impossible");
        }
        return admin;
    }

    @Override
    public void supprimer(Admin admin) {
        try {
            Connection connexion = daoFactory.getConnection();

            // Supprimer d'abord toutes les admins
            Connection connection = daoFactory.getConnection();
            String sql = "DELETE FROM Admin WHERE idAdmin = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, admin.getIdAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du client impossible");
        }
    }

    @Override
    public Admin seConnecter(String email, String motDePasse) {
        Admin admin = null;
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "SELECT * FROM Admin WHERE email = ? AND motDePasse = ?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("idAdmin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la tentative de connexion de l'administrateur.");
        }

        return admin;
    }
}


