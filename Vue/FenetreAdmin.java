package Vue;

import DAO.ProduitDAO;
import DAO.ProduitDAOImpl;
import DAO.CategorieDAO;
import DAO.CategorieDAOImpl;
import Vue.ProduitFormPanel;
import DAO.ClientDAO;
import DAO.ClientDAOImpl;
import DAO.DaoFactory;
import Modele.Admin;
import Modele.Client;
import Modele.Categorie;
import Modele.Produit;
import Vue.FenetreGestionCategories;
import Controleur.ProduitControleur;
import Controleur.CategorieControleur;
import Controleur.StatistiquesController;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class FenetreAdmin extends JFrame {
    public FenetreAdmin(Admin admin, DaoFactory daoFactory) {
        setTitle("Zanvia - Espace Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel bienvenue = new JLabel("Bienvenue Admin " + admin.getPrenom() + " " + admin.getNom(), SwingConstants.CENTER);
        bienvenue.setFont(new Font("Arial", Font.BOLD, 18));
        add(bienvenue, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        menuPanel.setBackground(new Color(255, 228, 225)); // 🌸 Rose pastel

        // Déclaration ds buttons pour gerer les comptes
        CustomButton buttonVoirClients = new CustomButton("👥 Voir tous les clients");
        CustomButton buttonProduits = new CustomButton("🛍️ Voir les produits");
        CustomButton buttonStats = new CustomButton("📈 Statistiques");
        CustomButton buttonGestionClients = new CustomButton("🛠️ Gérer les clients");
        CustomButton buttonGestionProduits = new CustomButton("🛠️ Gérer les produits");
        CustomButton buttonGestionCategories = new CustomButton("📂 Gérer les catégories");
        CustomButton buttonGestionReductions = new CustomButton("🏷️ Gérer les réductions");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        menuPanel.add(buttonVoirClients);
        menuPanel.add(buttonProduits);

        menuPanel.add(buttonGestionClients);
        menuPanel.add(buttonGestionProduits);

        menuPanel.add(buttonGestionCategories);
        menuPanel.add(buttonGestionReductions);

        menuPanel.add(buttonStats);
        menuPanel.add(retourBtn);

        add(menuPanel, BorderLayout.CENTER);

        // Les clients
        buttonVoirClients.addActionListener(e -> {
            ClientDAO clientDAO = new ClientDAOImpl(daoFactory);
            ArrayList<Client> clients = clientDAO.getAll();
            StringBuilder sb = new StringBuilder("Liste des clients :\n\n");
            for (Client c : clients) {
                sb.append("• ").append(c.getPrenom()).append(" ").append(c.getNom())
                        .append(" | ").append(c.getEmail()).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));

            JOptionPane.showMessageDialog(this, scrollPane, "Tous les clients", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonGestionClients.addActionListener(ev -> {
            new FenetreGestionClients(daoFactory);
        });

        buttonGestionReductions.addActionListener(e -> {
            new FenetreGestionReductions(daoFactory);
        });

        buttonGestionProduits.addActionListener(e -> {
            new FenetreGestionProduits(daoFactory);
        });

        buttonGestionCategories.addActionListener(e -> {
            new FenetreGestionCategories(daoFactory);
        });

        //Les statistques: plus tard
        buttonStats.addActionListener(e -> {
            new StatistiquesView(new StatistiquesController());
        });

        retourBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
