package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {

    public JMenu file, sortingMode;

    public JMenuItem save, saveAs, saveAsCSV, open, openCSV, date, amount, category, payer, place, comment;


    public MenuBar() {
        //File
        file = new JMenu("File");

        save = new JMenuItem("save");
        save.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAs = new JMenuItem("save as");
        open = new JMenuItem("open");

        saveAsCSV = new JMenuItem("save as CSV");
        openCSV = new JMenuItem("open CSV");

        file.add(save);
        file.add(saveAs);
        file.add(open);
        file.addSeparator();
        file.add(saveAsCSV);
        file.add(openCSV);

        //SortingMode
        sortingMode = new JMenu("Sorting Mode");

        date = new JMenuItem("Date");
        amount = new JMenuItem("Amount");
        category = new JMenuItem("Category");
        payer = new JMenuItem("Payer");
        place = new JMenuItem("Place");
        comment = new JMenuItem("Comment");

        sortingMode.add(date);
        sortingMode.add(amount);
        sortingMode.add(category);
        sortingMode.add(payer);
        sortingMode.add(place);
        sortingMode.add(comment);

        //add to menubar
        add(file);
        add(sortingMode);
    }
}
