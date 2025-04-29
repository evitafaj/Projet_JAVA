package Vue;

import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class FenetreClient extends JFrame {
    private DaoFactory daoFactory;

    public FenetreClient(Client client, DaoFactory daoFactory) {
        this.daoFactory = daoFactory;

        setTitle("Espace Client - Zanvia️");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Message d'accueil en haut
        JLabel bienvenue = new JLabel("👋 Bienvenue " + client.getPrenom() + " " + client.getNom(), SwingConstants.CENTER);
        bienvenue.setFont(new Font("SansSerif", Font.BOLD, 22));
        bienvenue.setForeground(new Color(70, 70, 70));
        add(bienvenue, BorderLayout.NORTH);

        // 🔥 Menu central avec fond pastel
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        menuPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond rose pastel
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔥 Boutons arrondis modernes
        CustomButton profilBtn = new CustomButton("👤 Mon profil");
        CustomButton commandesBtn = new CustomButton("📦 Mes commandes");
        CustomButton catalogueBtn = new CustomButton("🛍️ Voir le catalogue");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        menuPanel.add(catalogueBtn);
        menuPanel.add(profilBtn);
        menuPanel.add(commandesBtn);
        menuPanel.add(retourBtn);

        add(menuPanel, BorderLayout.CENTER);

        // 🎯 Actions des boutons
        profilBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Nom : " + client.getNom() +
                            "\nPrénom : " + client.getPrenom() +
                            "\nAdresse : " + client.getAdresse() +
                            "\nEmail : " + client.getEmail(),
                    "👤 Mon Profil",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        commandesBtn.addActionListener(e -> {
            new FenetreMesCommandes(client, daoFactory);
        });

        catalogueBtn.addActionListener(e -> {
            new FenetreCatalogueClient(daoFactory, client);
        });

        retourBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
