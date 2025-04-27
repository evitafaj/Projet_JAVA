package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class FenetreCatalogueClient extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Categorie> filtreCategorieBox;
    private JTextField rechercheField;
    private ProduitDAO produitDAO;
    private CategorieDAO categorieDAO;
    private DaoFactory daoFactory;

    public FenetreCatalogueClient(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;

        setTitle("Catalogue des Produits");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        produitDAO = new ProduitDAOImpl(daoFactory);
        categorieDAO = new CategorieDAOImpl(daoFactory);

        // 🔥 Titre en haut
        JLabel titre = new JLabel("Catalogue Produits", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Panneau top : Filtres
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel rose clair

        filtreCategorieBox = new JComboBox<>();
        filtreCategorieBox.addItem(new Categorie(0, "Toutes les catégories"));
        for (Categorie c : categorieDAO.getAllCategories()) {
            filtreCategorieBox.addItem(c);
        }

        rechercheField = new JTextField(15);

        topPanel.add(new JLabel("Filtrer par catégorie :"));
        topPanel.add(filtreCategorieBox);
        topPanel.add(new JLabel("Rechercher par nom :"));
        topPanel.add(rechercheField);

        add(topPanel, BorderLayout.NORTH);

        // 🔥 Table au centre
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Nom", "Description", "Prix Unitaire", "Prix Vrac", "Seuil Vrac", "Catégorie"}, 0
        );
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new CustomRenderer()); // Optionnel : couleur prix promo
        JScrollPane tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        // 🔥 Boutons en bas
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel aussi en bas

        CustomButton ajouterBtn = new CustomButton("🛒 Ajouter au panier");
        CustomButton avisBtn = new CustomButton("📝 Donner un avis");
        CustomButton voirAvisBtn = new CustomButton("⭐ Voir les avis");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        // 🎯 Listeners pour les boutons
        retourBtn.addActionListener(e -> dispose());

        avisBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int idProduit = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                new FenetreDonnerAvis(daoFactory, idProduit); // ✅ Ouvre la vraie fenêtre Donner Avis
            } else {
                JOptionPane.showMessageDialog(this, "❗ Sélectionnez un produit avant de donner un avis !");
            }
        });

        voirAvisBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int idProduit = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                new FenetreVoirAvis(daoFactory, idProduit); // ✅ Ouvre la vraie fenêtre Voir Avis
            } else {
                JOptionPane.showMessageDialog(this, "❗ Sélectionnez un produit avant de voir les avis !");
            }
        });

        // Ajout des boutons dans le panneau du bas
        bottomPanel.add(ajouterBtn);
        bottomPanel.add(avisBtn);
        bottomPanel.add(voirAvisBtn);
        bottomPanel.add(retourBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // 🎯 Listeners pour les filtres
        filtreCategorieBox.addActionListener(e -> chargerProduits());
        rechercheField.addActionListener(e -> chargerProduits());

        // 🔥 Chargement initial
        chargerProduits();
        setVisible(true);
    }

    private void chargerProduits() {
        tableModel.setRowCount(0);
        List<Produit> produits = produitDAO.getAll();
        Categorie selectedCat = (Categorie) filtreCategorieBox.getSelectedItem();
        String motCle = rechercheField.getText().trim().toLowerCase();

        if (selectedCat != null && selectedCat.getId() != 0) {
            produits = produits.stream()
                    .filter(p -> p.getIdCategorie() == selectedCat.getId())
                    .collect(Collectors.toList());
        }

        if (!motCle.isEmpty()) {
            produits = produits.stream()
                    .filter(p -> p.getNom().toLowerCase().contains(motCle))
                    .collect(Collectors.toList());
        }

        for (Produit p : produits) {
            String nomCategorie = categorieDAO.getCategorieById(p.getIdCategorie()).getNom();

            double prixFinal = p.getPrixUnitaire();
            String nomProduit = p.getNom();

            ReductionDAO reductionDAO = new ReductionDAOImpl(daoFactory);
            List<Reduction> toutesLesReductions = reductionDAO.getAllReductions();

            for (Reduction r : toutesLesReductions) {
                if (r.getIdProduit() == p.getIdProduit() && r.estActive()) {
                    prixFinal = r.appliquerReduction(p.getPrixUnitaire());
                    nomProduit += " ⭐PROMO";
                    break;
                }
            }

            tableModel.addRow(new Object[]{
                    p.getIdProduit(),
                    nomProduit,
                    p.getDescription(),
                    String.format("%.2f €", prixFinal),
                    p.getPrixVrac() + " €",
                    p.getSeuilVrac(),
                    nomCategorie
            });
        }
    }
}
