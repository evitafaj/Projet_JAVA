package Vue;

import Modele.Produit;
import Modele.Categorie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProduitFormPanel extends JPanel {
    private JTextField nomField, prixField, stockField;
    private JComboBox<Categorie> categorieBox;
    private CustomButton enregistrerBtn;

    public ProduitFormPanel(List<Categorie> categories) {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Nom :"));
        add(nomField = new JTextField());

        add(new JLabel("Prix unitaire :"));
        add(prixField = new JTextField());

        add(new JLabel("Quantité en stock :"));
        add(stockField = new JTextField());

        add(new JLabel("Catégorie :"));
        categorieBox = new JComboBox<>(categories.toArray(new Categorie[0]));
        add(categorieBox);

        enregistrerBtn = new CustomButton("✅ Enregistrer");
        add(enregistrerBtn);
        add(new JLabel()); // espace vide pour alignement
    }

    // Getters
    public JButton getEnregistrerBtn() { return enregistrerBtn; }
    public JTextField getNomField() { return nomField; }
    public JTextField getPrixField() { return prixField; }
    public JTextField getStockField() { return stockField; }
    public JComboBox<Categorie> getCategorieBox() { return categorieBox; }
}
