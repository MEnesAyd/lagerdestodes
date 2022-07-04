

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GUI extends Component {
    protected final JButton[][][] btnLager;
    private final JButton btnNaechsterEintrag;
    private final JButton btnUmlagern;
    private final JButton btnVerschrotten;
    private final JButton btnBilanz;
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
    CSVListe p = new CSVListe();
    List<Produkte> l;
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
        btnBilanz = new JButton("Bilanz");
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
        belohnung.add(btnBilanz);

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
        btnBilanz.setBackground(Color.gray);
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
        btnBilanz.addActionListener(this::oeffneBilanz);

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

    private  void oeffneBilanz(ActionEvent e) {
        BilanzGUI gui = new BilanzGUI(aw.getKontoverlauf());
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 400);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setContentPane(gui.getContent());
        gui.inhalt();

        dialog.setVisible(true);
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
        aw.aendereKontostand(-l.get(i).getBelohnung(), "Auftrag abgelehnt");
        fach[ausgewaehltesFach] = i = -1;
        auftragsLabelRefresh();

    }


    public void nextEintrag(ActionEvent e) {
        l = p.getList();
        btnNaechsterEintragIsPressed=true;
        fach[ausgewaehltesFach] = i = listeIndex;
        listeIndex = (listeIndex + 1) % l.size();
        auftragsLabelRefresh();
    }

    private void auftragsLabelRefresh() {
        boolean auswahlAktiv = !btnBearbeitenIsPressed && !btnUmlagernIsPressed && !btnVerschrottenIsPressed;
        if (l != null && i > -1) {
            lblProduktname.setText("Produktart: " + l.get(i).getName());
            lblAuftragsart.setText("Auftragsart: " + l.get(i).getAuftragsart());
            lblAttribut1.setText("1.Eigenschaft: " + l.get(i).getAttribute1());
            lblAttribut2.setText("2.Eigenschaft: " + l.get(i).getAttribute2());
            lblWert.setText("Wert: " + l.get(i).getBelohnung());
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
        lblBelohnung.setText("Belohnung: " + aw.getKontostand());
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
            if (Objects.equals(l.get(i).getAuftragsart(), "Einlagerung")) {
                if (aw.einlagerungErfolgreich()) {
                    if (Objects.equals(l.get(i).getName(), "Holz")) {
                        if ((Objects.equals(l.get(i).getAttribute2(), "Balken"))) {
                            btnLager[x][y][1].setText(l.get(i).getName() + " " + l.get(i).getAttribute1() + " " + l.get(i).getAttribute2());
                            btnLager[x][y][0].setText(l.get(i).getName() + " " + l.get(i).getAttribute1() + " " + l.get(i).getAttribute2());
                        } else {
                            btnLager[x][y][z].setText(l.get(i).getName() + " " + l.get(i).getAttribute1() + " " + l.get(i).getAttribute2());
                        }
                    } else if (Objects.equals(l.get(i).getName(), "Papier")) {
                        btnLager[x][y][z].setText(l.get(i).getName() + " " + l.get(i).getAttribute1() + " " + l.get(i).getAttribute2());
                    } else if (Objects.equals(l.get(i).getName(), "Stein")) {
                        btnLager[x][y][z].setText(l.get(i).getName() + " " + l.get(i).getAttribute1() + " " + l.get(i).getAttribute2());
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
            } else if (Objects.equals(l.get(i).getAuftragsart(), "Auslagerung")) {
                if (aw.auslagerungErfolgreich()) {
                    if (Objects.equals(l.get(i).getAttribute2(), "Balken")) {
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

}









