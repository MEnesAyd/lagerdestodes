

public class Produkte {
    private int auftragID;
    private String auftragsart;
    private String name;
    private String attribute1;
    private String attribute2;
    private int belohnung;

    public Produkte(int auftragID, String auftragsart, String name, String attribute1, String attribute2, int belohnung) {
        this.auftragID = auftragID;
        this.auftragsart = auftragsart;
        this.name = name;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.belohnung = belohnung;
    }

    public String getAuftragsart() {
        return auftragsart;
    }

    public String getName() {
        return name;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public int getBelohnung() {
        return belohnung;
    }
}
