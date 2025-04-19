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
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton afficherBtn = new JButton("1. Afficher tous les clients");
        JButton ajouterBtn = new JButton("2. Ajouter un nouveau client");
        JButton chercherBtn = new JButton("3. Chercher un client par ID");
        JButton modifierBtn = new JButton("4. Modifier un client");
        JButton supprimerBtn = new JButton("5. Supprimer un client");
        JButton retourBtn = new JButton("⬅ Retour");

        add(afficherBtn);
        add(ajouterBtn);
        add(chercherBtn);
        add(modifierBtn);
        add(supprimerBtn);
        add(retourBtn);

        ClientDAO clientDAO = new ClientDAOImpl(daoFactory);

        // 1. Afficher tous les clients
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

        // 2. Ajouter un nouveau client
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
            }
        });

        // 3. Chercher un client par ID
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

        // 4. Modifier un client
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
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Client introuvable.");
                }
            }
        });

        // 5. Supprimer un client
        supprimerBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du client à supprimer :");
            if (idStr != null) {
                Client c = clientDAO.chercher(Integer.parseInt(idStr));
                if (c != null) {
                    clientDAO.supprimer(c);
                    JOptionPane.showMessageDialog(this, "🗑️ Client supprimé.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Client introuvable.");
                }
            }
        });

        // 6. Retour
        retourBtn.addActionListener(e -> {
            dispose(); // Ferme cette fenêtre
        });

        setVisible(true);
    }
}
