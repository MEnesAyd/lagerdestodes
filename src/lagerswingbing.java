import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class lagerswingbing {
    private JButton btn43B;
    private JPanel cPanel;
    private JButton btn41A;
    private JButton btn43A;
    private JButton btn42B;
    private JButton btn41B;
    private JButton btn42A;
    private JButton btn32A;
    private JButton btn22A;
    private JButton btn12A;
    private JButton storeButton;
    private JButton outsourceButton;
    private JButton restoreButton;
    private JButton nextTaskButton;
    private JButton DESTROYButton;
    private JButton btn11A;
    private JButton btn11B;
    private JButton btn12B;
    private JButton btn13A;
    private JButton btn13B;
    private JButton btn21A;
    private JButton btn21B;
    private JButton btn22B;
    private JButton btn23A;
    private JButton btn23B;
    private JButton btn31A;
    private JButton btn31B;
    private JButton btn32B;
    private JButton btn33A;
    private JButton btn33B;
    private JLabel lblType;
    private JLabel lblAttrA;
    private JLabel lblAttrB;
    private JLabel lblVal;
    private JLabel lblTask;
    private JComboBox slotSelect;


    //Application data
    private Product[][][] storageMatrix;
    private JButton[][][] buttonMatrix;
    private List<Product> productList;
    private int[] productListSlots;
    private int nextProductInList = -1;
    private ActionType actionType = ActionType.NONE;

    //Constructor
    public lagerswingbing() {
        //Setup method handlers
        nextTaskButton.addActionListener(this::nextTask);
        slotSelect.addActionListener(this::changeSlot);
        storeButton.addActionListener(this::storeButtonPressed);
        outsourceButton.addActionListener(this::outsourceButtonPressed);
        restoreButton.addActionListener(this::restoreButtonPressed);
        DESTROYButton.addActionListener(this::destroyButtonPressed);

        //Generate button matrix
        buttonMatrix = new JButton[][][]{ new JButton[][] { new JButton[] {btn11A, btn11B}, new JButton[] {btn12A, btn12B}, new JButton[] {btn13A, btn13B} },
                                          new JButton[][] { new JButton[] {btn21A, btn21B}, new JButton[] {btn22A, btn22B}, new JButton[] {btn23A, btn23B} },
                                          new JButton[][] { new JButton[] {btn31A, btn31B}, new JButton[] {btn32A, btn32B}, new JButton[] {btn33A, btn33B} },
                                          new JButton[][] { new JButton[] {btn41A, btn41B}, new JButton[] {btn42A, btn42B}, new JButton[] {btn43A, btn43B} }};

        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 2; z++)
                {
                    buttonMatrix[x][y][z].addActionListener(this::matrixButtonPressed);
                    buttonMatrix[x][y][z].setName("btn" + (x + 1) + (y + 1) + (z == 0 ? "A" : "B"));
                }


        storageMatrix = new Product[4][3][2];
        productList = Product.loadProducts();
        productListSlots = new int[] {-1,-1,-1};
        refreshGUI();
    }

    private void nextTask(ActionEvent e) {
        productListSlots[getSlot()] = nextProductInList = (nextProductInList + 1) % productList.size();
        refreshGUI();
    }

    private void changeSlot(ActionEvent e) { refreshGUI();}

    private void matrixButtonPressed(ActionEvent e) {
        var coord = getCoordinateFromButton((JButton)e.getSource());
        switch(actionType) {
            case STORE:
                if (storageMatrix[coord.x][coord.y][coord.z] != null) break;
                storageMatrix[coord.x][coord.y][coord.z] = productList.get(productListSlots[getSlot()]);
                productListSlots[getSlot()] = -1;
                actionType = ActionType.NONE;
                break;
            case DESTROY:
                if (storageMatrix[coord.x][coord.y][coord.z] == null) break;
                storageMatrix[coord.x][coord.y][coord.z] = null;
                actionType = ActionType.NONE;
        }

        refreshGUI();
    }

    private void storeButtonPressed(ActionEvent e) {
        int i;
        if ((i = productListSlots[getSlot()]) < 0 || !productList.get(i).getType().equals("input") || actionType != ActionType.NONE) return;
        actionType = ActionType.STORE;
        refreshGUI();
    }

    private void outsourceButtonPressed(ActionEvent e) {
        int i;
        if ((i = productListSlots[getSlot()]) < 0 || !productList.get(i).getType().equals("output") || actionType != ActionType.NONE) return;
        actionType = ActionType.OUTSOURCE;
        refreshGUI();
    }


    private void restoreButtonPressed(ActionEvent e) {
        if (actionType == ActionType.NONE) actionType = ActionType.RESTORE_SRC;
        refreshGUI();
    }

    private void destroyButtonPressed(ActionEvent e) {
        if (actionType == ActionType.NONE) actionType = ActionType.DESTROY;
        refreshGUI();
    }

    private void refreshGUI() {
        var currentIndex = productListSlots[getSlot()];
        var prod = currentIndex < 0 ? null : productList.get(currentIndex);
        var isEmpty = getIsEmpty();
        var active = actionType == ActionType.NONE;

        //Refresh buttons
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 2; z++)
                {
                    var item = storageMatrix[x][y][z];
                    buttonMatrix[x][y][z].setText(item != null ? item.getMaterial() + ", " + item.getValueA() + ", " + item.getValueB() : " ");
                }

        //Enable/Disable buttons
        slotSelect.setEnabled(active);
        nextTaskButton.setEnabled(active);
        DESTROYButton.setEnabled(active && !isEmpty);
        restoreButton.setEnabled(active && !isEmpty);

        if (currentIndex < 0) {
            storeButton.setEnabled(false);
            outsourceButton.setEnabled(false);
        } else {
            storeButton.setEnabled(prod.getType().equals("input") && active);
            outsourceButton.setEnabled(prod.getType().equals("output") && active);
        }



        lblType.setText(prod == null ? "" : prod.getMaterial());
        lblAttrA.setText(prod == null ? "" : prod.getValueA());
        lblAttrB.setText(prod == null ? "" : prod.getValueB());
        lblVal.setText(prod == null ? "" : prod.getReward() + "€");
        lblTask.setText(prod == null ? "" : prod.getType());
    }

    //Getter
    public JPanel getContentPanel() { return cPanel; }

    public Coordinate getCoordinateFromButton(JButton b) {
        var txt = b.getName();
        var lol = Character.toString(txt.charAt(3));
        return new Coordinate(Integer.parseInt(Character.toString(txt.charAt(3))) - 1, Integer.parseInt(Character.toString(txt.charAt(4))) - 1, txt.charAt(5) == 'A' ? 0 : 1);
    }

    public boolean getIsEmpty() {
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 2; z++)
                    if (storageMatrix[x][y][z] != null) return false;
        return true;
    }
    private int getSlot() { return slotSelect.getSelectedIndex(); }

}
