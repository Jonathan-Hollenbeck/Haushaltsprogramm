package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public JTabbedPane tabbedPane;

    public MainFrame() {
        createMainFrame();

        tabbedPane = new JTabbedPane();

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createMainFrame() {
        setTitle("Haushaltsprogramm");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

}
