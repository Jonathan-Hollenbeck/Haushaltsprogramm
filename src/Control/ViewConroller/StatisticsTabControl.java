package Control.ViewConroller;

import Control.EntryControl;
import Model.Entry;
import Util.EntryAttribute;
import View.StatisticsTab;

import java.util.*;

public class StatisticsTabControl {

    private StatisticsTab statisticsTab;

    private EntryControl entryControl;

    public StatisticsTabControl(EntryControl entryControl) {
        this.entryControl = entryControl;
        statisticsTab = new StatisticsTab();

        setComboBoxListeners();
    }

    private void setComboBoxListeners() {
        statisticsTab.xAxis.addActionListener(e -> {
            update();
        });
        statisticsTab.graphMode.addActionListener(e -> {
            update();
        });
        statisticsTab.datePackMode.addActionListener(e -> {
            update();
        });
    }

    public void update() {
        ArrayList<Entry> entries = entryControl.filterEntryList(entryControl.getEntriesAsList());
        //set general Information
        double sum = 0;
        HashSet<String> days = new HashSet<>();
        HashSet<String> months = new HashSet<>();
        HashSet<String> years = new HashSet<>();

        for (Entry entry : entries) {
            sum += entry.getAmount();
            days.add(entry.getDate());
            months.add(entry.getMonth() + "." + entry.getYear());
            years.add(entry.getYear());
        }

        statisticsTab.sumValueLabel.setText("" + roundToTwo(sum));
        statisticsTab.dayAverageValueLabel.setText("" + roundToTwo(sum / (double) days.size()));
        statisticsTab.monthAverageValueLabel.setText("" + roundToTwo(sum / (double) months.size()));
        statisticsTab.yearAverageValueLabel.setText("" + roundToTwo(sum / (double) years.size()));

        //prep data for Graph
        ArrayList<String> keysAsList = new ArrayList<>();
        EntryAttribute sortingAttribute = EntryAttribute.date;
        HashMap<String, Double> data = new HashMap<>();
        switch ((String) statisticsTab.xAxis.getSelectedItem()) {
            case "Date":
                statisticsTab.datePackMode.setEnabled(true);
                for (Entry entry : entries) {
                    switch ((String) statisticsTab.datePackMode.getSelectedItem()) {
                        case "Day":
                            data = extendHashMap(data, entry.getDate(), entry.getAmount());
                            if (!keysAsList.contains(entry.getDate())) {
                                keysAsList.add(entry.getDate());
                            }
                            break;
                        case "Month":
                            data = extendHashMap(data, entry.getMonth() + "." + entry.getYear(), entry.getAmount());
                            if (!keysAsList.contains("01." + entry.getMonth() + "." + entry.getYear())) {
                                keysAsList.add("01." + entry.getMonth() + "." + entry.getYear());
                            }
                            break;
                        case "Year":
                            data = extendHashMap(data, entry.getYear(), entry.getAmount());
                            if (!keysAsList.contains("01.01." + entry.getYear())) {
                                keysAsList.add("01.01." + entry.getYear());
                            }
                            break;
                    }
                }
                break;
            case "Category":
                statisticsTab.datePackMode.setEnabled(false);
                sortingAttribute = EntryAttribute.category;
                for (Entry entry : entries) {
                    data = extendHashMap(data, entry.getCategory(), entry.getAmount());
                    if (!keysAsList.contains(entry.getCategory())) {
                        keysAsList.add(entry.getCategory());
                    }
                }
                break;
            case "Payer":
                statisticsTab.datePackMode.setEnabled(false);
                sortingAttribute = EntryAttribute.payer;
                for (Entry entry : entries) {
                    data = extendHashMap(data, entry.getPayer(), entry.getAmount());
                    if (!keysAsList.contains(entry.getPayer())) {
                        keysAsList.add(entry.getPayer());
                    }
                }
                break;
            case "Place":
                statisticsTab.datePackMode.setEnabled(false);
                sortingAttribute = EntryAttribute.place;
                for (Entry entry : entries) {
                    data = extendHashMap(data, entry.getPlace(), entry.getAmount());
                    if (!keysAsList.contains(entry.getPlace())) {
                        keysAsList.add(entry.getPlace());
                    }
                }
                break;
        }
        //create an Entry for every Key with respective Attribute set
        ArrayList<Entry> sortedEntryList = new ArrayList<>();
        switch (sortingAttribute) {
            case date:
                for (int i = 0; i < keysAsList.size(); i++) {
                    sortedEntryList.add(new Entry(keysAsList.get(i), 0, "", "", "", "", 0));
                }
                break;
            case category:
                for (int i = 0; i < keysAsList.size(); i++) {
                    sortedEntryList.add(new Entry("", 0, keysAsList.get(i), "", "", "", 0));
                }
                break;
            case payer:
                for (int i = 0; i < keysAsList.size(); i++) {
                    sortedEntryList.add(new Entry("", 0, "", keysAsList.get(i), "", "", 0));
                }
                break;
            case place:
                for (int i = 0; i < keysAsList.size(); i++) {
                    sortedEntryList.add(new Entry("", 0, "", "", keysAsList.get(i), "", 0));
                }
                break;
        }
        //sort that shit
        entryControl.sort(sortingAttribute, sortedEntryList);
        //put everything in a Hashmap where the key is the key and the value the position of this key in the sorting order
        HashMap<String, Integer> keySortingPositions = new HashMap<>();
        switch ((String) statisticsTab.xAxis.getSelectedItem()) {
            case "Date":
                switch ((String) statisticsTab.datePackMode.getSelectedItem()) {
                    case "Day":
                        for (int i = 0; i < sortedEntryList.size(); i++) {
                            keySortingPositions.put(sortedEntryList.get(i).getDate(), i);
                        }
                        break;
                    case "Month":
                        for (int i = 0; i < sortedEntryList.size(); i++) {
                            keySortingPositions.put(sortedEntryList.get(i).getMonth() + "." + sortedEntryList.get(i).getYear(), i);
                        }
                        break;
                    case "Year":
                        for (int i = 0; i < sortedEntryList.size(); i++) {
                            keySortingPositions.put(sortedEntryList.get(i).getYear(), i);
                        }
                        break;
                }
                break;
            case "Category":
                for (int i = 0; i < sortedEntryList.size(); i++) {
                    keySortingPositions.put(sortedEntryList.get(i).getCategory(), i);
                }
                break;
            case "Payer":
                for (int i = 0; i < sortedEntryList.size(); i++) {
                    keySortingPositions.put(sortedEntryList.get(i).getPayer(), i);
                }
                break;
            case "Place":
                for (int i = 0; i < sortedEntryList.size(); i++) {
                    keySortingPositions.put(sortedEntryList.get(i).getPlace(), i);
                }
                break;
        }

        statisticsTab.graphGenerator.setData(data, keySortingPositions, (String) statisticsTab.graphMode.getSelectedItem(), "Category");
    }

    private double roundToTwo(double value) {
        return Math.floor(100 * (value + 0.005)) / 100;
    }

    private HashMap<String, Double> extendHashMap(HashMap<String, Double> hashMap, String string, double value) {
        if (!hashMap.keySet().contains(string)) {
            hashMap.put(string, value);
        } else {
            hashMap.put(string, hashMap.get(string) + value);
        }
        return hashMap;
    }

    public StatisticsTab getStatisticsTab() {
        return statisticsTab;
    }
}
