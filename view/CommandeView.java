package view;

import controller.CommandeController;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CommandeView extends JFrame {

    private final CommandeController controller;

    public CommandeView() {
        controller = new CommandeController();

        setTitle("Ajouter une commande");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Champs de formulaire
        JTextField statutField = new JTextField();
        JTextField totalField = new JTextField();
        JTextField idClientField = new JTextField();

        JButton btnAjouter = new JButton("Ajouter commande");

        // Ajout des composants
        add(new JLabel("Statut :"));
        add(statutField);

        add(new JLabel("Total (€) :"));
        add(totalField);

        add(new JLabel("ID Client :"));
        add(idClientField);

        add(new JLabel("")); // espacement
        add(btnAjouter);

        // Action bouton
        btnAjouter.addActionListener(e -> {
            try {
                String statut = statutField.getText().trim();
                double total = Double.parseDouble(totalField.getText().trim());
                int idClient = Integer.parseInt(idClientField.getText().trim());

                boolean success = controller.ajouterCommande(new Date(), statut, total, idClient);
                JOptionPane.showMessageDialog(this,
                        success ? " Commande ajoutée avec succès !" : " Échec de l'ajout.",
                        "Résultat", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer des valeurs valides.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CommandeView::new);
    }
}

