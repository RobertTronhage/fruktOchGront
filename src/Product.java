//Robert Tronhage, robert.tronhage@iths.se
public class Product {

    private String name;
    private String productGroup;
    //Price in SEK
    private double price;

    private boolean unitPriceByWeight;

    public Product(String name, String productGroup, double price, boolean unitPriceByWeight) {
        this.name = name;
        this.productGroup = productGroup;
        this.price = price;
        this.unitPriceByWeight = unitPriceByWeight;
    }

    public Product(String name, String productGroup, double price) {
        this.name = name;
        this.productGroup = productGroup;
        this.price = price;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name){
    this.name = name;
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
        return "Product{" +
                "Namn='" + name + '\'' +
                ", Kategori='" + productGroup + '\'' +
                ", Pris=" + price + "SEK" +
                ", Säljs=" + unitPriceByWeight +
                "}\n";
    }
}
