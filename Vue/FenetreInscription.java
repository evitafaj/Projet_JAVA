package Vue;

import DAO.ClientDAO;
import DAO.ClientDAOImpl;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class FenetreInscription extends JFrame {
    private JTextField nomField, prenomField, adresseField, emailField;
    private JPasswordField mdpField;
    private CustomButton inscrireBtn, retourBtn;

    public FenetreInscription(DaoFactory daoFactory) {
        setTitle("Créer un compte client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("📝 Inscription Client", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Formulaire centre
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        nomField = new JTextField();
        prenomField = new JTextField();
        adresseField = new JTextField();
        emailField = new JTextField();
        mdpField = new JPasswordField();

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom :"));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Adresse :"));
        formPanel.add(adresseField);
        formPanel.add(new JLabel("Email :"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(mdpField);

        add(formPanel, BorderLayout.CENTER);

        // 🔥 Bas : boutons inscription et retour
        JPanel boutonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        boutonPanel.setBackground(new Color(255, 228, 225));

        retourBtn = new CustomButton("⬅️ Retour");
        inscrireBtn = new CustomButton("✅ Créer mon compte");

        boutonPanel.add(retourBtn);
        boutonPanel.add(inscrireBtn);

        add(boutonPanel, BorderLayout.SOUTH);

        ClientDAO clientDAO = new ClientDAOImpl(daoFactory);

        // 🎯 Action bouton inscription
        inscrireBtn.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String adresse = adresseField.getText();
            String email = emailField.getText();
            String mdp = new String(mdpField.getPassword());

            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Merci de remplir tous les champs !");
                return;
            }

            // Vérification email déjà utilisé
            for (Client c : clientDAO.getAll()) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(this, "❌ Cet email est déjà utilisé.");
                    return;
                }
            }

            Client nouveauClient = new Client(0, nom, prenom, adresse, email, mdp);
            clientDAO.ajouter(nouveauClient);

            JOptionPane.showMessageDialog(this, "✅ Compte créé avec succès !");
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
