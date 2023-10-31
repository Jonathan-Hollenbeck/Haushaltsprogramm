package Control.ViewConroller;

import Control.EntryControl;
import View.MainFrame;

import java.awt.*;

public class MainFrameControl {

    private MainFrame mainFrame;

    private EntryControl entryControl;

    private EntryTabControl entryTabControl;
    private StatisticsTabControl statisticsTabControl;
    private FilterBarControl filterBarControl;

    private MenuBarControl menuBarControl;

    public MainFrameControl() {
        mainFrame = new MainFrame();

        entryControl = new EntryControl();

        entryTabControl = new EntryTabControl(entryControl);
        statisticsTabControl = new StatisticsTabControl(entryControl);

        filterBarControl = new FilterBarControl(entryControl, entryTabControl, statisticsTabControl);

        menuBarControl = new MenuBarControl(entryControl, entryTabControl, statisticsTabControl, this);

        mainFrame.add(filterBarControl.getFilterBar(), BorderLayout.NORTH);
        mainFrame.tabbedPane.add("Entries", entryTabControl.getEntryTab());
        mainFrame.tabbedPane.add("Statistics", statisticsTabControl.getStatisticsTab());
        mainFrame.setJMenuBar(menuBarControl.getMenuBar());

        /*mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });*/

        mainFrame.pack();
        mainFrame.setLocationByPlatform(true);
        mainFrame.setVisible(true);
    }

    public void updateTitle(String string){
        mainFrame.setTitle("Haushaltsprogramm - " + string);
    }

}
