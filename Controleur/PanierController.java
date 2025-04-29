package Controleur;

import DAO.ProduitDAO;
import Modele.*;
import Vue.PanierView;
import DAO.CommandeDAO;
import DAO.CommandeDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;

public class PanierController {

    private Panier panier;
    private ProduitDAO produitDAO;
    private PanierView panierView;
    private Client client;

    public PanierController(Panier panier, ProduitDAO produitDAO, Client client) {
        this.panier = panier;
        this.produitDAO = produitDAO;
        this.client = client;
    }

    public void ajouterProduit(JTable table, DefaultTableModel tableModel, JFrame parentWindow) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentWindow, "❌ Sélectionnez un produit pour ajouter au panier !");
            return;
        }

        int idProduit = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        Produit produit = produitDAO.chercher(idProduit);

        if (produit == null) {
            JOptionPane.showMessageDialog(parentWindow, "❌ Produit introuvable !");
            return;
        }

        panier.ajouterProduit(produit, 1);

        // 🔥 Afficher ou actualiser la fenêtre PanierView
        if (panierView == null) {
            panierView = new PanierView(panier, client);
            panierView.setPanierController(this); // 🔥 Très important !
        }
        if (!panierView.isVisible()) {
            Point parentLocation = parentWindow.getLocation();
            Dimension parentSize = parentWindow.getSize();
            panierView.setLocation(parentLocation.x + parentSize.width + 10, parentLocation.y);
            panierView.setVisible(true);
        }
        panierView.updatePanierView();
    }

    public void retirerProduit(Produit produit) {
        if (produit != null) {
            panier.retirerProduit(produit);
            if (panierView != null) {
                panierView.updatePanierView();
            }
        }
    }

    public void viderPanier() {
        panier.viderPanier();
        if (panierView != null) {
            panierView.updatePanierView();
        }
    }

    public void commander(Client client) {
        try {
            double total = panier.calculerTotal(); // 🔥 Très important !

            if (total == 0) {
                JOptionPane.showMessageDialog(null, "❌ Ton panier est vide !");
                return;
            }

            CommandeDAO commandeDAO = new CommandeDAOImpl();
            Commande nouvelleCommande = new Commande(
                    0,
                    new Timestamp(System.currentTimeMillis()),
                    "payée",
                    total,
                    client.getIdClient()
            );

            commandeDAO.add(nouvelleCommande);

            JOptionPane.showMessageDialog(null, "✅ Merci pour ta commande ! Total : " + total + " €");

            panier.viderPanier();
            if (panierView != null) {
                panierView.updatePanierView();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 🔥
            JOptionPane.showMessageDialog(null, "❌ Erreur lors du traitement de la commande : " + e.getMessage());
        }
    }
}
