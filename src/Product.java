import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

//Describes a product to be contained into the matrix
public class Product {
    //Attributes
    private final String type;
    private final String name;
    private final String valueA;
    private final String valueB;
    private final int reward;

    //Getter
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public int getReward() {
        return reward;
    }
    public String getValueA() {
        return valueA;
    }
    public String getValueB() {
        return valueB;
    }

    //Constructor
    public Product(String type, String name, String attribute1, String attribute2, int reward) {
        this.type = type;
        this.name = name;
        this.valueA = attribute1;
        this.valueB = attribute2;
        this.reward = reward;
    }

    //Static load method
    public static List<Product> loadProducts() {
        List<Product> ret = new ArrayList<>();

        try {
            BufferedReader streamr = new BufferedReader(new FileReader("Leistungsnachweis.csv"));

            String ln = streamr.readLine();
            while ((ln = streamr.readLine()) != null) {
                String[] attribs = ln.split(";");
                ret.add(new Product(attribs[1], attribs[2], attribs[3], attribs[4], Integer.parseInt(attribs[5])));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ret;
    }
}
