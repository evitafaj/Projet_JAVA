/**Author nina**/

package Vue;

import Controleur.StatistiquesController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Map;

public class StatistiquesView extends JFrame {

    private JTextArea textArea;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private StatistiquesController controller;
    private ChartPanel chartPanel;

    public StatistiquesView(StatistiquesController controller) {
        this.controller = controller;

        setTitle("Statistiques des Commandes");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        startDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startEditor);
        endDateSpinner.setEditor(endEditor);

        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> updateStatisticsForSelectedDates());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Date Début:"));
        topPanel.add(startDateSpinner);
        topPanel.add(new JLabel("Date Fin:"));
        topPanel.add(endDateSpinner);
        topPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.WEST);

        chartPanel = new ChartPanel(null);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        add(chartPanel, BorderLayout.CENTER);

        setVisible(true);

        updateStatisticsForSelectedDates();
    }

    public void afficherStatistiques(Map<String, Map<String, Object>> stats) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Object>> entry : stats.entrySet()) {
            String date = entry.getKey();
            double totalAmount = (Double) entry.getValue().get("totalAmount");
            int orderCount = (Integer) entry.getValue().get("orderCount");

            sb.append("Date: ").append(date)
                    .append(" - Total: ").append(totalAmount)
                    .append(" € - Commandes: ").append(orderCount)
                    .append("\n");
        }
        textArea.setText(sb.toString());

        createChart(stats);
    }

    private void createChart(Map<String, Map<String, Object>> stats) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Map<String, Object>> entry : stats.entrySet()) {
            String date = entry.getKey();
            double totalAmount = (Double) entry.getValue().get("totalAmount");
            int orderCount = (Integer) entry.getValue().get("orderCount");

            dataset.addValue(totalAmount, "Chiffre d'Affaires (€)", date);
            dataset.addValue(orderCount, "Nombre de Commandes", date);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "📈 Statistiques des Commandes",
                "Date",
                "Valeur",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // 🌸 Personnaliser le graphique
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 22));

        var plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE); // Fond du plot
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY); // Lignes de grille verticales
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);  // Lignes de grille horizontales

        var renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.PINK); // Série 0 : chiffre d'affaires en rose
        renderer.setSeriesPaint(1, Color.BLUE); // Série 1 : nombre de commandes en bleu
        renderer.setSeriesStroke(0, new BasicStroke(2.0f)); // Traits plus épais
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        chartPanel.setChart(chart);
    }

    private void updateStatisticsForSelectedDates() {
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();
        controller.afficherStatistiques(this, startDate, endDate);
    }
}