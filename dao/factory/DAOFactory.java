package dao.factory;

import dao.*;
import dao.impl.*;
import util.ConnexionDB;

import java.sql.Connection;

public class DAOFactory {

    private static final Connection conn = ConnexionDB.getConnexion();

    public static CommandeDAO getCommandeDAO() {
        return new CommandeDAOImpl(conn);
    }

    public static LigneCommandeDAO getLigneCommandeDAO() {
        return new LigneCommandeDAOImpl(conn);
    }

    public static PaiementDAO getPaiementDAO() {
        return new PaiementDAOImpl(conn);
    }

    public static LivraisonDAO getLivraisonDAO() {
        return new LivraisonDAOImpl(conn);
    }

}
