package Control;

import Model.Entry;
import Util.EntryAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EntryControl {

    private Serializer serializer;

    private HashMap<Integer, Entry> entries;

    private ArrayList<Entry> displayEntryList;

    private int idCounter = 0;

    private String[] filterAttributes = {"", "", "", "", "", "", "", ""};

    public EntryControl() {
        entries = new HashMap<>();
        displayEntryList = new ArrayList<>();

        serializer = new Serializer();
    }

    //Entry Stuff
    public void addEntry(String date, String amount, String category, String payer, String place, String comment) {
        entries.put(idCounter, new Entry(date, Double.parseDouble(amount), category, payer, place, comment, idCounter));
        idCounter++;
        updateEntryList();
    }

    public void deleteEntry(int id) {
        entries.remove(id);
        updateEntryList();
    }

    public void setEntry(int id, String date, String amount, String category, String place, String comment) {
        entries.get(id).setDate(date);
        entries.get(id).setAmount(Double.parseDouble(amount));
        entries.get(id).setCategory(category);
        entries.get(id).setPlace(place);
        entries.get(id).setComment(comment);

        updateEntryList();
    }

    public Entry getEntry(int id) {
        return entries.get(id);
    }

    public ArrayList<Entry> getEntriesAsList() {
        return displayEntryList;
    }

    private void updateEntryList() {
        displayEntryList = new ArrayList<>();
        for (int key : entries.keySet()) {
            displayEntryList.add(entries.get(key));
        }
    }

    private boolean willEntryBeValid(ArrayList<String> arrayList) {
        if (!arrayList.get(0).matches(
                "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$"
        )) {
            return false;
        }
        if (!arrayList.get(1).matches(
                "[+-]?[0-9]*([.][0-9])?([.][0-9][0-9])?"
        )) {
            return false;
        }
        return true;
    }

    //Sorting
    public ArrayList<Entry> sort(EntryAttribute sortAttribute, ArrayList<Entry> entryArrayList) {
        if (entryArrayList.size() > 1) {
            switch (sortAttribute) {
                case date:
                    Entry[] entriesDate = new Entry[entryArrayList.size()];
                    entriesDate = entryArrayList.toArray(entriesDate);
                    entryArrayList.clear();
                    entryArrayList.addAll(Arrays.asList(mergeSort(entriesDate, entriesDate.length, (entry1, entry2) -> {
                        if (Integer.parseInt(entry1.getYear()) < Integer.parseInt(entry2.getYear())) { //year
                            return true;
                        } else if (Integer.parseInt(entry1.getYear()) == Integer.parseInt(entry2.getYear())
                                && Integer.parseInt(entry1.getMonth()) < Integer.parseInt(entry2.getMonth())) { // month
                            return true;
                        } else if (Integer.parseInt(entry1.getYear()) == Integer.parseInt(entry2.getYear())
                                && Integer.parseInt(entry1.getMonth()) == Integer.parseInt(entry2.getMonth())
                                && Integer.parseInt(entry1.getDay()) <= Integer.parseInt(entry2.getDay())) { //day
                            return true;
                        }
                        return false;
                    })));
                    break;
                case amount:
                    Entry[] entriesAmount = new Entry[entryArrayList.size()];
                    entriesAmount = entryArrayList.toArray(entriesAmount);
                    entryArrayList.clear();
                    entryArrayList.addAll(Arrays.asList(mergeSort(entriesAmount, entriesAmount.length, (entry1, entry2) -> entry1.getAmount() <= entry2.getAmount())));
                    break;
                case category:
                    Entry[] entriesCategory = new Entry[entryArrayList.size()];
                    entriesCategory = entryArrayList.toArray(entriesCategory);
                    entryArrayList.clear();
                    entryArrayList.addAll(Arrays.asList(mergeSort(entriesCategory, entriesCategory.length, (entry1, entry2) -> (entry1.getCategory().compareTo(entry2.getCategory()) <= 0) ? true : false)));
                    break;
                case payer:
                    Entry[] entriesPayer = new Entry[entryArrayList.size()];
                    entriesPayer = entryArrayList.toArray(entriesPayer);
                    entryArrayList.clear();
                    entryArrayList.addAll(Arrays.asList(mergeSort(entriesPayer, entriesPayer.length, (entry1, entry2) -> (entry1.getPayer().compareTo(entry2.getPayer()) <= 0) ? true : false)));
                    break;
                case place:
                    Entry[] entriesPlace = new Entry[entryArrayList.size()];
                    entriesPlace = entryArrayList.toArray(entriesPlace);
                    entryArrayList.clear();
                    entryArrayList.addAll(Arrays.asList(mergeSort(entriesPlace, entriesPlace.length, (entry1, entry2) -> (entry1.getPlace().compareTo(entry2.getPlace()) <= 0) ? true : false)));
                    break;
                case comment:
                    Entry[] entriesComment = new Entry[entryArrayList.size()];
                    entriesComment = entryArrayList.toArray(entriesComment);
                    entryArrayList.clear();
                    entryArrayList.addAll(Arrays.asList(mergeSort(entriesComment, entriesComment.length, (entry1, entry2) -> entry1.getComment().compareTo(entry2.getComment()) <= 0)));
                    break;
            }
        }
        return entryArrayList;
    }

    private Entry[] mergeSort(Entry[] a, int n, EntryCompare compare) {
        if (n < 2) {
            return new Entry[0];
        }
        int mid = n / 2;
        Entry[] l = new Entry[mid];
        Entry[] r = new Entry[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid, compare);
        mergeSort(r, n - mid, compare);

        return merge(a, l, r, mid, n - mid, compare);
    }

    private Entry[] merge(Entry[] a, Entry[] l, Entry[] r, int left, int right, EntryCompare compare) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (compare.compare(l[i], r[j])) { //l[i] <= r[j]
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
        return a;
    }

    private interface EntryCompare {
        boolean compare(Entry entry1, Entry entry2);
    }

    //Filtering
    public ArrayList<Entry> filterEntryList(ArrayList<Entry> entryArrayList) {
        ArrayList<Entry> filteredArrayList = new ArrayList<>();

        //amount Attribute filter
        String min = "", max = "";
        boolean left = true;
        if (filterAttributes[3].length() > 1) {
            for (int i = 0; i < filterAttributes[3].length(); i++) {
                if (filterAttributes[3].charAt(i) == '-') {
                    i++;
                    left = false;
                }
                if (left) {
                    min += filterAttributes[3].charAt(i);
                } else {
                    max += filterAttributes[3].charAt(i);
                }
            }
        }

        for (Entry entry : entryArrayList) {
            if ((filterAttributes[0].equals("") || filterAttributes[0].equals(entry.getDay()))
                    && (filterAttributes[1].equals("") || filterAttributes[1].equals(entry.getMonth()))
                    && (filterAttributes[2].equals("") || filterAttributes[2].equals(entry.getYear()))
                    && (filterAttributes[4].equals("") || filterAttributes[4].equals(entry.getCategory()))
                    && (filterAttributes[5].equals("") || filterAttributes[5].equals(entry.getPayer()))
                    && (filterAttributes[6].equals("") || filterAttributes[6].equals(entry.getPlace()))
                    && (filterAttributes[7].equals("") || filterAttributes[7].equals(entry.getComment()))) {
                if (min.equals("")) {
                    filteredArrayList.add(entry);
                } else if (max.equals("")) {
                    if (Double.parseDouble(min) == entry.getAmount())
                        filteredArrayList.add(entry);
                } else {
                    if (Double.parseDouble(min) < entry.getAmount() && Double.parseDouble(max) > entry.getAmount())
                        filteredArrayList.add(entry);
                }
            }
        }
        return filteredArrayList;
    }

    public void setFilterAttributes(String[] filterAttributes) {
        this.filterAttributes = filterAttributes;
    }

    //Serialize
    public void saveEntries(String saveName) {
        serializer.save(saveName, entries);
    }

    public void saveEntriesAsCSV(String saveName) {
        serializer.saveAsCSV(saveName, entries);
    }

    public void openEntries(String saveName) {
        entries = serializer.open(saveName);
        idCounter = 0;
        for (int key : entries.keySet()) {
            idCounter = (idCounter < key) ? key : idCounter;
        }
        idCounter++;
        updateEntryList();
    }

    public void openEntriesCSV(String saveName){
        entries.clear();
        ArrayList<String> rawArrayList = serializer.openCSV(saveName);
        ArrayList<String> seperatedList;
        idCounter = 0;
        for(String string : rawArrayList){
            seperatedList = getStringsSeperatedBy(',', string);
            if(willEntryBeValid(seperatedList)) {
                Entry entry = new Entry(seperatedList.get(0), Double.parseDouble(seperatedList.get(1)), seperatedList.get(2), seperatedList.get(3), seperatedList.get(4), seperatedList.get(5), idCounter);
                entries.put(idCounter, entry);
                idCounter++;
            }
        }
        updateEntryList();
    }

    private ArrayList<String> getStringsSeperatedBy(char seperator, String string){
        ArrayList<String> seperatedList = new ArrayList<>();
        String current = "";
        for(char c : string.toCharArray()){
            if(c != seperator){
                current += c;
            } else {
                seperatedList.add(current);
                current = "";
            }
        }
        seperatedList.add(current);
        return seperatedList;
    }

}
