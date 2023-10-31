package View;

import javax.swing.*;
import java.awt.*;

public class FilterBar extends JPanel {

    public JTextField day, month, year, amount, category, payer, place, comment;

    public JButton filterButton;

    public FilterBar() {
        setLayout(new GridLayout(2,9));

        day = new JTextField();
        month = new JTextField();
        year = new JTextField();
        amount = new JTextField();
        category = new JTextField();
        payer = new JTextField();
        place = new JTextField();
        comment = new JTextField();

        filterButton = new JButton("filter");

        add(new JLabel("Day"));
        add(new JLabel("Month"));
        add(new JLabel("Year"));
        add(new JLabel("Amount"));
        add(new JLabel("Category"));
        add(new JLabel("Payer"));
        add(new JLabel("Place"));
        add(new JLabel("Comment"));

        add(new JLabel());

        add(day);
        add(month);
        add(year);
        add(amount);
        add(category);
        add(payer);
        add(place);
        add(comment);

        add(filterButton);
    }
}
