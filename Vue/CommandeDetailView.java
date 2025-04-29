/**Autor Nina**/
package Vue;

import Controleur.CommandeController;
import Controleur.PaymentController;
import Controleur.TransportController;
import Modele.Client;
import Modele.Commande;
import Modele.Payment;
import Modele.Transport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CommandeDetailView extends JFrame {
    private JLabel labelId;
    private JLabel labelClientId;
    private JLabel labelClientNom;
    private JLabel labelClientMail;
    private JLabel labelDate;
    private JLabel labelTotal;
    private JLabel transportLabel;
    private JLabel transportDate;
    private JLabel paymentLabel;
    private JLabel paymentMode;
    private Commande commande;
    private Client client;
    private Transport transport;
    private Payment payment;
    private List<Map<String, Object>> commandeLine;
    private CommandeController commandeController;

    private JPanel buttonPanel;
    private JButton paymentButton;
    private JButton livraisonButton;
    private JButton refreshButton;


    public CommandeDetailView(Commande commande, Client client, Transport transport, Payment payment, List<Map<String, Object>> commandeLine, CommandeController commandeController) {
        this.commande = commande;
        this.client = client;
        this.transport = transport;
        this.payment = payment;
        this.commandeLine = commandeLine;
        this.commandeController = commandeController;

        setTitle("Détails de la Commande");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(6, 2, 10, 10));
        labelId = new JLabel("ID de commande : " + commande.getId());
        labelClientId = new JLabel("ID du client : " + commande.getIdClient());
        labelClientNom = new JLabel("Nom du client : " + client.getNom() + ' ' + client.getPrenom());
        labelClientMail = new JLabel("Mail du client : " + client.getEmail());
        labelDate = new JLabel("Date : " + commande.getDate().toString());
        labelTotal = new JLabel("Total (€) : " + commande.getTotal());

        infoPanel.add(labelId);
        infoPanel.add(labelClientId);
        infoPanel.add(labelClientNom);
        infoPanel.add(labelClientMail);
        infoPanel.add(labelDate);
        infoPanel.add(labelTotal);

        transportLabel = new JLabel((transport != null) ? "Transport Adresse : " + transport.getAdresse() : "Transport information not available");
        transportDate = new JLabel((transport != null) ? "Transport Date : " + transport.getDateLivraison() : "");

        paymentLabel = new JLabel((payment != null) ? "Montant payé : " + payment.getAmount() + " €" : "Payment information not available");
        paymentMode = new JLabel((payment != null) ? "Payé par : " + payment.getPaymentMethod() : "");

        infoPanel.add(transportLabel);
        infoPanel.add(transportDate);
        infoPanel.add(paymentLabel);
        infoPanel.add(paymentMode);

        String[] columnNames = {"Produit", "Quantité", "Prix Unitaire", "Prix Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        for (Map<String, Object> line : commandeLine) {
            String produitNom = (String) line.get("produitNom");
            int quantite = (Integer) line.get("quantite");
            double produitPrix = (Double) line.get("produitPrix");
            double prixTotalLigne = (Double) line.get("prixTotalLigne");

            Object[] row = {produitNom, quantite, produitPrix, prixTotalLigne};
            tableModel.addRow(row);
        }

        JScrollPane tableScrollPane = new JScrollPane(table);
        add(infoPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        createButtonPanel();

        setVisible(true);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        paymentButton = new JButton("Voir Paiement");
        livraisonButton = new JButton("Voir Livraison");
        refreshButton = new JButton("refresh");

        paymentButton.addActionListener(e -> {
            PaymentController paymentController = new PaymentController(commande.getTotal(), commande.getId());
            paymentController.startPaymentProcess();
        });

        livraisonButton.addActionListener(e -> {
            TransportController transportController = new TransportController();
            transportController.ouvrirTransportView(commande.getId());
        });

        refreshButton.addActionListener(e -> {
            if (commandeController != null) {
                commandeController.refreshCommandeDetails(commande.getId(), this);
            }
        });

        buttonPanel.add(paymentButton);
        buttonPanel.add(livraisonButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateCommandeDetails(Commande commande, Client client, Transport transport, Payment payment, List<Map<String, Object>> commandeLine) {
        this.commande = commande;
        this.client = client;
        this.transport = transport;
        this.payment = payment;
        this.commandeLine = commandeLine;

        labelId.setText("ID de commande : " + commande.getId());
        labelClientId.setText("ID du client : " + commande.getIdClient());
        labelClientNom.setText("Nom du client : " + client.getNom() + ' ' + client.getPrenom());
        labelClientMail.setText("Mail du client : " + client.getEmail());
        labelDate.setText("Date : " + commande.getDate().toString());
        labelTotal.setText("Total (€) : " + commande.getTotal());

        transportLabel.setText((transport != null) ? "Transport Adresse : " + transport.getAdresse() : "Transport information not available");
        transportDate.setText((transport != null) ? "Transport Date : " + transport.getDateLivraison() : "");

        paymentLabel.setText((payment != null) ? "Montant payé : " + payment.getAmount() + " €" : "Payment information not available");

        paymentMode.setText((payment != null) ? "Payé par : " + payment.getPaymentMethod() : "Payment method not available");

        DefaultTableModel tableModel = (DefaultTableModel) ((JTable) ((JScrollPane) getContentPane().getComponent(1)).getViewport().getView()).getModel();
        tableModel.setRowCount(0);
        for (Map<String, Object> line : commandeLine) {
            String produitNom = (String) line.get("produitNom");
            int quantite = (Integer) line.get("quantite");
            double produitPrix = (Double) line.get("produitPrix");
            double prixTotalLigne = (Double) line.get("prixTotalLigne");

            Object[] row = {produitNom, quantite, produitPrix, prixTotalLigne};
            tableModel.addRow(row);
        }
    }
}