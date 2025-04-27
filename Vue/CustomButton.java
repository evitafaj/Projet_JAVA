package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private Color normalColor = new Color(255, 255, 204); // 🌼 Jaune pastel
    private Color hoverColor = new Color(255, 255, 153);  // 🌟 Jaune plus clair au survol

    public CustomButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(normalColor);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true)); // Bords arrondis

        // 🎯 Ajout de l'effet hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }
}
