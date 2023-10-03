//Robert Tronhage, robert.tronhage@iths.se
public class Product {

    private String name;
    private String productGroup;
    //Price in SEK
    private double price;
    private boolean unitPriceByWeight;
    private final int productId;

    public Product(String name, String productGroup, double price, boolean unitPriceByWeight) {
        this.name = name;
        this.productGroup = productGroup;
        this.price = price;
        this.unitPriceByWeight = unitPriceByWeight;
        this.productId = Main.productIdTracker++;
    }

    public Product(String name, String productGroup, double price) {
        this.name = name;
        this.productGroup = productGroup;
        this.price = price;
        this.productId = Main.productIdTracker++;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.productId = Main.productIdTracker++;
    }

    public Product(String name){
    this.name = name;
    this.productId = Main.productIdTracker++;
    }

    public String getName() {
        return name;
    }
    public String getProductGroup() {
        return productGroup;
    }
    public double getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }

    public boolean isUnitPriceByWeight() {
        return unitPriceByWeight;
    }

    public void setName(String newName) {
        this.name = newName;
    }
    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        if (unitPriceByWeight){
            return "{" + "ProduktId: " + productId +
                    " Produktnamn='" + name + '\'' + ", Kategori='" + productGroup + '\'' + ", Pris=" + String.format("%.2f", price) + " SEK" + " /kilo" + "}\n";
        }else{
            return "{" + "ProduktId: " + productId +
                    " Produktnamn='" + name + '\'' + ", Kategori='" + productGroup + '\'' + ", Pris=" + String.format("%.2f", price) + " SEK" + " /styck" + "}\n";
        }
    }

}
