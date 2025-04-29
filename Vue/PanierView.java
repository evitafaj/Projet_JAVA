/*Nina */

package Vue;

import Modele.Panier;
import Modele.Produit;
import Modele.Client;
import Controleur.PanierController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.util.Map;

public class PanierView extends JFrame {
    private JTable tablePanier;
    private JButton btnCommander;
    private JButton btnDelete;
    private Panier panier;
    private PanierController panierController;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private Client client;

    public PanierView(Panier panier, Client client) {
        this.panier = panier;
        this.client = client;

        setTitle("🛒 Votre Panier");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"ID", "Nom", "Prix Unitaire", "Quantité", "Prix Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);

                if (column == 3) {
                    try {
                        int newQuantity = Integer.parseInt(aValue.toString());
                        Produit produit = panier.getProduits().keySet().toArray(new Produit[0])[row];
                        panier.changerQuantite(produit, newQuantity);

                        double prixTotal = produit.getPrixUnitaire() * newQuantity;
                        tableModel.setValueAt(prixTotal + " €", row, 4);
                        updatePanierView();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Quantité invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };

        tablePanier = new JTable(tableModel);
        tablePanier.setFillsViewportHeight(true);
        tablePanier.setRowHeight(24);
        tablePanier.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tablePanier.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tablePanier);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnCommander = new JButton("🛍 Commander");
        btnDelete = new JButton("Retirer");

        btnCommander.setBackground(new Color(33, 150, 243));
        btnCommander.setForeground(Color.WHITE);
        btnCommander.setFocusPainted(false);
        btnCommander.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCommander.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCommander.setUI(new MetalButtonUI());

        btnDelete.setBackground(new Color(239, 4, 135));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDelete.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnDelete.setUI(new MetalButtonUI());

        lblTotal = new JLabel("Total: 0.00 €");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(btnDelete);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btnCommander);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(lblTotal, BorderLayout.NORTH);

        updatePanierView();

        btnCommander.addActionListener(e -> panierController.commander(client));

        btnDelete.addActionListener(e -> {
            int selectedRow = tablePanier.getSelectedRow();
            if (selectedRow >= 0) {
                int idProduit = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                Produit produitARetirer = null;

                for (Produit p : panier.getProduits().keySet()) {
                    if (p.getIdProduit() == idProduit) {
                        produitARetirer = p;
                        break;
                    }
                }

                if (produitARetirer != null && panierController != null) {
                    panierController.retirerProduit(produitARetirer);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Produit non trouvé dans le panier !");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Sélectionne un produit à retirer !");
            }
        });
    }

    public void updatePanierView() {
        tableModel.setRowCount(0);
        double total = 0.0;

        for (Map.Entry<Produit, Integer> entry : panier.getProduits().entrySet()) {
            Produit produit = entry.getKey();
            int quantite = entry.getValue();
            double prixTotal = produit.getPrixUnitaire() * quantite;

            tableModel.addRow(new Object[]{
                    produit.getIdProduit(),
                    produit.getNom(),
                    produit.getPrixUnitaire() + " €",
                    quantite,
                    prixTotal + " €"
            });

            total += prixTotal;
        }

        lblTotal.setText("Total: " + total + " €");
    }

    public void setPanierController(PanierController panierController) {
        this.panierController = panierController;
    }
}
