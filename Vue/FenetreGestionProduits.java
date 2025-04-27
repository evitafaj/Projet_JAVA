package Vue;

import DAO.CategorieDAO;
import DAO.CategorieDAOImpl;
import DAO.DaoFactory;
import DAO.ProduitDAO;
import DAO.ProduitDAOImpl;
import Modele.Categorie;
import Modele.Produit;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetreGestionProduits extends JFrame {

    public FenetreGestionProduits(DaoFactory daoFactory) {
        setTitle("Gestion des Produits");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("📦 Gestion des Produits", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Menu central avec fond pastel
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔥 Boutons modernes
        CustomButton afficherBtn = new CustomButton("📋 Afficher tous les produits");
        CustomButton ajouterBtn = new CustomButton("➕ Ajouter un produit");
        CustomButton chercherBtn = new CustomButton("🔍 Chercher un produit");
        CustomButton modifierBtn = new CustomButton("✏️ Modifier un produit");
        CustomButton supprimerBtn = new CustomButton("🗑️ Supprimer un produit");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        menuPanel.add(afficherBtn);
        menuPanel.add(ajouterBtn);
        menuPanel.add(chercherBtn);
        menuPanel.add(modifierBtn);
        menuPanel.add(supprimerBtn);
        menuPanel.add(retourBtn);

        add(menuPanel, BorderLayout.CENTER);


        ProduitDAO produitDAO = new ProduitDAOImpl(daoFactory);
        CategorieDAO categorieDAO = new CategorieDAOImpl(daoFactory);

        // 1. Afficher tous les produits
        afficherBtn.addActionListener(e -> {
            List<Produit> produits = produitDAO.getAll();
            StringBuilder sb = new StringBuilder("📋 Liste des produits :\n\n");
            for (Produit p : produits) {
                sb.append("• ").append(p.getIdProduit()).append(" | ")
                        .append(p.getNom()).append(" | ")
                        .append(p.getDescription()).append(" | ")
                        .append(p.getPrixUnitaire()).append(" € | ")
                        .append("Vrac: ").append(p.getPrixVrac()).append(" € | ")
                        .append("Seuil: ").append(p.getSeuilVrac()).append(" | ")
                        .append("Catégorie ID: ").append(p.getIdCategorie()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Tous les produits", JOptionPane.INFORMATION_MESSAGE);
        });

        // 2. Ajouter un produit
        ajouterBtn.addActionListener(e -> {
            JTextField nom = new JTextField();
            JTextField description = new JTextField();
            JTextField prixVrac = new JTextField();
            JTextField prixUnitaire = new JTextField();
            JTextField stock = new JTextField();
            JComboBox<Categorie> categorieBox = new JComboBox<>();
            List<Categorie> categories = categorieDAO.getAllCategories();

            if (categories.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Aucune catégorie disponible. Veuillez en créer une d'abord.");
                return;
            }

            for (Categorie cat : categories) {
                categorieBox.addItem(cat);
            }

            Object[] fields = {
                    "Nom :", nom,
                    "Description :", description,
                    "Prix unitaire :", prixUnitaire,
                    "Prix Vrac :", prixVrac,
                    "Stock :", stock,
                    "Catégorie :", categorieBox
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Ajouter un produit", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Categorie selectedCategorie = (Categorie) categorieBox.getSelectedItem();
                    if (selectedCategorie == null) {
                        JOptionPane.showMessageDialog(this, "❌ Aucune catégorie sélectionnée.");
                        return;
                    }
                    int idCategorie = selectedCategorie.getId();

                    Produit nouveau = new Produit(
                            0,
                            nom.getText(),
                            description.getText(),
                            Double.parseDouble(prixUnitaire.getText()),
                            Double.parseDouble(prixVrac.getText()),
                            Integer.parseInt(stock.getText()),
                            idCategorie
                    );
                    produitDAO.ajouter(nouveau);
                    JOptionPane.showMessageDialog(this, "✅ Produit ajouté !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Erreur : " + ex.getMessage());
                }
            }
        });

        // 3. Chercher un produit par ID
        chercherBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du produit à chercher :");
            if (idStr != null) {
                Produit p = produitDAO.chercher(Integer.parseInt(idStr));
                if (p != null) {
                    JOptionPane.showMessageDialog(this,
                            "ID : " + p.getIdProduit() + "\nNom : " + p.getNom() +
                                    "\nPrix : " + p.getPrixUnitaire() +
                                    "\nStock : " + p.getSeuilVrac() +
                                    "\nCatégorie : " + p.getIdCategorie(),
                            "Produit trouvé", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Produit introuvable.");
                }
            }
        });

        // 4. Modifier un produit
        modifierBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du produit à modifier :");
            if (idStr != null) {
                try {
                    int idProduit = Integer.parseInt(idStr);
                    Produit p = produitDAO.chercher(idProduit);

                    if (p != null) {
                        JTextField nom = new JTextField(p.getNom());
                        JTextField prix = new JTextField(String.valueOf(p.getPrixUnitaire()));
                        JTextField stock = new JTextField(String.valueOf(p.getSeuilVrac()));
                        JTextField description = new JTextField(p.getDescription());

                        JComboBox<Categorie> categorieBox = new JComboBox<>();
                        List<Categorie> categories = categorieDAO.getAllCategories();

                        if (categories.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "❌ Aucune catégorie disponible pour la modification.");
                            return;
                        }

                        Categorie selectedCategorie = null;
                        for (Categorie cat : categories) {
                            categorieBox.addItem(cat);
                            if (cat.getId() == p.getIdCategorie()) {
                                selectedCategorie = cat;
                            }
                        }

                        if (selectedCategorie != null) {
                            categorieBox.setSelectedItem(selectedCategorie);
                        }

                        Object[] fields = {
                                "Nom :", nom,
                                "Description :", description,
                                "Prix unitaire :", prix,
                                "Stock :", stock,
                                "Catégorie :", categorieBox
                        };

                        int option = JOptionPane.showConfirmDialog(this, fields, "Modifier produit", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            p.setNom(nom.getText());
                            p.setDescription(description.getText());
                            p.setPrixUnitaire(Double.parseDouble(prix.getText()));
                            p.setSeuilVrac(Integer.parseInt(stock.getText()));
                            Categorie nouvelleCategorie = (Categorie) categorieBox.getSelectedItem();
                            if (nouvelleCategorie != null) {
                                p.setIdCategorie(nouvelleCategorie.getId());
                            }

                            produitDAO.modifier(p);
                            JOptionPane.showMessageDialog(this, "✅ Produit modifié.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Produit introuvable.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Erreur : " + ex.getMessage());
                }
            }
        });

        // 5. Supprimer un produit
        supprimerBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID du produit à supprimer :");
            if (idStr != null) {
                Produit p = produitDAO.chercher(Integer.parseInt(idStr));
                if (p != null) {
                    produitDAO.supprimer(p);
                    JOptionPane.showMessageDialog(this, "🗑️ Produit supprimé.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Produit introuvable.");
                }
            }
        });

        // 6. Retour
        retourBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}