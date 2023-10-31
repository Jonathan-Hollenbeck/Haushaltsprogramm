package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryTab extends JPanel {

    public JPanel entryAddEditPanel;

    public JTable entryDisplayTable;

    public JTextField dateTextField, amountTextField, categoryTextField, payerTextField, placeTextField, commentTextField;

    public JButton addEntryButton, editEntryButton, deleteEntryButton;

    public EntryTab() {
        setLayout(new GridLayout(1, 2));

        //EntryAddEditPanel
        entryAddEditPanel = new JPanel();
        entryAddEditPanel.setLayout(new GridLayout(7, 1));

        //TextFields

        Font textFieldFont = new Font("Arial", Font.BOLD, 20);

        String pattern = "dd.MM.yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());

        dateTextField = new JTextField(dateInString);
        dateTextField.setFont(textFieldFont);
        amountTextField = new JTextField();
        amountTextField.addFocusListener(setPromptText(amountTextField, "Amount"));
        amountTextField.setFont(textFieldFont);
        categoryTextField = new JTextField();
        categoryTextField.addFocusListener(setPromptText(categoryTextField, "Category"));
        categoryTextField.setFont(textFieldFont);
        payerTextField = new JTextField();
        payerTextField.addFocusListener(setPromptText(payerTextField, "Payer"));
        payerTextField.setFont(textFieldFont);
        placeTextField = new JTextField();
        placeTextField.addFocusListener((setPromptText(placeTextField, "Place")));
        placeTextField.setFont(textFieldFont);
        commentTextField = new JTextField();
        commentTextField.addFocusListener(setPromptText(commentTextField, "Comment"));
        commentTextField.setFont(textFieldFont);

        entryAddEditPanel.add(dateTextField);
        entryAddEditPanel.add(amountTextField);
        entryAddEditPanel.add(categoryTextField);
        entryAddEditPanel.add(payerTextField);
        entryAddEditPanel.add(placeTextField);
        entryAddEditPanel.add(commentTextField);

        //Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        addEntryButton = new JButton("add");
        editEntryButton = new JButton("edit");
        deleteEntryButton = new JButton("delete");
        deleteEntryButton.setEnabled(false);

        buttonPanel.add(addEntryButton);
        buttonPanel.add(editEntryButton);
        buttonPanel.add(deleteEntryButton);

        entryAddEditPanel.add(buttonPanel);

        //EntryDisplayPanel
        entryDisplayTable = new JTable();
        entryDisplayTable.setModel(new DefaultTableModel(
                                           new Object[][]{}, new String[]{"Id", "Date", "Amount", "Category", "Payer", "Place", "Comment"}
                                   ) {
                                       @Override
                                       public boolean isCellEditable(int row, int column) {
                                           return false;
                                       }
                                   }
        );
        entryDisplayTable.getSelectionModel().setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        entryDisplayTable.getTableHeader().setReorderingAllowed(false);

        //adding everything to the EntryTab
        add(entryAddEditPanel);
        add(new JScrollPane(entryDisplayTable));
    }

    private FocusListener setPromptText(JTextField textField, String promptText) {
        FocusListener focusListener = new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(promptText);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(promptText)) {
                    textField.setText("");
                }
            }
        };
        textField.setText(promptText);

        return focusListener;
    }

}
