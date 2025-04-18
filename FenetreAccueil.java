package Vue;

import DAO.DaoFactory;

import javax.swing.*;
import java.awt.*;

public class FenetreAccueil extends JFrame {
    public FenetreAccueil(DaoFactory daoFactory) {
        setTitle("Bienvenue sur Zanvia ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titre = new JLabel("Bienvenue !", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton btnConnexion = new JButton("🔐 Se connecter");
        JButton btnInscriptionClient = new JButton("🧍 Créer un compte client");
        JButton btnInscriptionAdmin = new JButton("👩‍💼 Créer un compte admin");

        add(titre);
        add(btnConnexion);
        add(btnInscriptionClient);
        add(btnInscriptionAdmin);

        btnConnexion.addActionListener(e -> {
            dispose();
            new FenetreConnection(daoFactory);
        });

        btnInscriptionClient.addActionListener(e -> {
            dispose();
            new FenetreInscription(daoFactory);
        });

        btnInscriptionAdmin.addActionListener(e -> {
            dispose();
            new FenetreInscriptionAdmin(daoFactory);
        });

        setVisible(true);
    }
}
