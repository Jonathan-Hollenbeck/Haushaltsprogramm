package Control.ViewConroller;

import Control.EntryControl;
import Util.EntryAttribute;
import View.MenuBar;

import javax.swing.*;

public class MenuBarControl {

    private MenuBar menuBar;

    private EntryControl entryControl;

    private EntryTabControl entryTabControl;

    private StatisticsTabControl statisticsTabControl;

    private MainFrameControl mainFrameControl;

    private String currentSaveName = "";

    public MenuBarControl(EntryControl entryControl, EntryTabControl entryTabControl, StatisticsTabControl statisticsTabControl, MainFrameControl mainFrameControl) {
        this.entryControl = entryControl;
        this.entryTabControl = entryTabControl;
        this.mainFrameControl = mainFrameControl;
        this.statisticsTabControl = statisticsTabControl;

        menuBar = new MenuBar();

        setSaveAction();
        setSaveAsAction();
        setOpenAction();
        setSaveAsCSVAction();
        setOpenCSVAction();

        setSortingModeAction();
    }

    //File
    private void setSaveAction() {
        menuBar.save.addActionListener(e -> {
            if (currentSaveName.equals("")) {
                JFileChooser chooser = new JFileChooser("./saves/");
                chooser.setApproveButtonText("Save");
                chooser.setDialogTitle("Save");
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    currentSaveName = chooser.getSelectedFile().getName();
                }
                if (!currentSaveName.equals("")) {
                    mainFrameControl.updateTitle(currentSaveName);
                }
            }
            if (!currentSaveName.equals("")) {
                entryControl.saveEntries(cutStringUntil('.', currentSaveName));
            }
        });
    }

    private void setSaveAsAction() {
        menuBar.saveAs.addActionListener(e -> {
            String fileName = "";

            JFileChooser chooser = new JFileChooser("./saves/");
            chooser.setApproveButtonText("Save");
            chooser.setDialogTitle("Save");
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileName = chooser.getSelectedFile().getName();
            }
            if (fileName != "") {
                currentSaveName = cutStringUntil('.', fileName);
                entryControl.saveEntries(currentSaveName);

                mainFrameControl.updateTitle(currentSaveName);
            }
        });
    }

    private void setOpenAction() {
        menuBar.open.addActionListener(e -> {
            String fileName = "";

            JFileChooser chooser = new JFileChooser("./saves/");
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileName = chooser.getSelectedFile().getName();
            }
            if (fileName != "") {
                currentSaveName = cutStringUntil('.', fileName);
                entryControl.openEntries(currentSaveName);
                entryTabControl.updateDisplayTable();

                statisticsTabControl.update();

                mainFrameControl.updateTitle(currentSaveName);
            }
        });
    }

    private void setSaveAsCSVAction(){
        menuBar.saveAsCSV.addActionListener(e -> {
            String fileName = "";

            JFileChooser chooser = new JFileChooser("./saves/");
            chooser.setApproveButtonText("Save");
            chooser.setDialogTitle("Save");
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileName = chooser.getSelectedFile().getName();
            }
            if (fileName != "") {
                entryControl.saveEntriesAsCSV(cutStringUntil('.', fileName));
            }
        });
    }

    private void setOpenCSVAction(){
        menuBar.openCSV.addActionListener(e -> {
            String fileName = "";

            JFileChooser chooser = new JFileChooser("./saves/");
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                fileName = chooser.getSelectedFile().getName();
            }
            if (fileName != "") {
                currentSaveName = cutStringUntil('.', fileName);
                entryControl.openEntriesCSV(cutStringUntil('.', fileName));
                entryTabControl.updateDisplayTable();

                statisticsTabControl.update();

                mainFrameControl.updateTitle(currentSaveName);
            }
        });
    }

    private String cutStringUntil(char cut, String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == cut) {
                return s.substring(0, i);
            }
        }
        return s;
    }

    //SortingMode
    private void setSortingModeAction() {
        menuBar.date.addActionListener(e -> {
            entryTabControl.setSortingAttribute(EntryAttribute.date);
        });
        menuBar.amount.addActionListener(e -> {
            entryTabControl.setSortingAttribute(EntryAttribute.amount);
        });
        menuBar.category.addActionListener(e -> {
            entryTabControl.setSortingAttribute(EntryAttribute.category);
        });
        menuBar.payer.addActionListener(e -> {
            entryTabControl.setSortingAttribute(EntryAttribute.payer);
        });
        menuBar.place.addActionListener(e -> {
            entryTabControl.setSortingAttribute(EntryAttribute.place);
        });
        menuBar.comment.addActionListener(e -> {
            entryTabControl.setSortingAttribute(EntryAttribute.comment);
        });
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
