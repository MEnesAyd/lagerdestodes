
import javax.swing.*;
import java.util.*;
import java.util.List;

public class Abwicklung {
    private final Lager la;
    private final List<Product> l;
    private int kontostand;
    private List<Kontoeintrag> kontoverlauf;
    private boolean einlagerungErfolgreich;
    private boolean auslagerungErfolgreich;
    private boolean umlagernErfolgreich;
    private boolean umlagernLeerePalette;


    public Abwicklung() {
        CSVListe p = new CSVListe();
        kontoverlauf = new ArrayList<Kontoeintrag>();
        la = new Lager();
        l = p.getList();
        kontostand = 0;
    }

    public Boolean umlagernLeerePalette() {
        return umlagernLeerePalette;
    }

    public Boolean umlagernErfolgreich() {
        return umlagernErfolgreich;
    }

    public Boolean auslagerungErfolgreich() {
        return auslagerungErfolgreich;
    }

    public Boolean einlagerungErfolgreich() {
        return einlagerungErfolgreich;
    }

    public void naechsterEintrag(int i, int x, int y, int z) {

        if (Objects.equals(l.get(i).getType(), "Einlagerung")) {

            if (!la.isEmpty(x, y, z)) {
                JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da die Palette nicht leer ist", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                einlagerungErfolgreich = false;
            } else {
                if (Objects.equals(l.get(i).getName(), "Holz")) {
                    einlagernHolz(i, x, y, z);
                } else if (Objects.equals(l.get(i).getName(), "Stein")) {
                    einlagernStein(i, x, y, z);
                } else if (Objects.equals(l.get(i).getName(), "Papier")) {
                    einlagernPapier(i, x, y, z);
                }


            }


        } else if (Objects.equals(l.get(i).getType(), "Auslagerung")) {

            if (la.isEmpty(x, y, z)) {
                JOptionPane.showMessageDialog(null, "Auslagerung nicht möglich, da die Palette leer ist", "Fehler: Auslagerung", JOptionPane.ERROR_MESSAGE);
                auslagerungErfolgreich = false;
            } else {
                if (!la.foundProduct(l.get(i), x, y, z)) {
                    JOptionPane.showMessageDialog(null, "Auslagerung nicht möglich, da das Produkt nicht gefunden wurde", "Fehler: Auslagerung", JOptionPane.ERROR_MESSAGE);
                    auslagerungErfolgreich = false;
                } else {
                    if (Objects.equals(la.getSearchedName(), "Holz")) {
                        auslagernHolz(i, x, y, z);
                    } else {
                        auslagernWiePapierStein(i, x, y, z);
                    }
                }
            }
        }
    }


    public int aendereKontostand(int differenz, String nachricht) {
        kontostand += differenz;
        Kontoeintrag neuerEintrag = new Kontoeintrag(nachricht, differenz);
        kontoverlauf.add(neuerEintrag);
        return kontostand;
    }

    public int getKontostand() {
        return kontostand;
    }

    public List<Kontoeintrag> getKontoverlauf() {
        return kontoverlauf;
    }

    public void verschrotten(int x, int y, int z) {
        if (la.isEmpty(x, y, z)) {
            JOptionPane.showMessageDialog(null, "Prdoukt kann nicht verschrottet werden, da die Palette leer ist.", "Fehler: Verschrotten", JOptionPane.ERROR_MESSAGE);
        } else {
            if (Objects.equals(la.getProduktName(x, y, z), "Holz") && Objects.equals(la.getProdukt(x, y, z).getValueB(), "Balken")) {
                la.insertProduct(x, y, 1, null);
                la.insertProduct(x, y, 0, null);
            } else {
                la.insertProduct(x, y, z, null);
            }
            JOptionPane.showMessageDialog(null, "Das ausgewählte Produkt wurde für 300 Geldeinheiten verschrottet.", "Info: Verschrotten", JOptionPane.INFORMATION_MESSAGE);
            aendereKontostand(-300, "Verschrottet");
        }

    }

    public void loeschen(int x, int y, int z) {
        if (Objects.equals(la.getProduktName(x, y, z), "Holz") && Objects.equals(la.getProdukt(x, y, z).getValueB(), "Balken")) {
            la.insertProduct(x, y, 1, null);
            la.insertProduct(x, y, 0, null);
        } else {
            la.insertProduct(x, y, z, null);
        }
    }


    //Einlagern/Auslagern der Produkte
    public void einlagernHolz(int i, int x, int y, int z) {
        if (Objects.equals(l.get(i).getValueB(), "Balken")) {
            if ((z == 0 && !la.isEmpty(x, y, 1) || (z == 1 && !la.isEmpty(x, y, 0)))) {
                System.out.println("Einlagerung nicht möglich ");
                einlagerungErfolgreich = false;
                JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da der Platz nicht ausreicht", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);

            } else {
                la.insertProduct(x, y, 0, l.get(i));
                la.insertProduct(x, y, 1, l.get(i));
                System.out.println("Einlagerung erfolgreich");
                einlagerungErfolgreich = true;
                auslagerungErfolgreich = false;
                aendereKontostand(l.get(i).getReward(), "Eingelagert");
            }
        } else {
            la.insertProduct(x, y, z, l.get(i));
            System.out.println("Einlagerung erfolgreich");
            einlagerungErfolgreich = true;
            auslagerungErfolgreich = false;
            aendereKontostand(l.get(i).getReward(), "Eingelagert");
        }

    }

    public void einlagernStein(int i, int x, int y, int z) {
        if (Objects.equals(l.get(i).getValueB(), "Schwer")) {
            if (!la.isEmpty(x, 0, z) || (y != 0)) {
                if (!la.isEmpty(x, 0, z)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da der Stein zu schwer ist!", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;

                } else if ((y != 0)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da schwere Steine nur für die unteren Paletten geeignet sind!", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                }

            } else {
                la.insertProduct(x, y, z, l.get(i));
                System.out.println("Einlagerung erfolgreich");
                einlagerungErfolgreich = true;
                auslagerungErfolgreich = false;
                aendereKontostand(l.get(i).getReward(), "Eingelagert");
            }

        } else if (Objects.equals(l.get(i).getValueB(), "Mittel")) {
            if ((y == 0 && !la.isEmpty(x, y, z)) || y == 1 && !la.isEmpty(x, y, z) || y == 2) {
                if (y == 0 && !la.isEmpty(x, y, z)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da die unteren Paletten besetzt sind", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                } else if (y == 1 && !la.isEmpty(x, y, z)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da die mittleren Paletten besetzt sind", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                } else if (y == 2) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da mittelschwere Paletten nicht oben platziert werden dürfen", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                }

            } else {
                la.insertProduct(x, y, z, l.get(i));
                System.out.println("Einlagerung erfolgreich");
                einlagerungErfolgreich = true;
                auslagerungErfolgreich = false;
                aendereKontostand(l.get(i).getReward(), "Eingelagert");
            }

        } else if (Objects.equals(l.get(i).getValueB(), "Leicht")) {
            la.insertProduct(x, y, z, l.get(i));
            System.out.println("Einlagerung erfolgreich");
            einlagerungErfolgreich = true;
            auslagerungErfolgreich = false;
            aendereKontostand(l.get(i).getReward(), "Eingelagert");
        }
    }

    public void einlagernPapier(int i, int x, int y, int z) {
        la.insertProduct(x, y, z, l.get(i));
        System.out.println("Einlagerung erfolgreich");
        einlagerungErfolgreich = true;
        auslagerungErfolgreich = false;
        aendereKontostand(l.get(i).getReward(), "Eingelagert");
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void auslagernHolz(int i, int x, int y, int z) {

        if (Objects.equals(l.get(i).getValueB(), "Balken")) {
            if (z == 0) {
                la.insertProduct(x, y, 1, null);
                la.insertProduct(x, y, 0, null);
                aendereKontostand(l.get(i).getReward(), "Ausgelagert");
                System.out.println("Auslagern erfolgreich");
                auslagerungErfolgreich = true;
                einlagerungErfolgreich = false;
            } else if (z == 1) {
                la.insertProduct(x, y, 1, null);
                la.insertProduct(x, y, 0, null);
                aendereKontostand(l.get(i).getReward(), "Ausgelagert");
                System.out.println("Auslagern erfolgreich");
                auslagerungErfolgreich = true;
                einlagerungErfolgreich = false;
            }
        } else {
            auslagernWiePapierStein(i, x, y, z);
        }
    }

    public void auslagernWiePapierStein(int i, int x, int y, int z) {
        if (z == 1) {
            if (la.isEmpty(x, y, 0)) {
                la.insertProduct(x, y, z, null);
                aendereKontostand(l.get(i).getReward(), "Ausgelagert");
                System.out.println("Auslagern erfolgreich");
                auslagerungErfolgreich = true;
                einlagerungErfolgreich = false;
            } else {
                JOptionPane.showMessageDialog(null, "Auslagerung nicht möglich, da ein anderes Produkt das aktuelle Produkt blockiert", "Fehler: Auslagerung", JOptionPane.ERROR_MESSAGE);
                auslagerungErfolgreich = false;
            }

        } else {
            la.insertProduct(x, y, z, null);
            aendereKontostand(l.get(i).getReward(), "Ausgelagert");
            System.out.println("Auslagern erfolgreich");
            auslagerungErfolgreich = true;
            einlagerungErfolgreich = false;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void umlagern(int x, int y, int z, int zX, int zY, int zZ) { //Muss gemacht werden
        //Produkt auswählen

        if (la.isEmpty(x, y, z)) {
            JOptionPane.showMessageDialog(null, "Die ausgewählte Palette ist leer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
            umlagernErfolgreich = false;

        } else if (!la.isEmpty(zX, zY, zZ)) {
            umlagernLeerePalette=false;
            //Aktueles Produkt Papier
            if (Objects.equals(la.getProdukt(x, y, z).getName(), "Papier")) {
                aktProduktPapier(x, y, z, zX, zY, zZ);
            }


            //Aktuelles Produkt Holz
            else if (Objects.equals(la.getProdukt(x, y, z).getName(), "Holz")) {
                aktProduktHolz(x, y, z, zX, zY, zZ);

            }


            //Aktuelles Produkt Stein
            else if (Objects.equals(la.getProdukt(x, y, z).getName(), "Stein")) {
                aktProduktStein(x, y, z, zX, zY, zZ);

            }
        }//Ziel Leer
        else if (la.isEmpty(zX, zY, zZ)) {
            //Aktuelles Produkt Papier
            if (Objects.equals(la.getProdukt(x, y, z).getName(), "Papier")) {
                EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
            }
            //Aktuelles Produkt Stein
            else if(Objects.equals(la.getProdukt(x, y, z).getName(), "Stein")){
                if(Objects.equals(la.getProdukt(x,y,z).getValueB(),"Schwer")){
                    if(zY!=0){
                        //Stein zu schwer
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    }else{
                        EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                    }
                }else if(Objects.equals(la.getProdukt(x,y,z).getValueB(),"Mittel")){
                    if(zY==2){
                        //Stein zu schwer
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    }else{
                        EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                    }
                }else if(Objects.equals(la.getProdukt(x,y,z).getValueB(),"Leicht")){
                    EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                }
            }
            //Aktuelles Produkt Holz
            else if(Objects.equals(la.getProdukt(x, y, z).getName(), "Holz")){
                //Aktuelles Produkt kein Balken
                if(!Objects.equals(la.getProdukt(x,y,z).getValueB(),"Balken")){
                    EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                }
                //Aktuelles Balken
                //TODO: Anschauen
                else{
                    BalkenZuLeer(x,y,z,zX,zY,zZ);
                }
            }
        }












    }

    public void aktProduktPapier(int x, int y, int z, int zX, int zY, int zZ) { //Muss ich schauen

        //Zielprodukt Papier
        if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Papier")) {
            zielproduktPapier(x, y, z, zX, zY, zZ);
        }

        //Zielprodukt Stein
        else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Stein")) {
            if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                if (y != 0) {
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielProduktStein(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                if (y == 2) {
                    //Stein zu schwer
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;

                } else {
                    zielProduktStein(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Leicht")) {
                zielProduktStein(x, y, z, zX, zY, zZ);
            }
        }

        //Zielprodukt Holz
        else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Holz")) {
            zielProduktHolz(x, y, z, zX, zY, zZ);
        }



    }


    public void aktProduktHolz(int x, int y, int z, int zX, int zY, int zZ) {
        if (!Objects.equals(la.getProdukt(x, y, z).getValueB(), "Balken")) {
            //Ziel Papier
            if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Papier")) {
                zielproduktPapier(x, y, z, zX, zY, zZ);
            }
            //Ziel Stein
            else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Stein")) {
                if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                    if (y != 0) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                    if (y == 2) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }

                } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Leicht")) {
                    zielProduktStein(x, y, z, zX, zY, zZ);
                }

            }
            //Ziel Holz
            else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Holz")) {
                zielProduktHolz(x, y, z, zX, zY, zZ);
            }
        } else {
            //Ziel Papier
            if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Papier")) {
                balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
            }
            //Ziel Stein
            else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Stein")) {
                if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                    if (y != 0) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
                    }
                } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                    if (y == 2) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
                    }

                } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Leicht")) {
                    balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
                }

            }

            //Ziel Holz
            else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Holz")) {
                balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
            }
        }
    }

    public void aktProduktStein(int x, int y, int z, int zX, int zY, int zZ) {
        //Zielprodukt Papier
        if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Papier")) {
            if (Objects.equals(la.getProdukt(x, y, z).getValueB(), "Schwer")) {
                if (zY != 0) {
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielproduktPapier(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(la.getProdukt(x, y, z).getValueB(), "Mittel")) {
                if (zY == 2) {
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielproduktPapier(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(la.getProdukt(x, y, z).getValueB(), "Leicht")) {
                zielproduktPapier(x, y, z, zX, zY, zZ);
            }
        }


        //Zielprodukt Stein
        else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Stein")) {
            if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Stein")) {
                if (Objects.equals(la.getProdukt(x, y, z).getValueB(), "Schwer")) {
                    if (zY != 0) {
                        //Der Stein ist viel zu schwer für eine höhere Ebene
                        JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                } else if (Objects.equals(la.getProdukt(x, y, z).getValueB(), "Mittel")) {
                    if (zY == 2) {
                        //Der Stein ist viel zu schwer für eine höhere Ebene
                        JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                } else {
                    if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                        if (y != 0) {
                            //Fehler
                            JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                            umlagernErfolgreich = false;
                        } else {
                            zielProduktStein(x, y, z, zX, zY, zZ);
                        }
                    } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                        if (y == 2) {
                            //Fehler
                            JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                            umlagernErfolgreich = false;
                        } else {
                            zielProduktStein(x, y, z, zX, zY, zZ);
                        }
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                }
            }

        }


        //Zielprodukt Holz
        else if (Objects.equals(la.getProdukt(zX, zY, zZ).getName(), "Holz")) {
            if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                if (y != 0) {
                    //Fehler
                    JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielProduktHolz(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                if (y == 2) {
                    //Fehler
                    JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielProduktHolz(x, y, z, zX, zY, zZ);
                }
            } else {
                zielProduktHolz(x, y, z, zX, zY, zZ);
            }

        }

    }


    public void balkenZuEinfachenProdukt(int x, int y, int z, int zX, int zY, int zZ) {
        if (z == 0 && zZ == 0) {
            if (!la.isEmpty(zX, zY, 1)) {
                JOptionPane.showMessageDialog(null, "Hinter dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 1, zX, zY, 1);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (!la.isEmpty(zX, zY, 0)) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 1, zX, zY, 0);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 1 && zZ == 0) {
            if (!la.isEmpty(zX, zY, 1)) {
                JOptionPane.showMessageDialog(null, "Hinter dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 0, zX, zY, 1);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 1 && zZ == 1) {
            if (!la.isEmpty(zX, zY, 0)) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 0, zX, zY, 0);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        }
    }

    public void zielproduktPapier(int x, int y, int z, int zX, int zY, int zZ) {
        if (z == 1 && zZ == 1) {
            if (!la.isEmpty(zX, zY, 0) || !la.isEmpty(x, y, 0)) {
                //Vor dem Zielprodukt ist ein anderes Produkt
                JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (x == zX && y == zY) {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!la.isEmpty(zX, zY, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }

        } else if (z == 1 && zZ == 0) {
            if (x == zX && y == zY) {
                //Tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!la.isEmpty(x, y, 0)) {
                    //Aktuelle produkt wird blockiert
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }
        } else if (z == 0 && zZ == 0) {
            //tausche
            la.tauscheProdukt(x, y, z, zX, zY, zZ);
            System.out.println("Erfolgreich umgelagert");
            umlagernErfolgreich = true;
            aendereKontostand(-100, "Umgelagert");
        }
    }

    public void zielProduktStein(int x, int y, int z, int zX, int zY, int zZ) {
        if (z == 1 && zZ == 1) {
            if (!la.isEmpty(zX, zY, 0) || !la.isEmpty(x, y, 0)) {
                //Vor dem Zielprodukt ist ein anderes Produkt
                JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (x == zX && y == zY) {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!la.isEmpty(zX, zY, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }

        } else if (z == 1 && zZ == 0) {
            if (x == zX && y == zY) {
                //Tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!la.isEmpty(x, y, 0)) {
                    //Aktuelle produkt wird blockiert
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }
        } else if (z == 0 && zZ == 0) {
            //tausche
            la.tauscheProdukt(x, y, z, zX, zY, zZ);
            System.out.println("Erfolgreich umgelagert");
            umlagernErfolgreich = true;
            aendereKontostand(-100, "Umgelagert");
        }
    }

    public void zielProduktHolz(int x, int y, int z, int zX, int zY, int zZ) {
        if (Objects.equals(la.getProdukt(zX, zY, zZ).getValueB(), "Balken")) {
            if (z == 0 && zZ == 0) {
                if (!la.isEmpty(x, y, 1)) {
                    JOptionPane.showMessageDialog(null, "Hinter dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    la.tauscheProdukt(x, y, 1, zX, zY, 1);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }

            } else if (z == 0 && zZ == 1) {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 1, zX, zY, 0);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");

            } else if (z == 1 && zZ == 0) {
                if (!la.isEmpty(x, y, 0)) {
                    JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    la.tauscheProdukt(x, y, 0, zX, zY, 1);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }


            } else if (z == 1 && zZ == 1) {
                if (!la.isEmpty(x, y, 0)) {
                    JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    la.tauscheProdukt(x, y, 0, zX, zY, 0);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }

            }
        } else {
            if (z == 1 && zZ == 1) {
                if (!la.isEmpty(zX, zY, 0) || !la.isEmpty(x, y, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            } else if (z == 0 && zZ == 1) {
                if (x == zX && y == zY) {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                } else {
                    if (!la.isEmpty(zX, zY, 0)) {
                        //Vor dem Zielprodukt ist ein anderes Produkt
                        JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        //tausche
                        la.tauscheProdukt(x, y, z, zX, zY, zZ);
                        System.out.println("Erfolgreich umgelagert");
                        umlagernErfolgreich = true;
                        aendereKontostand(-100, "Umgelagert");
                    }
                }

            } else if (z == 1 && zZ == 0) {
                if (x == zX && y == zY) {
                    //Tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                } else {
                    if (!la.isEmpty(zX, zY, 1)) {
                        //Aktuelle produkt wird blockiert
                        JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        //tausche
                        la.tauscheProdukt(x, y, z, zX, zY, zZ);
                        System.out.println("Erfolgreich umgelagert");
                        umlagernErfolgreich = true;
                        aendereKontostand(-100, "Umgelagert");
                    }
                }
            } else if (z == 0 && zZ == 0) {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        }
    }

    public void EinzelproduktZuLeer(int x, int y, int z, int zX, int zY, int zZ){
        umlagernErfolgreich=false;
        if (z == 1 && zZ == 1) {
            if (!la.isEmpty(zX, zY, 0) || !la.isEmpty(x, y, 0)) {
                //Vor dem Zielprodukt ist ein anderes Produkt
                JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (x == zX && y == zY) {
                //tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!la.isEmpty(zX, zY, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernLeerePalette = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernLeerePalette = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }

        } else if (z == 1 && zZ == 0) {
            if (x == zX && y == zY) {
                //Tausche
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!la.isEmpty(x, y, 0)) {
                    //Aktuelle produkt wird blockiert
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernLeerePalette = false;
                } else {
                    //tausche
                    la.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernLeerePalette = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }
        } else if (z == 0 && zZ == 0) {
            //tausche
            la.tauscheProdukt(x, y, z, zX, zY, zZ);
            System.out.println("Erfolgreich umgelagert");
            umlagernLeerePalette = true;
            aendereKontostand(-100, "Umgelagert");
        }
    }

    public void BalkenZuLeer(int x, int y, int z, int zX,int zY, int zZ){
        umlagernErfolgreich=false;
        if (z == 0 && zZ == 0) {
            if (!la.isEmpty(zX, zY, 1)||(!la.isEmpty(zX,zY,0))) {
                JOptionPane.showMessageDialog(null, "Ein Anderes Produkt blockiert die Umlagerung und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 1, zX, zY, 1);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            }

        } else if (z == 0 && zZ == 1) {
            if(!la.isEmpty(zX, zY, 1)||(!la.isEmpty(zX,zY,0))){
                JOptionPane.showMessageDialog(null, "Ein Anderes Produkt blockiert die Umlagerung und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);

            }else{
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 1, zX, zY, 0);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            }


        } else if (z == 1 && zZ == 0) {
            if (!la.isEmpty(zX, zY, 1)||(!la.isEmpty(zX,zY,0))) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 0, zX, zY, 1);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            }


        } else if (z == 1 && zZ == 1) {
            if (!la.isEmpty(zX, zY, 1)||(!la.isEmpty(zX,zY,0))) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                la.tauscheProdukt(x, y, z, zX, zY, zZ);
                la.tauscheProdukt(x, y, 0, zX, zY, 0);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                aendereKontostand(-100, "Umgelagert");
            }

        }
    }

}

