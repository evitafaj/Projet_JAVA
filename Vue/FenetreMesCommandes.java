package Vue;

import DAO.CommandeDAO;
import DAO.CommandeDAOImpl;
import DAO.DaoFactory;
import Modele.Client;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FenetreMesCommandes extends JFrame {
    private Client client;
    private DaoFactory daoFactory;
    private JTable tableCommandes;
    private DefaultTableModel tableModel;

    public FenetreMesCommandes(Client client, DaoFactory daoFactory) {
        this.client = client;
        this.daoFactory = daoFactory;

        setTitle("📦 Mes Commandes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre
        JLabel titre = new JLabel("Mes Commandes", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titre, BorderLayout.NORTH);

        // 🔥 Table
        String[] colonnes = {"ID Commande", "Date", "Statut", "Total (€)"};
        tableModel = new DefaultTableModel(colonnes, 0);
        tableCommandes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCommandes);
        add(scrollPane, BorderLayout.CENTER);

        // 🔥 Charger les commandes
        chargerCommandes();

        setVisible(true);
    }

    private void chargerCommandes() {
        tableModel.setRowCount(0); // Vide le tableau avant

        try {
            CommandeDAO commandeDAO = new CommandeDAOImpl();
            List<Commande> commandes = commandeDAO.getCommandesByClientId(client.getIdClient());

            System.out.println("Commandes récupérées : " + commandes.size());

            if (commandes != null && !commandes.isEmpty()) {
                for (Commande commande : commandes) {
                    System.out.println("Ajout commande " + commande.getId());
                    tableModel.addRow(new Object[]{
                            commande.getId(),
                            commande.getDate(),
                            commande.getStatut(),
                            String.format("%.2f", commande.getTotal())
                    });
                }
            } else {
                System.out.println("Aucune commande trouvée.");
            }

            // 🔥 CECI À LA FIN pour forcer l'affichage :
            tableModel.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de chargement des commandes : " + e.getMessage());
        }
    }

}
