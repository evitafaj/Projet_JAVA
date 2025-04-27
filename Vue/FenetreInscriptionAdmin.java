package Vue;

import DAO.AdminDAO;
import DAO.AdminDAOImpl;
import DAO.DaoFactory;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;

public class FenetreInscriptionAdmin extends JFrame {
    private JTextField nomField, prenomField, emailField;
    private JPasswordField mdpField;
    private CustomButton inscrireBtn, retourBtn;

    public FenetreInscriptionAdmin(DaoFactory daoFactory) {
        setTitle("Créer un compte administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("🛠️ Inscription Administrateur", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Formulaire au centre
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(255, 228, 225)); // 🌸 Rose pastel
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        mdpField = new JPasswordField();

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom :"));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Email :"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(mdpField);

        add(formPanel, BorderLayout.CENTER);

        // 🔥 Bas : boutons
        JPanel boutonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        boutonPanel.setBackground(new Color(255, 228, 225));

        retourBtn = new CustomButton("⬅️ Retour");
        inscrireBtn = new CustomButton("✅ Créer mon compte");

        boutonPanel.add(retourBtn);
        boutonPanel.add(inscrireBtn);

        add(boutonPanel, BorderLayout.SOUTH);

        AdminDAO adminDAO = new AdminDAOImpl(daoFactory);

        // 🎯 Action bouton inscription
        inscrireBtn.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = new String(mdpField.getPassword());

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Merci de remplir tous les champs !");
                return;
            }

            // Vérification email déjà utilisé
            for (Admin a : adminDAO.getAll()) {
                if (a.getEmail().equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(this, "❌ Cet email est déjà utilisé.");
                    return;
                }
            }

            Admin nouvelAdmin = new Admin(0, nom, prenom, email, mdp);
            adminDAO.ajouter(nouvelAdmin);

            JOptionPane.showMessageDialog(this, "✅ Compte administrateur créé !");
            dispose();
            new FenetreConnection(daoFactory);
        });

        // 🎯 Action bouton retour
        retourBtn.addActionListener(e -> {
            dispose();
            new FenetreAccueil(daoFactory);
        });

        setVisible(true);
    }
}
