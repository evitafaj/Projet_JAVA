package Vue;

import DAO.DaoFactory;

import javax.swing.*;
import java.awt.*;

// La fenetre qui montre la page Accueil
public class FenetreAccueil extends JFrame {
    public FenetreAccueil(DaoFactory daoFactory) {
        setTitle("Zanvia Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("Bienvenue sur la page d'accueil !", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 30));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Centre : les boutons
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        centerPanel.setBackground(new Color(255, 228, 225)); // 🌸 Rose pastel
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        // 🔥 Créer les boutons arrondis
        CustomButton buttonConnexion = new CustomButton("🔐 Se connecter");
        CustomButton buttonInscriptionClient = new CustomButton("🛍️ Créer un compte client");
        CustomButton buttonInscriptionAdmin = new CustomButton("🛠️ Créer un compte admin");

        centerPanel.add(buttonConnexion);
        centerPanel.add(buttonInscriptionClient);
        centerPanel.add(buttonInscriptionAdmin);

        add(centerPanel, BorderLayout.CENTER);

        // 🎯 Actions
        buttonConnexion.addActionListener(e -> {
            dispose();
            new FenetreConnection(daoFactory);
        });

        buttonInscriptionClient.addActionListener(e -> {
            dispose();
            new FenetreInscription(daoFactory);
        });

        buttonInscriptionAdmin.addActionListener(e -> {
            dispose();
            new FenetreInscriptionAdmin(daoFactory);
        });

        setVisible(true);
    }
}
