import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

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
    private JLabel lblBalance;


    //Application data
    private Product[][][] storageMatrix;
    private JButton[][][] buttonMatrix;
    private List<Product> productList;
    private int[] productListSlots;
    private int nextProductInList = -1;
    private ActionType actionType = ActionType.NONE;
    private  Product productBuffer;
    private int balance;

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
        //Check if current task is declined
        if (productListSlots[getSlot()] > -1) balance -= productList.get(productListSlots[getSlot()]).getReward();
        productListSlots[getSlot()] = nextProductInList = (nextProductInList + 1) % productList.size();
        refreshGUI();
    }

    private void changeSlot(ActionEvent e) { refreshGUI();}

    private void matrixButtonPressed(ActionEvent e) {
        var coord = getCoordinateFromButton((JButton)e.getSource());
        Product obj;
        switch(actionType) {
            case STORE:
                obj = productList.get(productListSlots[getSlot()]);
                if (!isStoreValid(obj, coord)) break;
                storageMatrix[coord.x][coord.y][coord.z] = obj;
                productListSlots[getSlot()] = -1;
                balance += obj.getReward();
                actionType = ActionType.NONE;
                break;
            case DESTROY:
                if (!isOutsourceValid(coord)) break;
                storageMatrix[coord.x][coord.y][coord.z] = null;
                balance -= 300;
                actionType = ActionType.NONE;
                break;
            case RESTORE_SRC:
                if (!isOutsourceValid(coord)) break;
                productBuffer = storageMatrix[coord.x][coord.y][coord.z];
                storageMatrix[coord.x][coord.y][coord.z] = null;
                actionType = ActionType.RESTORE_DEST;
                break;
            case RESTORE_DEST:
                if (!isStoreValid(productBuffer, coord)) break;
                storageMatrix[coord.x][coord.y][coord.z] = productBuffer;
                productBuffer = null;
                balance -= 100;
                actionType = ActionType.NONE;
                break;
            case OUTSOURCE:
                if (!isOutsourceValid(coord)) break;
                obj = storageMatrix[coord.x][coord.y][coord.z];
                storageMatrix[coord.x][coord.y][coord.z] = null;
                productListSlots[getSlot()] = -1;
                balance += obj.getReward();
                actionType = ActionType.NONE;
                break;
        }

        refreshGUI();
    }

    private boolean isStoreValid(Product p, Coordinate c) {
        var neighbour = storageMatrix[c.x][c.y][1 - c.z]; //The product next to our destination
        if (storageMatrix[c.x][c.y][c.z] != null) return false; //Is space already covered
        if (c.z == 1 && storageMatrix[c.x][c.y][0] != null) return false; //Is access blocked
        if (neighbour != null && (neighbour.getValueB().equals("beams") || p.getValueB().equals("beams"))) return false; //Neighbour blocks space because it's too big
        if (c.y > 0 && p.getValueB().equals("heavy") ||  c.y > 1 && p.getValueB().equals("medium")) return false; //Product is too heavy for desired level
        return true; //Everything is a-ok
    }

    private boolean isOutsourceValid(Coordinate c) {
        if (storageMatrix[c.x][c.y][c.z] == null) return false; //Is space already empty
        if (c.z == 1 && storageMatrix[c.x][c.y][0] != null) return false; //Is access blocked
        return true;
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
                    if (actionType == ActionType.OUTSOURCE && prod != null) buttonMatrix[x][y][z].setEnabled(item != null && prod.compareToProduct(item)); else buttonMatrix[x][y][z].setEnabled(true);
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
        lblVal.setText(prod == null ? "" : prod.getReward() + "???");
        lblTask.setText(prod == null ? "" : prod.getType());
        lblBalance.setText(balance + "???");
    }

    //Getter
    public JPanel getContentPanel() { return cPanel; }

    public Coordinate getCoordinateFromButton(JButton b) {
        var txt = b.getName();
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
