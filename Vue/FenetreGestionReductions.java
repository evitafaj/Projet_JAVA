package Vue;

import DAO.DaoFactory;
import DAO.ReductionDAO;
import DAO.ReductionDAOImpl;
import Modele.Reduction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FenetreGestionReductions extends JFrame {

    public FenetreGestionReductions(DaoFactory daoFactory) {
        setTitle("Gestion des Produits");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("🏷️ Gestion des Réductions", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Menu central avec fond pastel
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔥 Boutons modernes
        CustomButton afficherBtn = new CustomButton("📋 Afficher toutes les réductions");
        CustomButton ajouterBtn = new CustomButton("➕ Ajouter une réduction");
        CustomButton modifierBtn = new CustomButton("✏️ Modifier une réduction");
        CustomButton supprimerBtn = new CustomButton("🗑️ Supprimer une réduction");
        CustomButton retourBtn = new CustomButton("⬅️ Retour");

        menuPanel.add(afficherBtn);
        menuPanel.add(ajouterBtn);
        menuPanel.add(modifierBtn);
        menuPanel.add(supprimerBtn);
        menuPanel.add(retourBtn);

        add(menuPanel, BorderLayout.CENTER);


        ReductionDAO reductionDAO = new ReductionDAOImpl(daoFactory);

        // 1. Afficher toutes les réductions
        afficherBtn.addActionListener(e -> {
            List<Reduction> reductions = reductionDAO.getAllReductions();
            StringBuilder sb = new StringBuilder("📋 Liste des réductions :\n\n");
            for (Reduction r : reductions) {
                sb.append("• ID : ").append(r.getIdReduction())
                        .append(" | ").append(r.getPourcentage()).append("%")
                        .append(" | Du ").append(r.getDateDebut())
                        .append(" au ").append(r.getDateFin())
                        .append(" | Produit ID : ").append(r.getIdProduit())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Réductions existantes", JOptionPane.INFORMATION_MESSAGE);
        });

        // 2. Ajouter une réduction
        ajouterBtn.addActionListener(e -> {
            JTextField pourcentageField = new JTextField();
            JTextField dateDebutField = new JTextField("2025-05-01");
            JTextField dateFinField = new JTextField("2025-05-31");
            JTextField produitIdField = new JTextField();

            Object[] fields = {
                    "Pourcentage de réduction (%):", pourcentageField,
                    "Date de début (AAAA-MM-JJ):", dateDebutField,
                    "Date de fin (AAAA-MM-JJ):", dateFinField,
                    "ID du produit concerné :", produitIdField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Ajouter une réduction", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Reduction nouvelle = new Reduction(
                            0,
                            Double.parseDouble(pourcentageField.getText()),
                            LocalDate.parse(dateDebutField.getText()),
                            LocalDate.parse(dateFinField.getText()),
                            Integer.parseInt(produitIdField.getText())
                    );
                    reductionDAO.ajouterReduction(nouvelle);
                    JOptionPane.showMessageDialog(this, "✅ Réduction ajoutée !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Erreur lors de l'ajout.");
                    ex.printStackTrace();
                }
            }
        });

        // 3. Modifier une réduction
        modifierBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID de la réduction à modifier :");
            if (idStr != null) {
                Reduction r = reductionDAO.getReductionById(Integer.parseInt(idStr));
                if (r != null) {
                    JTextField pourcentageField = new JTextField(String.valueOf(r.getPourcentage()));
                    JTextField dateDebutField = new JTextField(r.getDateDebut().toString());
                    JTextField dateFinField = new JTextField(r.getDateFin().toString());
                    JTextField produitIdField = new JTextField(String.valueOf(r.getIdProduit()));

                    Object[] fields = {
                            "Pourcentage de réduction (%):", pourcentageField,
                            "Date de début (AAAA-MM-JJ):", dateDebutField,
                            "Date de fin (AAAA-MM-JJ):", dateFinField,
                            "ID du produit concerné :", produitIdField
                    };

                    int option = JOptionPane.showConfirmDialog(this, fields, "Modifier réduction", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            r.setPourcentage(Double.parseDouble(pourcentageField.getText()));
                            r.setDateDebut(LocalDate.parse(dateDebutField.getText()));
                            r.setDateFin(LocalDate.parse(dateFinField.getText()));
                            r.setIdProduit(Integer.parseInt(produitIdField.getText()));

                            reductionDAO.modifierReduction(r);
                            JOptionPane.showMessageDialog(this, "✅ Réduction modifiée !");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "❌ Erreur lors de la modification.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Réduction introuvable.");
                }
            }
        });

        // 4. Supprimer une réduction
        supprimerBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID de la réduction à supprimer :");
            if (idStr != null) {
                Reduction r = reductionDAO.getReductionById(Integer.parseInt(idStr));
                if (r != null) {
                    reductionDAO.supprimerReduction(r.getIdReduction());
                    JOptionPane.showMessageDialog(this, "🗑️ Réduction supprimée.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Réduction introuvable.");
                }
            }
        });

        // 5. Retour
        retourBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
