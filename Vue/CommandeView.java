/**Autor Nina**/
package Vue;

import Controleur.CommandeController;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CommandeView extends JFrame {

    private CommandeController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton boutonVoirDetails;

    public CommandeView(CommandeController controller) {
        this.controller = controller;

        setTitle("Liste des Commandes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Client", "Date", "Total (€)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        boutonVoirDetails = new JButton("Voir les détails");
        panelBoutons.add(boutonVoirDetails);
        add(panelBoutons, BorderLayout.SOUTH);

        controller.afficherCommandes(this);

        boutonVoirDetails.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int commandeId = (int) table.getModel().getValueAt(selectedRow, 0);
            controller.voirDetailsCommande(commandeId, null);
        });

        setVisible(true);
    }

    public void afficherCommandes(List<Commande> commandes) {
        tableModel.setRowCount(0);

        for (Commande commande : commandes) {
            Object[] rowData = {
                    commande.getId(),
                    commande.getIdClient(),
                    commande.getDate(),
                    commande.getTotal()
            };
            tableModel.addRow(rowData);
        }
    }
}