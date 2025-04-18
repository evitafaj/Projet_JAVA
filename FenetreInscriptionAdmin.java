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
    private JButton inscrireBtn, retourBtn;

    public FenetreInscriptionAdmin(DaoFactory daoFactory) {
        setTitle("Créer un compte administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        mdpField = new JPasswordField();
        inscrireBtn = new JButton("Créer mon compte admin");
        retourBtn = new JButton("Retour");

        add(new JLabel("Nom :"));
        add(nomField);
        add(new JLabel("Prénom :"));
        add(prenomField);
        add(new JLabel("Email :"));
        add(emailField);
        add(new JLabel("Mot de passe :"));
        add(mdpField);
        add(retourBtn);
        add(inscrireBtn);

        AdminDAO adminDAO = new AdminDAOImpl(daoFactory);

        // Bouton inscription
        inscrireBtn.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = new String(mdpField.getPassword());

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Merci de remplir tous les champs !");
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

        retourBtn.addActionListener(e -> {
            dispose();
            new FenetreAccueil(daoFactory);
        });

        setVisible(true);
    }
}
