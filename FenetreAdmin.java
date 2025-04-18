package Vue;

import DAO.ClientDAO;
import DAO.ClientDAOImpl;
import DAO.DaoFactory;
import Modele.Admin;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FenetreAdmin extends JFrame {
    public FenetreAdmin(Admin admin, DaoFactory daoFactory) {
        setTitle("Espace Admin - Zanvia 🛍️");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel bienvenue = new JLabel("Bienvenue Admin " + admin.getPrenom() + " " + admin.getNom(), SwingConstants.CENTER);
        bienvenue.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(bienvenue, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        // Déclare les boutons
        JButton voirClientsBtn = new JButton("👥 Voir tous les clients");
        JButton produitsBtn = new JButton("📦 Voir les produits");
        JButton statsBtn = new JButton("📊 Statistiques");
        JButton gestionClientsBtn = new JButton("👥 Gérer les clients"); // 👈 AJOUT ICI


        menuPanel.add(voirClientsBtn);
        menuPanel.add(produitsBtn);
        menuPanel.add(gestionClientsBtn); // 👈 AJOUT ICI
        menuPanel.add(statsBtn);

        add(menuPanel, BorderLayout.CENTER);

        // Action : voir tous les clients
        voirClientsBtn.addActionListener(e -> {
            ClientDAO clientDAO = new ClientDAOImpl(daoFactory);
            ArrayList<Client> clients = clientDAO.getAll();
            StringBuilder sb = new StringBuilder("📋 Liste des clients :\n\n");
            for (Client c : clients) {
                sb.append("• ").append(c.getPrenom()).append(" ").append(c.getNom())
                        .append(" | ").append(c.getEmail()).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));

            JOptionPane.showMessageDialog(this, scrollPane, "Tous les clients", JOptionPane.INFORMATION_MESSAGE);
            gestionClientsBtn.addActionListener(ev -> {
                new FenetreGestionClients(daoFactory); // 👈 Ouvre la fenêtre de gestion des clients
            });

        });

        // Action : statistiques (à compléter plus tard)
        statsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "📊 Statistiques à venir !", "Stats", JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);
    }
}
