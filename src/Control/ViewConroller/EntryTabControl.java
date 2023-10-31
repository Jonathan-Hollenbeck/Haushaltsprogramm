package Control.ViewConroller;

import Control.EntryControl;
import Model.Entry;
import Util.EntryAttribute;
import View.EntryTab;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EntryTabControl {

    private EntryTab entryTab;

    private EntryControl entryControl;

    private EntryAttribute sortingAttribute = EntryAttribute.date;

    private int selectedEntry = -1;

    public EntryTabControl(EntryControl entryControl) {
        this.entryControl = entryControl;

        entryTab = new EntryTab();

        setAddButtonAction();
        setEditButtonAction();
        setDeleteButtonAction();
        addMouseListenerToTable();
    }

    private void setAddButtonAction() {
        entryTab.addEntryButton.addActionListener(
                e -> {
                    if (!entryTab.dateTextField.getText().matches(
                            "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$"
                    )) {
                        JOptionPane.showMessageDialog(null, "wrong date format!\nuse dd.mm.yyyy");
                    } else if (!entryTab.amountTextField.getText().matches(
                            "[+-]?[0-9]*([.][0-9])?([.][0-9][0-9])?"
                    )) {
                        JOptionPane.showMessageDialog(null, "wrong amount format!\nuse x*.xx");
                    } else {
                        entryControl.addEntry(entryTab.dateTextField.getText(), entryTab.amountTextField.getText(), entryTab.categoryTextField.getText(), entryTab.payerTextField.getText(), entryTab.placeTextField.getText(), entryTab.commentTextField.getText());
                        updateDisplayTable();
                    }
                }
        );
    }

    private void setEditButtonAction() {
        entryTab.editEntryButton.addActionListener(
                e -> {
                    if (!entryTab.dateTextField.getText().matches(
                            "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$"
                    )) {
                        JOptionPane.showMessageDialog(null, "wrong date format!\nuse dd.mm.yyyy");
                    } else if (!entryTab.amountTextField.getText().matches(
                            "[+-]?[0-9]*([.][0-9])?([.][0-9][0-9])?"
                    )) {
                        JOptionPane.showMessageDialog(null, "wrong amount format!\nuse x*.xx");
                    } else {
                        entryControl.setEntry(selectedEntry, entryTab.dateTextField.getText(), entryTab.amountTextField.getText(), entryTab.categoryTextField.getText(), entryTab.placeTextField.getText(), entryTab.commentTextField.getText());
                        updateDisplayTable();
                    }
                }
        );
    }

    private void setDeleteButtonAction() {
        entryTab.deleteEntryButton.addActionListener(
                e -> {
                    if (selectedEntry != -1) {
                        entryControl.deleteEntry(selectedEntry);
                        selectedEntry = -1;
                        entryTab.deleteEntryButton.setEnabled(false);
                        DefaultTableModel model = (DefaultTableModel) entryTab.entryDisplayTable.getModel();
                        model.removeRow(entryTab.entryDisplayTable.getSelectedRow());
                    }
                }
        );
    }

    private void addMouseListenerToTable() {
        entryTab.entryDisplayTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                entryTab.deleteEntryButton.setEnabled(true);

                //get Selected Entry
                selectedEntry = (Integer) entryTab.entryDisplayTable.getModel().getValueAt(entryTab.entryDisplayTable.getSelectedRow(), 0);

                //display Entry in Entry Overview
                Entry entry = entryControl.getEntry(selectedEntry);
                updateEntryOverview(entry);
            }
        });
    }

    public void updateDisplayTable() {
        DefaultTableModel model = (DefaultTableModel) entryTab.entryDisplayTable.getModel();
        model.setRowCount(0);
        ArrayList<Entry> entryArrayList = entryControl.sort(sortingAttribute, entryControl.filterEntryList(entryControl.getEntriesAsList()));
        for (Entry entry : entryArrayList) {
            addEntryInDisplayTable(entry, model);
        }
    }

    private void addEntryInDisplayTable(Entry entry, DefaultTableModel model) {
        model.addRow(new Object[]{entry.getId(), entry.getDate(), entry.getAmount(), entry.getCategory(), entry.getPayer(), entry.getPlace(), entry.getComment()});
        selectedEntry = entry.getId();
    }

    private void updateEntryOverview(Entry entry) {
        entryTab.dateTextField.setText(entry.getDate());
        entryTab.amountTextField.setText(String.valueOf(entry.getAmount()));
        entryTab.categoryTextField.setText(entry.getCategory());
        entryTab.payerTextField.setText(entry.getPayer());
        entryTab.placeTextField.setText(entry.getPlace());
        entryTab.commentTextField.setText(entry.getComment());
    }

    public void setSortingAttribute(EntryAttribute sortingAttribute) {
        this.sortingAttribute = sortingAttribute;
        updateDisplayTable();
    }

    public EntryTab getEntryTab(){
        return entryTab;
    }
}
