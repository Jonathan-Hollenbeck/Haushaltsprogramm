package View;

import javax.swing.*;
import java.awt.*;

public class StatisticsTab extends JPanel {

    public JLabel sumValueLabel, dayAverageValueLabel, monthAverageValueLabel, yearAverageValueLabel;

    public JPanel informationPanel;

    public GraphGenerator graphGenerator;

    public JComboBox<String> xAxis, graphMode, datePackMode;

    public StatisticsTab() {
        setLayout(new BorderLayout());

        //Information
        informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(4, 2));

        Font labelFont = new Font("Arial", Font.BOLD, 20);

        sumValueLabel = new JLabel();
        sumValueLabel.setFont(labelFont);
        dayAverageValueLabel = new JLabel();
        dayAverageValueLabel.setFont(labelFont);
        monthAverageValueLabel = new JLabel();
        monthAverageValueLabel.setFont(labelFont);
        yearAverageValueLabel = new JLabel();
        yearAverageValueLabel.setFont(labelFont);

        JLabel sumLabel = new JLabel("Sum: ", SwingConstants.RIGHT);
        sumLabel.setFont(labelFont);
        JLabel dayLabel = new JLabel("Day Average: ", SwingConstants.RIGHT);
        dayLabel.setFont(labelFont);
        JLabel monthLabel = new JLabel("Month Average: ", SwingConstants.RIGHT);
        monthLabel.setFont(labelFont);
        JLabel yearLabel = new JLabel("Year Average: ", SwingConstants.RIGHT);
        yearLabel.setFont(labelFont);

        informationPanel.add(sumLabel);
        informationPanel.add(sumValueLabel);
        informationPanel.add(dayLabel);
        informationPanel.add(dayAverageValueLabel);
        informationPanel.add(monthLabel);
        informationPanel.add(monthAverageValueLabel);
        informationPanel.add(yearLabel);
        informationPanel.add(yearAverageValueLabel);

        //Graph
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout());

        //Graph Settings
        JPanel graphSettingsPanel = new JPanel();
        graphSettingsPanel.setLayout(new GridLayout(1, 6));

        xAxis = new JComboBox<>(new String[]{"Date", "Category", "Payer", "Place"});
        graphMode = new JComboBox<>(new String[]{"Line", "Bar"});
        datePackMode = new JComboBox<>(new String[]{"Day", "Month", "Year"});

        graphSettingsPanel.add(new JLabel("x-axis: ", SwingConstants.RIGHT));
        graphSettingsPanel.add(xAxis);
        graphSettingsPanel.add(new JLabel("mode: ", SwingConstants.RIGHT));
        graphSettingsPanel.add(graphMode);
        graphSettingsPanel.add(new JLabel("date Packmode: ", SwingConstants.RIGHT));
        graphSettingsPanel.add(datePackMode);

        //Actual Graph
        graphGenerator = new GraphGenerator();

        //adding Settings and Graph
        graphPanel.add(graphSettingsPanel, BorderLayout.NORTH);
        graphPanel.add(graphGenerator, BorderLayout.CENTER);

        //adding everything to the StatisticsTab
        add(informationPanel, BorderLayout.WEST);
        add(graphPanel, BorderLayout.CENTER);
    }

}
