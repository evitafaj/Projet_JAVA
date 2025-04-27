package Vue;

import DAO.ClientDAO;
import DAO.ClientDAOImpl;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FenetreGestionClients extends JFrame {

    public FenetreGestionClients(DaoFactory daoFactory) {
        setTitle("Gestion des Clients");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("👥 Gestion des Clients", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Panneau des boutons au centre
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔥 Boutons CustomButton modernes
        CustomButton afficherBtn = new CustomButton("📋 Afficher tous les clients");
        CustomButton ajouterBtn = new CustomButton("➕ Ajouter un nouveau client");
        CustomButton chercherBtn = new CustomButton("🔍 Chercher un client par ID");
        CustomButton modifierBtn = new CustomButton("✏️ Modifier un client");
        CustomButton supprimerBtn = new CustomButton("🗑️ Supprimer un client");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        menuPanel.add(afficherBtn);
        menuPanel.add(ajouterBtn);
        menuPanel.add(chercherBtn);
        menuPanel.add(modifierBtn);
        menuPanel.add(supprimerBtn);
        menuPanel.add(retourBtn);

        add(menuPanel, BorderLayout.CENTER);

        ClientDAO clientDAO = new ClientDAOImpl(daoFactory);

        // 🎯 1. Afficher tous les clients
        afficherBtn.addActionListener(e -> {
            ArrayList<Client> clients = clientDAO.getAll();
            StringBuilder sb = new StringBuilder("📋 Liste des clients :\n\n");
            for (Client c : clients) {
                sb.append("• ").append(c.getIdClient()).append(" | ")
                        .append(c.getPrenom()).append(" ").append(c.getNom())
                        .append(" | ").append(c.getEmail()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Tous les clients", JOptionPane.INFORMATION_MESSAGE);
        });

        // 🎯 2. Ajouter un nouveau client
        ajouterBtn.addActionListener(e -> {
            JTextField nom = new JTextField();
            JTextField prenom = new JTextField();
            JTextField adresse = new JTextField();
            JTextField email = new JTextField();
            JTextField mdp = new JTextField();
            Object[] message = {
                    "Nom :", nom,
                    "Prénom :", prenom,
                    "Adresse :", adresse,
                    "Email :", email,
                    "Mot de passe :", mdp
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un client", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Client nouveau = new Client(0, nom.getText(), prenom.getText(), adresse.getText(), email.getText(), mdp.getText());
                clientDAO.ajouter(nouveau);
                JOptionPane.showMessageDialog(this, "✅ Client ajouté !");
                afficherBtn.doClick(); // 🔥 Actualiser liste
            }
        });

        // 🎯 3. Chercher un client par ID
        chercherBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du client à chercher :");
            if (idStr != null) {
                Client c = clientDAO.chercher(Integer.parseInt(idStr));
                if (c != null) {
                    JOptionPane.showMessageDialog(this,
                            "ID : " + c.getIdClient() + "\nNom : " + c.getNom() + "\nPrénom : " + c.getPrenom() +
                                    "\nAdresse : " + c.getAdresse() + "\nEmail : " + c.getEmail(),
                            "Client trouvé", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Client introuvable.");
                }
            }
        });

        // 🎯 4. Modifier un client
        modifierBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du client à modifier :");
            if (idStr != null) {
                Client c = clientDAO.chercher(Integer.parseInt(idStr));
                if (c != null) {
                    JTextField nom = new JTextField(c.getNom());
                    JTextField prenom = new JTextField(c.getPrenom());
                    JTextField adresse = new JTextField(c.getAdresse());
                    JTextField email = new JTextField(c.getEmail());
                    JTextField mdp = new JTextField(c.getMotDePasse());
                    Object[] fields = {
                            "Nom :", nom,
                            "Prénom :", prenom,
                            "Adresse :", adresse,
                            "Email :", email,
                            "Mot de passe :", mdp
                    };
                    int option = JOptionPane.showConfirmDialog(this, fields, "Modifier client", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        c.setNom(nom.getText());
                        c.setPrenom(prenom.getText());
                        c.setAdresse(adresse.getText());
                        c.setEmail(email.getText());
                        c.setMotDePasse(mdp.getText());
                        clientDAO.modifier(c);
                        JOptionPane.showMessageDialog(this, "✅ Client modifié.");
                        afficherBtn.doClick(); // 🔥 Actualiser liste
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Client introuvable.");
                }
            }
        });

        // 🎯 5. Supprimer un client
        supprimerBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du client à supprimer :");
            if (idStr != null) {
                Client c = clientDAO.chercher(Integer.parseInt(idStr));
                if (c != null) {
                    clientDAO.supprimer(c);
                    JOptionPane.showMessageDialog(this, "🗑️ Client supprimé.");
                    afficherBtn.doClick(); // 🔥 Actualiser liste
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Client introuvable.");
                }
            }
        });

        // 🎯 6. Retour
        retourBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
