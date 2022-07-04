

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;


public class GUI extends Component {
    protected final JButton[][][] btnLager;
    private final JButton btnNaechsterEintrag;
    private final JButton btnUmlagern;
    private final JButton btnVerschrotten;
    private final JButton btnAuftragBearbeiten, btnAuftragAblehnen;

    private final Label lblBelohnung;
    private final Label lblProduktname;
    private final Label lblAttribut1;
    private final Label lblAttribut2;
    private final Label lblAuftragsart;
    private final Label lblWert;
    private final JComboBox cmbbox;


    int i=0;
    int listeIndex = 0;
    int ausgewaehltesFach = 0;
    List<Product> l;
    Abwicklung aw = new Abwicklung();
    Lager lager;
    int belohnung;
    boolean btnBearbeitenIsPressed;
    boolean btnUmlagernIsPressed;
    boolean btnVerschrottenIsPressed;
    boolean btnFlaecheIsPressed;
    boolean btnNaechsterEintragIsPressed;
    int vX, vY, vZ;
    boolean waehlequelle;
    int[] fach = {-1, -1, -1};


    int balance;


    public GUI() {

        btnFlaecheIsPressed = false;
        btnBearbeitenIsPressed = false;
        btnUmlagernIsPressed = false;
        btnVerschrottenIsPressed = false;
        btnNaechsterEintragIsPressed=false;

        //Windows
        JFrame frame = new JFrame();
        frame.setTitle("Lagerspiel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout(10, 10));


        //Objekt erzeugung
        btnNaechsterEintrag = new JButton("Nächster Eintrag");
        btnUmlagern = new JButton("Umlagern");
        btnVerschrotten = new JButton("Verschrotten");
        btnAuftragBearbeiten = new JButton("Auftrag bearbeiten");
        btnAuftragAblehnen = new JButton("Auftrag ablehnen");

        cmbbox = new JComboBox(new String[] {"A", "B", "C"});
        cmbbox.addActionListener(this::comboBox);


        lblBelohnung = new Label("Belohnung: ");
        lblProduktname = new Label("Produktart: ");
        lblAttribut1 = new Label("1.Eigenschaft:");
        lblAttribut2 = new Label("2.Eigenschaft: ");
        lblAuftragsart = new Label("Auftragsart: ");
        lblWert = new Label("Wert: ");
        Label[] a = new Label[3];
        Label[] b = new Label[3];

        btnLager = new JButton[4][3][2];
        lager = new Lager();

        JPanel panelAuftrag = new JPanel();
        panelAuftrag.setLayout(new GridLayout(0, 2));
        panelAuftrag.add(btnAuftragBearbeiten);
        panelAuftrag.add(btnAuftragAblehnen);


        //Panels Außen
        JPanel belohnung = new JPanel();
        JPanel infos = new JPanel();
        JPanel buttonsAktion = new JPanel();
        JPanel panel5 = new JPanel();

        //Hinzufügen der Elemente

        belohnung.setLayout(new GridLayout(1, 2));
        infos.setLayout(new GridLayout(6, 2));
        buttonsAktion.setLayout(new GridLayout(0, 4));

        belohnung.add(lblBelohnung);

        infos.add(lblProduktname);
        infos.add(lblAuftragsart);
        infos.add(lblAttribut1);
        infos.add(lblAttribut2);
        infos.add(lblWert);
        infos.add(panelAuftrag);


        buttonsAktion.add(btnNaechsterEintrag);
        buttonsAktion.add(btnUmlagern);
        buttonsAktion.add(btnVerschrotten);
        buttonsAktion.add(cmbbox);



        //Eigenschaften
        lblBelohnung.setText("Belohnung: 0");
        belohnung.setBackground(Color.gray);
        panel5.setBackground(Color.blue);

        panel5.setLayout(new BorderLayout());

        belohnung.setPreferredSize(new Dimension(100, 50));
        infos.setPreferredSize(new Dimension(300, 100));
        buttonsAktion.setPreferredSize(new Dimension(100, 100));
        panel5.setPreferredSize(new Dimension(100, 100));

        //------------- sub panels --------------------

        //Objekt erzeugung
        JPanel oben = new JPanel();
        JPanel unten = new JPanel();
        JPanel links = new JPanel();
        JPanel rechts = new JPanel();
        JPanel mainInhalt = new JPanel();

        btnAuftragAblehnen.setEnabled(false);
        btnAuftragBearbeiten.setEnabled(false);
        btnUmlagern.setEnabled(false);
        btnVerschrotten.setEnabled(false);


        //Eigenschaften der Elemente
        oben.setBackground(Color.white);
        unten.setBackground(Color.white);
        links.setBackground(Color.white);
        rechts.setBackground(Color.white);
        mainInhalt.setBackground(Color.white);

        panel5.setLayout(new BorderLayout());
        oben.setPreferredSize(new Dimension(50, 50));
        unten.setPreferredSize(new Dimension(50, 50));
        links.setPreferredSize(new Dimension(50, 50));
        rechts.setPreferredSize(new Dimension(50, 50));
        mainInhalt.setPreferredSize(new Dimension(50, 50));

        links.setLayout(new GridLayout(0, 1));
        mainInhalt.setLayout(new GridLayout(6, 5));

        //Buttons erzegung
        String x1, y1;
        for (Integer j = 2; j > -1; j--) {
            a[j] = new Label("Hinten" + " " + (j + 1) + ".Ebene");
            mainInhalt.add(a[j]);
            for (Integer i = 0; i < 4; i++) {
                x1 = i.toString();
                y1 = j.toString();
                btnLager[i][j][1] = new JButton(" ");
                btnLager[i][j][1].addActionListener(this::ButtonPressed);
                btnLager[i][j][1].setName(x1 + y1 + 1);
                mainInhalt.add(btnLager[i][j][1]);
            }
        }

        String x0, y0;
        for (Integer j = 2; j > -1; j--) {
            b[j] = new Label("Vorne" + " " + (j + 1) + ".Ebene");
            mainInhalt.add(b[j]);
            for (Integer i = 0; i < 4; i++) {

                x0 = i.toString();
                y0 = j.toString();
                btnLager[i][j][0] = new JButton(" ");
                btnLager[i][j][0].addActionListener(this::ButtonPressed);
                btnLager[i][j][0].setName(x0 + y0 + 0);
                mainInhalt.add(btnLager[i][j][0]);

            }
        }


        panel5.add(oben, BorderLayout.NORTH);
        panel5.add(unten, BorderLayout.SOUTH);
        panel5.add(links, BorderLayout.WEST);
        panel5.add(rechts, BorderLayout.EAST);
        panel5.add(mainInhalt, BorderLayout.CENTER);

        //------------- sub panels --------------------

        frame.add(belohnung, BorderLayout.NORTH);
        frame.add(infos, BorderLayout.WEST);
        frame.add(buttonsAktion, BorderLayout.SOUTH);
        frame.add(panel5, BorderLayout.CENTER);

        //Startzustände


        for (int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                btnLager[i][j][1].setEnabled(false);
            }
        }

        for (int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                btnLager[i][j][0].setEnabled(false);
            }
        }


        //Button action
        btnNaechsterEintrag.addActionListener(this::nextEintrag);
        btnAuftragAblehnen.addActionListener(this::pressedAblehnen);
        btnAuftragBearbeiten.addActionListener(this::pressedBearbeiten);
        btnUmlagern.addActionListener(this::pressedUmlagern);
        btnVerschrotten.addActionListener(this::pressedVerschrotten);

        frame.setSize(1000, 1000);
        frame.setVisible(true);


    }

    private void comboBox(ActionEvent e) {

        if(e.getSource()==cmbbox){
            JComboBox cb = (JComboBox) e.getSource();
            ausgewaehltesFach = cb.getSelectedIndex();
            i = fach[ausgewaehltesFach];
            auftragsLabelRefresh();
        }
    }

    private void pressedVerschrotten(ActionEvent e) {
        btnBearbeitenIsPressed=false;
        btnUmlagernIsPressed=false;
        btnVerschrottenIsPressed = true;
        for (int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                btnLager[i][j][1].setEnabled(true);
            }
        }

        for (int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                btnLager[i][j][0].setEnabled(true);
            }
        }

        auftragsLabelRefresh();
    }

    private void pressedUmlagern(ActionEvent e) {
        btnUmlagernIsPressed = true;
        btnBearbeitenIsPressed = false;
        btnVerschrottenIsPressed=false;
        waehlequelle = true;
        for (int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                btnLager[i][j][1].setEnabled(true);
            }
        }

        for (int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                btnLager[i][j][0].setEnabled(true);
            }
        }

        auftragsLabelRefresh();
    }

    private void pressedBearbeiten(ActionEvent e) {
            btnAuftragAblehnen.setBorderPainted(true);
            btnAuftragAblehnen.setEnabled(true);
            btnAuftragBearbeiten.setEnabled(true);
            btnVerschrotten.setEnabled(true);
            btnUmlagern.setEnabled(true);
            btnBearbeitenIsPressed = true;
            btnNaechsterEintragIsPressed=false;

        auftragsLabelRefresh();

            for (int j = 2; j > -1; j--) {
                for (int i = 0; i < 4; i++) {
                    btnLager[i][j][1].setEnabled(true);
                }
            }

            for (int j = 2; j > -1; j--) {
                for (int i = 0; i < 4; i++) {
                    btnLager[i][j][0].setEnabled(true);
                }
            }



    }

    private void pressedAblehnen(ActionEvent e) {
        
        JOptionPane.showMessageDialog(null, "Als Vertragsstrafe wird die Belohnung vom Kontostand abgezogen", "Vertragsstrafe", JOptionPane.INFORMATION_MESSAGE);
        balance -= -l.get(i).getReward();
        fach[ausgewaehltesFach] = i = -1;
        auftragsLabelRefresh();

    }


    public void nextEintrag(ActionEvent e) {
        if (l == null) l = Product.loadProducts();
        btnNaechsterEintragIsPressed=true;
        fach[ausgewaehltesFach] = i = listeIndex;
        listeIndex = (listeIndex + 1) % l.size();
        auftragsLabelRefresh();
    }

    private void auftragsLabelRefresh() {
        boolean auswahlAktiv = !btnBearbeitenIsPressed && !btnUmlagernIsPressed && !btnVerschrottenIsPressed;
        if (l != null && i > -1) {
            lblProduktname.setText("Produktart: " + l.get(i).getName());
            lblAuftragsart.setText("Auftragsart: " + l.get(i).getType());
            lblAttribut1.setText("1.Eigenschaft: " + l.get(i).getValueA());
            lblAttribut2.setText("2.Eigenschaft: " + l.get(i).getValueB());
            lblWert.setText("Wert: " + l.get(i).getReward());
            btnAuftragBearbeiten.setEnabled(!btnBearbeitenIsPressed && !btnUmlagernIsPressed && !btnVerschrottenIsPressed);
            btnAuftragAblehnen.setEnabled(!btnBearbeitenIsPressed && !btnUmlagernIsPressed && !btnVerschrottenIsPressed);
            btnNaechsterEintrag.setEnabled(false);
        } else {
            lblProduktname.setText("Produktart: ");
            lblAuftragsart.setText("Auftragsart: ");
            lblAttribut1.setText("1.Eigenschaft: ");
            lblAttribut2.setText("2.Eigenschaft: ");
            lblWert.setText("Wert: ");
            btnAuftragBearbeiten.setEnabled(false);
            btnAuftragAblehnen.setEnabled(false);
            btnNaechsterEintrag.setEnabled(auswahlAktiv);
        }

        btnVerschrotten.setEnabled(auswahlAktiv);
        btnUmlagern.setEnabled(auswahlAktiv);
        cmbbox.setEnabled(auswahlAktiv);
        lblBelohnung.setText("Belohnung: " + balance);
    }

    public void ButtonPressed(ActionEvent e) {
        btnFlaecheIsPressed = true;
        var name = ((JButton) e.getSource()).getName();
        int index = Integer.parseInt(name);
        int x = index / 100;
        int y = (index / 10) - (x * 10);
        int z;
        if (x != 0) {
            z = (index % (100 * x)) - (y * 10);
        } else z = index - (y * 10);

        if (btnBearbeitenIsPressed) {
            aw.naechsterEintrag(i, x, y, z);
            if (Objects.equals(l.get(i).getType(), "Einlagerung")) {
                if (aw.einlagerungErfolgreich()) {
                    if (Objects.equals(l.get(i).getName(), "Holz")) {
                        if ((Objects.equals(l.get(i).getValueB(), "Balken"))) {
                            btnLager[x][y][1].setText(l.get(i).getName() + " " + l.get(i).getValueA() + " " + l.get(i).getValueB());
                            btnLager[x][y][0].setText(l.get(i).getName() + " " + l.get(i).getValueA() + " " + l.get(i).getValueB());
                        } else {
                            btnLager[x][y][z].setText(l.get(i).getName() + " " + l.get(i).getValueA() + " " + l.get(i).getValueB());
                        }
                    } else if (Objects.equals(l.get(i).getName(), "Papier")) {
                        btnLager[x][y][z].setText(l.get(i).getName() + " " + l.get(i).getValueA() + " " + l.get(i).getValueB());
                    } else if (Objects.equals(l.get(i).getName(), "Stein")) {
                        btnLager[x][y][z].setText(l.get(i).getName() + " " + l.get(i).getValueA() + " " + l.get(i).getValueB());
                    }

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][1].setEnabled(false);
                        }
                    }

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][0].setEnabled(false);
                        }
                    }


                    fach[ausgewaehltesFach] = i = -1;
                    auftragsLabelRefresh();
                    i++;
                } else {
                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][1].setEnabled(false);
                        }
                    }

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][0].setEnabled(false);
                        }
                    }
                    btnBearbeitenIsPressed = true;

                }
            } else if (Objects.equals(l.get(i).getType(), "Auslagerung")) {
                if (aw.auslagerungErfolgreich()) {
                    if (Objects.equals(l.get(i).getValueB(), "Balken")) {
                        btnLager[x][y][1].setText(" ");
                        btnLager[x][y][0].setText(" ");
                    } else btnLager[x][y][z].setText(" ");

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][1].setEnabled(false);
                        }
                    }

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][0].setEnabled(false);
                        }
                    }

                } else {
                    btnBearbeitenIsPressed = false;
                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][1].setEnabled(false);
                        }
                    }

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            btnLager[i][j][0].setEnabled(false);
                        }
                    }

                    btnBearbeitenIsPressed = true;
                    btnUmlagern.setEnabled(true);
                    btnVerschrotten.setEnabled(true);

                }
            }

            fach[ausgewaehltesFach] = i = -1;
            btnFlaecheIsPressed = false;
            btnBearbeitenIsPressed = false;
        } else if (btnVerschrottenIsPressed && btnFlaecheIsPressed) {
            try{
                aw.verschrotten(x, y, z);
                String[] infos = btnLager[x][y][z].getText().split(" ");
                String splitedName = infos[0];
                String attribut2 = infos[2];
                if (Objects.equals(splitedName, "Holz") && Objects.equals(attribut2, "Balken")) {
                    btnLager[x][y][0].setText(" ");
                    btnLager[x][y][1].setText(" ");
                    btnVerschrottenIsPressed = false;
                    btnFlaecheIsPressed = false;
                } else {
                    btnLager[x][y][z].setText(" ");
                    btnVerschrottenIsPressed = false;
                    btnFlaecheIsPressed = false;
                }
                lblBelohnung.setText("Belohnung: " + aw.getKontostand());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Wählen Sie keine leere Paletten aus.","Info: Palette",JOptionPane.INFORMATION_MESSAGE);
            }
            //Umlagern
        } else if (btnUmlagernIsPressed && waehlequelle) {
            vX = x;
            vY = y;
            vZ = z;
            waehlequelle = false;

        } else if (btnUmlagernIsPressed && !waehlequelle) {
            aw.umlagern(vX, vY, vZ, x, y, z);
            if (aw.umlagernLeerePalette()) {
                String[] Vinfos = btnLager[vX][vY][vZ].getText().split(" ");
                String Vattribut2 = Vinfos[2];
                String Vname = Vinfos[0];
                //Aktuell Papier, Stein oder Holz aber kein Balken
                if (Objects.equals(Vname, "Papier") || Objects.equals(Vname, "Stein") || (Objects.equals(Vname, "Holz") && !Objects.equals(Vattribut2, "Balken"))) {
                    var vorher = btnLager[vX][vY][vZ].getText();
                    btnLager[vX][vY][vZ].setText(" ");
                    btnLager[x][y][z].setText(vorher);

                }
                //Aktuell Balken
                else {
                    if (z == 0 && vZ == 0) {
                        var temp = btnLager[vX][vY][vZ].getText();
                        btnLager[vX][vY][vZ].setText(btnLager[x][y][z].getText());
                        btnLager[x][y][z].setText(temp);
                        btnLager[vX][vY][1].setText(" ");
                        btnLager[x][y][1].setText(temp);

                    } else if (z == 0 && vZ == 1) {
                        var vorher = btnLager[vX][vY][vZ].getText();
                        var aktuell = btnLager[x][y][z].getText();
                        btnLager[vX][vY][1].setText(aktuell);
                        btnLager[x][y][z].setText(vorher);
                        btnLager[vX][vY][0].setText(" ");
                        btnLager[x][y][1].setText(aktuell);
                    } else if (z == 1 && vZ == 0) {
                        var temp = btnLager[vX][vY][vZ].getText();
                        var aktuell= btnLager[x][y][z].getText();
                        btnLager[vX][vY][vZ].setText(aktuell);
                        btnLager[x][y][z].setText(temp);
                        btnLager[vX][vY][1].setText(" ");
                        btnLager[x][y][0].setText(temp);

                    } else if (z == 1 && vZ == 1) {
                        var temp = btnLager[vX][vY][vZ].getText();
                        var aktuell = btnLager[x][y][z].getText();
                        btnLager[vX][vY][1].setText(aktuell);
                        btnLager[x][y][z].setText(temp);
                        btnLager[vX][vY][0].setText(" ");
                        btnLager[x][y][0].setText(temp);

                    }
                }
            }

            else if (aw.umlagernErfolgreich()) {
                String[] infos = btnLager[x][y][z].getText().split(" ");
                String attribut2 = infos[2];
                String[] Vinfos = btnLager[vX][vY][vZ].getText().split(" ");
                String Vattribut2 = Vinfos[2];
                //Wenn ausgewähltes Produkt keine Balken
                if (!Objects.equals(Vattribut2, "Balken")) {
                    //Wenn Zielprodukt kein Balken
                    if (!Objects.equals(attribut2, "Balken")) {
                        var temp = btnLager[vX][vY][vZ].getText();
                        btnLager[vX][vY][vZ].setText(btnLager[x][y][z].getText());
                        btnLager[x][y][z].setText(temp);
                    }
                    //Wenn Zielprodukt Balken
                    else {
                        if (z == 0 && vZ == 0) {
                            var temp = btnLager[vX][vY][vZ].getText();
                            btnLager[vX][vY][0].setText(btnLager[x][y][z].getText());
                            btnLager[x][y][z].setText(temp);
                            btnLager[vX][vY][1].setText(btnLager[vX][vY][vZ].getText());
                            btnLager[x][y][1].setText(" ");

                        } else if (z == 0 && vZ == 1) {
                            var vorher = btnLager[vX][vY][vZ].getText();
                            var aktuell = btnLager[x][y][z].getText();
                            btnLager[vX][vY][1].setText(aktuell);
                            btnLager[x][y][z].setText(vorher);
                            btnLager[vX][vY][0].setText(aktuell);
                            btnLager[x][y][1].setText(" ");
                        } else if (z == 1 && vZ == 0) {
                            var temp = btnLager[vX][vY][vZ].getText();
                            btnLager[vX][vY][0].setText(btnLager[x][y][z].getText());
                            btnLager[x][y][z].setText(temp);
                            btnLager[vX][vY][1].setText(btnLager[vX][vY][vZ].getText());
                            btnLager[x][y][0].setText(" ");

                        } else if (z == 1 && vZ == 1) {
                            var temp = btnLager[vX][vY][vZ].getText();
                            btnLager[vX][vY][1].setText(btnLager[x][y][z].getText());
                            btnLager[x][y][z].setText(temp);
                            btnLager[vX][vY][0].setText(btnLager[vX][vY][vZ].getText());
                            btnLager[x][y][0].setText(" ");

                        }
                    }
                }
                //Wenn ausgewähltes Produkt Balken
                else {
                    //Wenn Zielprodukt keine Balken [Muss überarbeitet werden]
                    if (!Objects.equals(attribut2, "Balken")) {
                        if (z == 0 && vZ == 0) {
                            var vorher = btnLager[vX][vY][vZ].getText();
                            var aktuell = btnLager[x][y][z].getText();
                            btnLager[vX][vY][0].setText(aktuell);
                            btnLager[vX][vY][1].setText(" ");
                            btnLager[x][y][0].setText(vorher);
                            btnLager[x][y][1].setText(vorher);


                        } else if (z == 0 && vZ == 1) {
                            var vorher = btnLager[vX][vY][vZ].getText();
                            var aktuell = btnLager[x][y][z].getText();
                            btnLager[vX][vY][0].setText(" ");
                            btnLager[vX][vY][1].setText(aktuell);
                            btnLager[x][y][0].setText(vorher);
                            btnLager[x][y][1].setText(vorher);
                        } else if (z == 1 && vZ == 0) {
                            var vorher = btnLager[vX][vY][vZ].getText();
                            var aktuell = btnLager[x][y][z].getText();
                            btnLager[x][y][0].setText(vorher);
                            btnLager[x][y][1].setText(vorher);
                            btnLager[vX][vY][0].setText(aktuell);
                            btnLager[vX][vY][1].setText(" ");

                        } else if (z == 1 && vZ == 1) {
                            var vorher = btnLager[vX][vY][vZ].getText();
                            var aktuell = btnLager[x][y][z].getText();
                            btnLager[vX][vY][1].setText(aktuell);
                            btnLager[vX][vY][0].setText(" ");
                            btnLager[x][y][0].setText(vorher);
                            btnLager[x][y][1].setText(vorher);

                        }

                    }
                    //Wenn Zielprodukt Balken
                    else {
                        var vorher = btnLager[vX][vY][vZ].getText();
                        var aktuell = btnLager[x][y][z].getText();
                        btnLager[vX][vY][1].setText(aktuell);
                        btnLager[vX][vY][0].setText(aktuell);
                        btnLager[x][y][0].setText(vorher);
                        btnLager[x][y][1].setText(vorher);
                    }
                }

            }
            btnUmlagernIsPressed = false;
        }

        auftragsLabelRefresh();
    }

    private final Lager matrix = new Lager();
    private final List<Product> jobs = Product.loadProducts();
    private boolean einlagerungErfolgreich;
    private boolean auslagerungErfolgreich;
    private boolean umlagernErfolgreich;
    private boolean umlagernLeerePalette;



    public void naechsterEintrag(int i, int x, int y, int z) {

        if (Objects.equals(jobs.get(i).getType(), "Einlagerung")) {

            if (!matrix.isEmpty(x, y, z)) {
                JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da die Palette nicht leer ist", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                einlagerungErfolgreich = false;
            } else {
                if (Objects.equals(jobs.get(i).getName(), "Holz")) {
                    einlagernHolz(i, x, y, z);
                } else if (Objects.equals(jobs.get(i).getName(), "Stein")) {
                    einlagernStein(i, x, y, z);
                } else if (Objects.equals(jobs.get(i).getName(), "Papier")) {
                    einlagernPapier(i, x, y, z);
                }


            }


        } else if (Objects.equals(jobs.get(i).getType(), "Auslagerung")) {

            if (matrix.isEmpty(x, y, z)) {
                JOptionPane.showMessageDialog(null, "Auslagerung nicht möglich, da die Palette leer ist", "Fehler: Auslagerung", JOptionPane.ERROR_MESSAGE);
                auslagerungErfolgreich = false;
            } else {
                if (!matrix.foundProduct(jobs.get(i), x, y, z)) {
                    JOptionPane.showMessageDialog(null, "Auslagerung nicht möglich, da das Produkt nicht gefunden wurde", "Fehler: Auslagerung", JOptionPane.ERROR_MESSAGE);
                    auslagerungErfolgreich = false;
                } else {
                    if (Objects.equals(matrix.getSearchedName(), "Holz")) {
                        auslagernHolz(i, x, y, z);
                    } else {
                        auslagernWiePapierStein(i, x, y, z);
                    }
                }
            }
        }
    }W

    public void verschrotten(int x, int y, int z) {
        if (matrix.isEmpty(x, y, z)) {
            JOptionPane.showMessageDialog(null, "Prdoukt kann nicht verschrottet werden, da die Palette leer ist.", "Fehler: Verschrotten", JOptionPane.ERROR_MESSAGE);
        } else {
            if (Objects.equals(matrix.getProduktName(x, y, z), "Holz") && Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Balken")) {
                matrix.insertProduct(x, y, 1, null);
                matrix.insertProduct(x, y, 0, null);
            } else {
                matrix.insertProduct(x, y, z, null);
            }
            JOptionPane.showMessageDialog(null, "Das ausgewählte Produkt wurde für 300 Geldeinheiten verschrottet.", "Info: Verschrotten", JOptionPane.INFORMATION_MESSAGE);
            aendereKontostand(-300, "Verschrottet");
        }

    }

    public void loeschen(int x, int y, int z) {
        if (Objects.equals(matrix.getProduktName(x, y, z), "Holz") && Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Balken")) {
            matrix.insertProduct(x, y, 1, null);
            matrix.insertProduct(x, y, 0, null);
        } else {
            matrix.insertProduct(x, y, z, null);
        }
    }


    //Einlagern/Auslagern der Produkte
    public void einlagernHolz(int i, int x, int y, int z) {
        if (Objects.equals(jobs.get(i).getValueB(), "Balken")) {
            if ((z == 0 && !matrix.isEmpty(x, y, 1) || (z == 1 && !matrix.isEmpty(x, y, 0)))) {
                System.out.println("Einlagerung nicht möglich ");
                einlagerungErfolgreich = false;
                JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da der Platz nicht ausreicht", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);

            } else {
                matrix.insertProduct(x, y, 0, jobs.get(i));
                matrix.insertProduct(x, y, 1, jobs.get(i));
                System.out.println("Einlagerung erfolgreich");
                einlagerungErfolgreich = true;
                auslagerungErfolgreich = false;
                aendereKontostand(jobs.get(i).getReward(), "Eingelagert");
            }
        } else {
            matrix.insertProduct(x, y, z, jobs.get(i));
            System.out.println("Einlagerung erfolgreich");
            einlagerungErfolgreich = true;
            auslagerungErfolgreich = false;
            aendereKontostand(jobs.get(i).getReward(), "Eingelagert");
        }

    }

    public void einlagernStein(int i, int x, int y, int z) {
        if (Objects.equals(jobs.get(i).getValueB(), "Schwer")) {
            if (!matrix.isEmpty(x, 0, z) || (y != 0)) {
                if (!matrix.isEmpty(x, 0, z)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da der Stein zu schwer ist!", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;

                } else if ((y != 0)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da schwere Steine nur für die unteren Paletten geeignet sind!", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                }

            } else {
                matrix.insertProduct(x, y, z, jobs.get(i));
                System.out.println("Einlagerung erfolgreich");
                einlagerungErfolgreich = true;
                auslagerungErfolgreich = false;
                aendereKontostand(jobs.get(i).getReward(), "Eingelagert");
            }

        } else if (Objects.equals(jobs.get(i).getValueB(), "Mittel")) {
            if ((y == 0 && !matrix.isEmpty(x, y, z)) || y == 1 && !matrix.isEmpty(x, y, z) || y == 2) {
                if (y == 0 && !matrix.isEmpty(x, y, z)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da die unteren Paletten besetzt sind", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                } else if (y == 1 && !matrix.isEmpty(x, y, z)) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da die mittleren Paletten besetzt sind", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                } else if (y == 2) {
                    JOptionPane.showMessageDialog(null, "Einlagerung nicht möglich, da mittelschwere Paletten nicht oben platziert werden dürfen", "Fehler: Einlagerung", JOptionPane.ERROR_MESSAGE);
                    einlagerungErfolgreich = false;
                }

            } else {
                matrix.insertProduct(x, y, z, jobs.get(i));
                System.out.println("Einlagerung erfolgreich");
                einlagerungErfolgreich = true;
                auslagerungErfolgreich = false;
                aendereKontostand(jobs.get(i).getReward(), "Eingelagert");
            }

        } else if (Objects.equals(jobs.get(i).getValueB(), "Leicht")) {
            matrix.insertProduct(x, y, z, jobs.get(i));
            System.out.println("Einlagerung erfolgreich");
            einlagerungErfolgreich = true;
            auslagerungErfolgreich = false;
            balance += jobs.get(i).getReward();
        }
    }

    public void einlagernPapier(int i, int x, int y, int z) {
        matrix.insertProduct(x, y, z, jobs.get(i));
        System.out.println("Einlagerung erfolgreich");
        einlagerungErfolgreich = true;
        auslagerungErfolgreich = false;
        aendereKontostand(jobs.get(i).getReward(), "Eingelagert");
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void auslagernHolz(int i, int x, int y, int z) {

        if (Objects.equals(jobs.get(i).getValueB(), "Balken")) {
            if (z == 0) {
                matrix.insertProduct(x, y, 1, null);
                matrix.insertProduct(x, y, 0, null);
                aendereKontostand(jobs.get(i).getReward(), "Ausgelagert");
                System.out.println("Auslagern erfolgreich");
                auslagerungErfolgreich = true;
                einlagerungErfolgreich = false;
            } else if (z == 1) {
                matrix.insertProduct(x, y, 1, null);
                matrix.insertProduct(x, y, 0, null);
                aendereKontostand(jobs.get(i).getReward(), "Ausgelagert");
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
            if (matrix.isEmpty(x, y, 0)) {
                matrix.insertProduct(x, y, z, null);
                aendereKontostand(jobs.get(i).getReward(), "Ausgelagert");
                System.out.println("Auslagern erfolgreich");
                auslagerungErfolgreich = true;
                einlagerungErfolgreich = false;
            } else {
                JOptionPane.showMessageDialog(null, "Auslagerung nicht möglich, da ein anderes Produkt das aktuelle Produkt blockiert", "Fehler: Auslagerung", JOptionPane.ERROR_MESSAGE);
                auslagerungErfolgreich = false;
            }

        } else {
            matrix.insertProduct(x, y, z, null);
            aendereKontostand(jobs.get(i).getReward(), "Ausgelagert");
            System.out.println("Auslagern erfolgreich");
            auslagerungErfolgreich = true;
            einlagerungErfolgreich = false;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void umlagern(int x, int y, int z, int zX, int zY, int zZ) { //Muss gemacht werden
        //Produkt auswählen

        if (matrix.isEmpty(x, y, z)) {
            JOptionPane.showMessageDialog(null, "Die ausgewählte Palette ist leer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
            umlagernErfolgreich = false;

        } else if (!matrix.isEmpty(zX, zY, zZ)) {
            umlagernLeerePalette=false;
            //Aktueles Produkt Papier
            if (Objects.equals(matrix.getProdukt(x, y, z).getName(), "Papier")) {
                aktProduktPapier(x, y, z, zX, zY, zZ);
            }


            //Aktuelles Produkt Holz
            else if (Objects.equals(matrix.getProdukt(x, y, z).getName(), "Holz")) {
                aktProduktHolz(x, y, z, zX, zY, zZ);

            }


            //Aktuelles Produkt Stein
            else if (Objects.equals(matrix.getProdukt(x, y, z).getName(), "Stein")) {
                aktProduktStein(x, y, z, zX, zY, zZ);

            }
        }//Ziel Leer
        else if (matrix.isEmpty(zX, zY, zZ)) {
            //Aktuelles Produkt Papier
            if (Objects.equals(matrix.getProdukt(x, y, z).getName(), "Papier")) {
                EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
            }
            //Aktuelles Produkt Stein
            else if(Objects.equals(matrix.getProdukt(x, y, z).getName(), "Stein")){
                if(Objects.equals(matrix.getProdukt(x,y,z).getValueB(),"Schwer")){
                    if(zY!=0){
                        //Stein zu schwer
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    }else{
                        EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                    }
                }else if(Objects.equals(matrix.getProdukt(x,y,z).getValueB(),"Mittel")){
                    if(zY==2){
                        //Stein zu schwer
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    }else{
                        EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                    }
                }else if(Objects.equals(matrix.getProdukt(x,y,z).getValueB(),"Leicht")){
                    EinzelproduktZuLeer(x,y,z,zX,zY,zZ);
                }
            }
            //Aktuelles Produkt Holz
            else if(Objects.equals(matrix.getProdukt(x, y, z).getName(), "Holz")){
                //Aktuelles Produkt kein Balken
                if(!Objects.equals(matrix.getProdukt(x,y,z).getValueB(),"Balken")){
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
        if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Papier")) {
            zielproduktPapier(x, y, z, zX, zY, zZ);
        }

        //Zielprodukt Stein
        else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Stein")) {
            if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                if (y != 0) {
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielProduktStein(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                if (y == 2) {
                    //Stein zu schwer
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;

                } else {
                    zielProduktStein(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Leicht")) {
                zielProduktStein(x, y, z, zX, zY, zZ);
            }
        }

        //Zielprodukt Holz
        else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Holz")) {
            zielProduktHolz(x, y, z, zX, zY, zZ);
        }



    }


    public void aktProduktHolz(int x, int y, int z, int zX, int zY, int zZ) {
        if (!Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Balken")) {
            //Ziel Papier
            if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Papier")) {
                zielproduktPapier(x, y, z, zX, zY, zZ);
            }
            //Ziel Stein
            else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Stein")) {
                if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                    if (y != 0) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                    if (y == 2) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }

                } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Leicht")) {
                    zielProduktStein(x, y, z, zX, zY, zZ);
                }

            }
            //Ziel Holz
            else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Holz")) {
                zielProduktHolz(x, y, z, zX, zY, zZ);
            }
        } else {
            //Ziel Papier
            if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Papier")) {
                balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
            }
            //Ziel Stein
            else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Stein")) {
                if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                    if (y != 0) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
                    }
                } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
                    if (y == 2) {
                        JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
                    }

                } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Leicht")) {
                    balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
                }

            }

            //Ziel Holz
            else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Holz")) {
                balkenZuEinfachenProdukt(x, y, z, zX, zY, zZ);
            }
        }
    }

    public void aktProduktStein(int x, int y, int z, int zX, int zY, int zZ) {
        //Zielprodukt Papier
        if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Papier")) {
            if (Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Schwer")) {
                if (zY != 0) {
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielproduktPapier(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Mittel")) {
                if (zY == 2) {
                    JOptionPane.showMessageDialog(null, "Stein zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielproduktPapier(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Leicht")) {
                zielproduktPapier(x, y, z, zX, zY, zZ);
            }
        }


        //Zielprodukt Stein
        else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Stein")) {
            if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Stein")) {
                if (Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Schwer")) {
                    if (zY != 0) {
                        //Der Stein ist viel zu schwer für eine höhere Ebene
                        JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                } else if (Objects.equals(matrix.getProdukt(x, y, z).getValueB(), "Mittel")) {
                    if (zY == 2) {
                        //Der Stein ist viel zu schwer für eine höhere Ebene
                        JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        zielProduktStein(x, y, z, zX, zY, zZ);
                    }
                } else {
                    if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                        if (y != 0) {
                            //Fehler
                            JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                            umlagernErfolgreich = false;
                        } else {
                            zielProduktStein(x, y, z, zX, zY, zZ);
                        }
                    } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
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
        else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getName(), "Holz")) {
            if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Schwer")) {
                if (y != 0) {
                    //Fehler
                    JOptionPane.showMessageDialog(null, "Produkt zu schwer", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    zielProduktHolz(x, y, z, zX, zY, zZ);
                }
            } else if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Mittel")) {
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
            if (!matrix.isEmpty(zX, zY, 1)) {
                JOptionPane.showMessageDialog(null, "Hinter dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 1, zX, zY, 1);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (!matrix.isEmpty(zX, zY, 0)) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 1, zX, zY, 0);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 1 && zZ == 0) {
            if (!matrix.isEmpty(zX, zY, 1)) {
                JOptionPane.showMessageDialog(null, "Hinter dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 0, zX, zY, 1);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 1 && zZ == 1) {
            if (!matrix.isEmpty(zX, zY, 0)) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt befindet sich ein anderes Produkt", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 0, zX, zY, 0);
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        }
    }

    public void zielproduktPapier(int x, int y, int z, int zX, int zY, int zZ) {
        if (z == 1 && zZ == 1) {
            if (!matrix.isEmpty(zX, zY, 0) || !matrix.isEmpty(x, y, 0)) {
                //Vor dem Zielprodukt ist ein anderes Produkt
                JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (x == zX && y == zY) {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!matrix.isEmpty(zX, zY, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }

        } else if (z == 1 && zZ == 0) {
            if (x == zX && y == zY) {
                //Tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            } else {
                if (!matrix.isEmpty(x, y, 0)) {
                    //Aktuelle produkt wird blockiert
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    aendereKontostand(-100, "Umgelagert");
                }
            }
        } else if (z == 0 && zZ == 0) {
            //tausche
            matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
            System.out.println("Erfolgreich umgelagert");
            umlagernErfolgreich = true;
            aendereKontostand(-100, "Umgelagert");
        }
    }

    public void zielProduktStein(int x, int y, int z, int zX, int zY, int zZ) {
        if (z == 1 && zZ == 1) {
            if (!matrix.isEmpty(zX, zY, 0) || !matrix.isEmpty(x, y, 0)) {
                //Vor dem Zielprodukt ist ein anderes Produkt
                JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernErfolgreich = false;
            } else {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                aendereKontostand(-100, "Umgelagert");
            }
        } else if (z == 0 && zZ == 1) {
            if (x == zX && y == zY) {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                balance -= 100;
            } else {
                if (!matrix.isEmpty(zX, zY, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                }
            }

        } else if (z == 1 && zZ == 0) {
            if (x == zX && y == zY) {
                //Tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                balance -= 100;
            } else {
                if (!matrix.isEmpty(x, y, 0)) {
                    //Aktuelle produkt wird blockiert
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                }
            }
        } else if (z == 0 && zZ == 0) {
            //tausche
            matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
            System.out.println("Erfolgreich umgelagert");
            umlagernErfolgreich = true;
            balance -= 100;
        }
    }

    public void zielProduktHolz(int x, int y, int z, int zX, int zY, int zZ) {
        if (Objects.equals(matrix.getProdukt(zX, zY, zZ).getValueB(), "Balken")) {
            if (z == 0 && zZ == 0) {
                if (!matrix.isEmpty(x, y, 1)) {
                    JOptionPane.showMessageDialog(null, "Hinter dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    matrix.tauscheProdukt(x, y, 1, zX, zY, 1);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                }

            } else if (z == 0 && zZ == 1) {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 1, zX, zY, 0);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                balance -= 100;

            } else if (z == 1 && zZ == 0) {
                if (!matrix.isEmpty(x, y, 0)) {
                    JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    matrix.tauscheProdukt(x, y, 0, zX, zY, 1);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                }


            } else if (z == 1 && zZ == 1) {
                if (!matrix.isEmpty(x, y, 0)) {
                    JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    matrix.tauscheProdukt(x, y, 0, zX, zY, 0);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                }

            }
        } else {
            if (z == 1 && zZ == 1) {
                if (!matrix.isEmpty(zX, zY, 0) || !matrix.isEmpty(x, y, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernErfolgreich = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                }
            } else if (z == 0 && zZ == 1) {
                if (x == zX && y == zY) {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                } else {
                    if (!matrix.isEmpty(zX, zY, 0)) {
                        //Vor dem Zielprodukt ist ein anderes Produkt
                        JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        //tausche
                        matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                        System.out.println("Erfolgreich umgelagert");
                        umlagernErfolgreich = true;
                        balance -= 100;
                    }
                }

            } else if (z == 1 && zZ == 0) {
                if (x == zX && y == zY) {
                    //Tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernErfolgreich = true;
                    balance -= 100;
                } else {
                    if (!matrix.isEmpty(zX, zY, 1)) {
                        //Aktuelle produkt wird blockiert
                        JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                        umlagernErfolgreich = false;
                    } else {
                        //tausche
                        matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                        System.out.println("Erfolgreich umgelagert");
                        umlagernErfolgreich = true;
                        balance -= 100;
                    }
                }
            } else if (z == 0 && zZ == 0) {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernErfolgreich = true;
                balance -= 100;
            }
        }
    }

    public void EinzelproduktZuLeer(int x, int y, int z, int zX, int zY, int zZ){
        umlagernErfolgreich=false;
        if (z == 1 && zZ == 1) {
            if (!matrix.isEmpty(zX, zY, 0) || !matrix.isEmpty(x, y, 0)) {
                //Vor dem Zielprodukt ist ein anderes Produkt
                JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            }
        } else if (z == 0 && zZ == 1) {
            if (x == zX && y == zY) {
                //tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            } else {
                if (!matrix.isEmpty(zX, zY, 0)) {
                    //Vor dem Zielprodukt ist ein anderes Produkt
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernLeerePalette = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernLeerePalette = true;
                    balance -= 100;
                }
            }

        } else if (z == 1 && zZ == 0) {
            if (x == zX && y == zY) {
                //Tausche
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            } else {
                if (!matrix.isEmpty(x, y, 0)) {
                    //Aktuelle produkt wird blockiert
                    JOptionPane.showMessageDialog(null, "Das Produkt wird von einem anderen Produkt blockiert", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                    umlagernLeerePalette = false;
                } else {
                    //tausche
                    matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                    System.out.println("Erfolgreich umgelagert");
                    umlagernLeerePalette = true;
                    balance -= 100;
                }
            }
        } else if (z == 0 && zZ == 0) {
            //tausche
            matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
            System.out.println("Erfolgreich umgelagert");
            umlagernLeerePalette = true;
            balance -= 100;
        }
    }

    public void BalkenZuLeer(int x, int y, int z, int zX,int zY, int zZ){
        umlagernErfolgreich=false;
        if (z == 0 && zZ == 0) {
            if (!matrix.isEmpty(zX, zY, 1)||(!matrix.isEmpty(zX,zY,0))) {
                JOptionPane.showMessageDialog(null, "Ein Anderes Produkt blockiert die Umlagerung und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 1, zX, zY, 1);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            }

        } else if (z == 0 && zZ == 1) {
            if(!matrix.isEmpty(zX, zY, 1)||(!matrix.isEmpty(zX,zY,0))){
                JOptionPane.showMessageDialog(null, "Ein Anderes Produkt blockiert die Umlagerung und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);

            }else{
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 1, zX, zY, 0);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            }


        } else if (z == 1 && zZ == 0) {
            if (!matrix.isEmpty(zX, zY, 1)||(!matrix.isEmpty(zX,zY,0))) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 0, zX, zY, 1);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            }


        } else if (z == 1 && zZ == 1) {
            if (!matrix.isEmpty(zX, zY, 1)||(!matrix.isEmpty(zX,zY,0))) {
                JOptionPane.showMessageDialog(null, "Vor dem Zielprodukt ist ein anderes Produkt und Balken braucht zwei Paletten platz", "Fehler: Umlagerung", JOptionPane.ERROR_MESSAGE);
                umlagernLeerePalette = false;
            } else {
                matrix.tauscheProdukt(x, y, z, zX, zY, zZ);
                matrix.tauscheProdukt(x, y, 0, zX, zY, 0);
                System.out.println("Erfolgreich umgelagert");
                umlagernLeerePalette = true;
                balance -= 100;
            }

        }
    }


}









