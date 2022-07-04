

import java.util.Objects;

public class Lager {
    //Attributes
    private int x = 4;
    private int y = 3;
    private int z = 2;
    private Boolean isEmpty = true;
    private int anzPaletten = x * y * z;
    private Product Lager[][][] = new Product[x][y][z];
    private String searchedName;
    Boolean gefunden = false;


    //Methods
    public Boolean isEmpty(int x, int y, int z) {
        if (Lager[x][y][z] == null) {
            isEmpty = true;
        } else isEmpty = false;
        return isEmpty;
    }
    public String  getProduktName(int x, int y, int z) {
        return Lager[x][y][z].getName();
    }

    public void insertProduct(int x, int y, int z, Product produkte) {
        Lager[x][y][z] = produkte;
    }

    public Product getProdukt(int x, int y, int z) {
        return Lager[x][y][z];
    }

    public void tauscheProdukt(int x, int y, int z, int xZ, int yZ, int zZ) {
        var temp = Lager[x][y][z];
        Lager[x][y][z] = Lager[xZ][yZ][zZ];
        Lager[xZ][yZ][zZ] = temp;
    }


    public Boolean foundProduct(Product searchElement, int i, int j, int k) {
        if (Objects.equals(Lager[i][j][k].getName(), searchElement.getName()) && Objects.equals(Lager[i][j][k].getValueA(), searchElement.getValueA()) && Objects.equals(Lager[i][j][k].getValueB(), searchElement.getValueB())) {
            System.out.println("Auslagerung!");
            gefunden = true;
            searchedName = Lager[i][j][k].getName();
        } else gefunden = false;

        return gefunden;
    }


    public String getSearchedName() {
        return searchedName;
    }
}
