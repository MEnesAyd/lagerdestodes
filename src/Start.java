

import javax.swing.*;

public class Start extends JFrame {

    public static void main(String[] args) {
        var window = new JFrame("Die Lager");
        window.setContentPane(new lagerswingbing().getContentPanel());
        window.setSize(800, 600);
        window.setResizable(false);
        window.setVisible(true);
    }


}
