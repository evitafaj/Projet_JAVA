package Vue;

import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class FenetreClient extends JFrame {

    public FenetreClient(Client client) {
        setTitle("Espace Client - Zanvia️");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Message d'accueil
        JLabel bienvenue = new JLabel("Bienvenue " + client.getPrenom() + " " + client.getNom(), SwingConstants.CENTER);
        bienvenue.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(bienvenue, BorderLayout.NORTH);

        // Menu des actions
        JPanel menuPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton profilBtn = new JButton("👤 Mon profil");
        JButton commandesBtn = new JButton("📦 Mes commandes");

        menuPanel.add(profilBtn);
        menuPanel.add(commandesBtn);

        add(menuPanel, BorderLayout.CENTER);

        // Action sur "Mon profil"
        profilBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Nom : " + client.getNom() +
                            "\nPrénom : " + client.getPrenom() +
                            "\nAdresse : " + client.getAdresse() +
                            "\nEmail : " + client.getEmail(),
                    "Profil Client",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Action sur "Mes commandes" (à compléter avec l’affichage réel ensuite)
        commandesBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "📦 Tu verras ici toutes tes commandes (à implémenter 👷‍♀️)",
                    "Mes Commandes",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);
    }
}
