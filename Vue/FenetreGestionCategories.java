package Vue;

import DAO.CategorieDAO;
import DAO.CategorieDAOImpl;
import DAO.DaoFactory;
import Modele.Categorie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetreGestionCategories extends JFrame {

    public FenetreGestionCategories(DaoFactory daoFactory) {
        setTitle("Gestion des Catégories");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("📂 Gestion des Catégories", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Panneau des boutons au centre
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔥 Création des boutons CustomButton
        CustomButton afficherBtn = new CustomButton("📋 Afficher toutes les catégories");
        CustomButton ajouterBtn = new CustomButton("➕ Ajouter une catégorie");
        CustomButton chercherBtn = new CustomButton("🔍 Chercher une catégorie");
        CustomButton modifierBtn = new CustomButton("✏️ Modifier une catégorie");
        CustomButton supprimerBtn = new CustomButton("🗑️ Supprimer une catégorie");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        menuPanel.add(afficherBtn);
        menuPanel.add(ajouterBtn);
        menuPanel.add(chercherBtn);
        menuPanel.add(modifierBtn);
        menuPanel.add(supprimerBtn);
        menuPanel.add(retourBtn);

        add(menuPanel, BorderLayout.CENTER);

        CategorieDAO categorieDAO = new CategorieDAOImpl(daoFactory);

        // 🎯 Action 1. Afficher toutes les catégories
        afficherBtn.addActionListener(e -> {
            List<Categorie> categories = categorieDAO.getAllCategories();
            StringBuilder sb = new StringBuilder("📂 Liste des catégories :\n\n");
            for (Categorie c : categories) {
                sb.append("• ID ").append(c.getId()).append(" - ").append(c.getNom()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Toutes les catégories", JOptionPane.INFORMATION_MESSAGE);
        });

        // 🎯 Action 2. Ajouter une catégorie
        ajouterBtn.addActionListener(e -> {
            JTextField nomField = new JTextField();
            Object[] fields = {"Nom de la catégorie :", nomField};
            int option = JOptionPane.showConfirmDialog(this, fields, "Ajouter une catégorie", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Categorie nouvelle = new Categorie(0, nomField.getText());
                categorieDAO.ajouterCategorie(nouvelle);
                JOptionPane.showMessageDialog(this, "✅ Catégorie ajoutée !");
                afficherBtn.doClick(); // 🔥 Actualiser automatiquement
            }
        });

        // 🎯 Action 3. Chercher une catégorie
        chercherBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID de la catégorie à chercher :");
            if (idStr != null) {
                Categorie c = categorieDAO.getCategorieById(Integer.parseInt(idStr));
                if (c != null) {
                    JOptionPane.showMessageDialog(this, "ID : " + c.getId() + "\nNom : " + c.getNom(),
                            "Catégorie trouvée", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Catégorie introuvable.");
                }
            }
        });

        // 🎯 Action 4. Modifier une catégorie
        modifierBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID de la catégorie à modifier :");
            if (idStr != null) {
                Categorie c = categorieDAO.getCategorieById(Integer.parseInt(idStr));
                if (c != null) {
                    JTextField nomField = new JTextField(c.getNom());
                    Object[] fields = {"Nouveau nom :", nomField};
                    int option = JOptionPane.showConfirmDialog(this, fields, "Modifier catégorie", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        c.setNom(nomField.getText());
                        categorieDAO.modifierCategorie(c);
                        JOptionPane.showMessageDialog(this, "✅ Catégorie modifiée !");
                        afficherBtn.doClick(); // 🔥 Rafraîchir aussi
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Catégorie introuvable.");
                }
            }
        });

        // 🎯 Action 5. Supprimer une catégorie
        supprimerBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID de la catégorie à supprimer :");
            if (idStr != null) {
                Categorie c = categorieDAO.getCategorieById(Integer.parseInt(idStr));
                if (c != null) {
                    categorieDAO.supprimerCategorie(c.getId());
                    JOptionPane.showMessageDialog(this, "🗑️ Catégorie supprimée.");
                    afficherBtn.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Catégorie introuvable.");
                }
            }
        });

        // 🎯 Action 6. Retour
        retourBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
