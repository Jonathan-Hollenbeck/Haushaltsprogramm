package Control;

import Model.Entry;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Serializer {

    public Serializer() {
        createSaveFolder();
    }

    private void createSaveFolder() {
        File directory = new File("saves");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void save(String saveName, HashMap<Integer, Entry> entries) {
        String path = "./saves/" + saveName + ".ser";
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(entries);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + path);
        } catch (IOException i) {
            System.out.println("Serializing failed");
            i.printStackTrace();
        }
    }

    public HashMap<Integer, Entry> open(String saveName) {
        String path = "./saves/" + saveName + ".ser";

        File saveFile = new File(path);

        if (saveFile.exists() == true) {
            HashMap<Integer, Entry> entries = null;
            try {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                entries = (HashMap<Integer, Entry>) in.readObject();
                in.close();
                fileIn.close();
                System.out.println("Deserialized data in " + path + " was loaded");
            } catch (IOException i) {
                System.out.println("Deserialization failed");
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("ArrayList<Entry> class not found");
                c.printStackTrace();
            }

            if (entries != null) {
                return entries;
            } else {
                return new HashMap<Integer, Entry>();
            }
        } else {
            System.out.println("Savefile does not exist. No deserialization attempted.");
            return new HashMap<Integer, Entry>();
        }
    }

    public void saveAsCSV(String saveName, HashMap<Integer, Entry> entries) {
        String path = "./saves/" + saveName + ".csv";
        try {
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Date,Amount,Category,Payer,Place,Comment\n");
            for (Integer key : entries.keySet()) {
                Entry entry = entries.get(key);
                String entryAsCSV = entry.getDate() + "," + entry.getAmount() + "," + entry.getCategory()+ "," + entry.getPayer() + "," + entry.getPlace() + "," + entry.getComment() + "\n";
                bw.write(entryAsCSV);
            }
            bw.close();
            System.out.println("Serialized CSV data is saved in " + path);
        } catch (IOException e) {
            System.out.println("Serializing CSV failed");
            e.printStackTrace();
        }
    }

    public ArrayList<String> openCSV(String saveName){
        String path = "./saves/" + saveName + ".csv";
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("Serializing CSV failed. File does not exist");
                return null;
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String current;
            while((current = br.readLine()) != null) {
                arrayList.add(current);
            }
            br.close();
            System.out.println("Serialized CSV data is open from" + path);
            return arrayList;
        } catch (IOException e) {
            System.out.println("Serializing CSV failed");
            e.printStackTrace();
        }
        return null;
    }
}
