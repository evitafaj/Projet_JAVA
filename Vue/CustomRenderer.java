package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Par défaut couleur normale
        c.setForeground(Color.BLACK);

        // Vérifier si on est sur la colonne du prix (colonne 3 = Prix Unitaire)
        if (column == 3) {
            String nomProduit = (String) table.getValueAt(row, 1); // colonne 1 = Nom du produit
            if (nomProduit.contains("⭐PROMO")) {
                c.setForeground(Color.RED); // 🔥 Rouge si produit en promo
            }
        }

        return c;
    }
}
