

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVListe {

    public List<Produkte> getList() {
        int attributeID;
        int behlohnung;

        List<Produkte> l = new ArrayList<>();

        //1.Zeile von CSV Datei überspringen
        try {
            BufferedReader rd = new BufferedReader(new FileReader("Leistungsnachweis.csv"));

            for (int i = 0; i < 1; i++) {
                rd.readLine(); //Skipt 1.Zeile
            }
            String line;
            for (line = rd.readLine(); line != null; line = rd.readLine()) {
                // Evtl noch ifs mit bedingungen, um leere Zeilen auszugrenzen
                String[] attribute = line.split(";");
                attributeID = Integer.parseInt(attribute[0]);
                behlohnung = Integer.parseInt(attribute[5]);
                Produkte p = new Produkte(attributeID, attribute[1], attribute[2], attribute[3], attribute[4], behlohnung);
                l.add(p);


            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return l;
    }


}
