package Control.ViewConroller;

import Control.EntryControl;
import View.FilterBar;

import javax.swing.*;

public class FilterBarControl {

    private FilterBar filterBar;

    private EntryTabControl entryTabControl;

    private StatisticsTabControl statisticsTabControl;

    private EntryControl entryControl;

    public FilterBarControl(EntryControl entryControl, EntryTabControl entryTabControl, StatisticsTabControl statisticsTabControl) {
        this.entryTabControl = entryTabControl;
        this.entryControl = entryControl;
        this.statisticsTabControl = statisticsTabControl;

        filterBar = new FilterBar();

        setFilterButtonAction();
    }

    private void setFilterButtonAction() {
        filterBar.filterButton.addActionListener(e -> {
            if (!filterBar.amount.getText().matches(
                    "[-]?[0-9]*([.][0-9])?([.][0-9][0-9])?([-][-]?[0-9]*([.][0-9])?([.][0-9][0-9])?)?"
            )) {
                JOptionPane.showMessageDialog(null, "wrong amount format!\nuse x*.xx");
            } else {
                String[] filterAttributes = {filterBar.day.getText(), filterBar.month.getText(),
                        filterBar.year.getText(), filterBar.amount.getText(),
                        filterBar.category.getText(), filterBar.payer.getText(),
                        filterBar.place.getText(), filterBar.comment.getText()};
                entryControl.setFilterAttributes(filterAttributes);
                entryTabControl.updateDisplayTable();
                statisticsTabControl.update();
            }
        });
    }

    public FilterBar getFilterBar() {
        return filterBar;
    }
}
