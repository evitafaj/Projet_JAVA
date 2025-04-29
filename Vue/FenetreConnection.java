package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;

public class FenetreConnection extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private CustomButton loginButton;
    private JLabel resultLabel;

    public FenetreConnection(DaoFactory daoFactory) {
        setTitle("Connexion Utilisateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Titre en haut
        JLabel titre = new JLabel("🔐 Connexion", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setForeground(new Color(70, 70, 70));
        add(titre, BorderLayout.NORTH);

        // 🔥 Centre : Formulaire
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(new Color(255, 228, 225)); // 🌸 Fond pastel rose
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new CustomButton("🔓 Se connecter");
        resultLabel = new JLabel("", SwingConstants.CENTER);

        formPanel.add(new JLabel("Email :", SwingConstants.CENTER));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Mot de passe :", SwingConstants.CENTER));
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // 🔥 Bas : bouton + message
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomPanel.setBackground(new Color(255, 228, 225));

        bottomPanel.add(loginButton);
        bottomPanel.add(resultLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // 🎯 Action bouton connexion
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());

            ClientDAO clientDAO = daoFactory.getClientDAO();
            AdminDAO adminDAO = new AdminDAOImpl(daoFactory);

            Client client = clientDAO.seConnecter(email, mdp);
            if (client != null) {
                dispose();
                new FenetreClient(client, daoFactory);
                return;
            }

            Admin admin = adminDAO.seConnecter(email, mdp);
            if (admin != null) {
                dispose();
                new FenetreAdmin(admin, daoFactory);
                return;
            }

            resultLabel.setForeground(Color.RED);
            resultLabel.setText("❌ Identifiants incorrects.");
        });

        setVisible(true);
    }
}
