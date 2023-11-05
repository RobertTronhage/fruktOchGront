//Robert Tronhage, robert.tronhage@iths.se
public class Product {
    private String name;
    private String productGroup;
    //Price in SEK
    private double price;
    private double campaignPrice;
    private ProductCampaign productCampaign;
    private int campaignCondition;
    private boolean unitPriceByWeight;
    private final int productId;

    public Product(String name, String productGroup, double price, boolean unitPriceByWeight) {
        this.name = name;
        this.productGroup = productGroup;
        this.price = price;
        this.unitPriceByWeight = unitPriceByWeight;
        this.productId = Main.productIdTracker++;
    }

    public String getName() {
        return name;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public int getCampaignCondition() {

        return campaignCondition;
    }

    public void setcampaignCondition(int campaignCondition) {

        this.campaignCondition = campaignCondition;
    }

    public String printCampaignPrice() {
        return ("Kampanjpris: " + campaignPrice + "Ordinarie pris: " + price);
    }

    public int getProductId() {
        return productId;
    }

    public ProductCampaign getProductCampaign() {
        return productCampaign;
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

    private void setCampaignPrice() {
        this.campaignPrice = productCampaign.calculateCampaignPrice(price);
    }

    public double getPrice() {
        if (productCampaign != null) {
            return campaignPrice;
        }
        return price;
    }
    public double getPrice(double amountOfProduct) {
        if (productCampaign != null && amountOfProduct>=campaignCondition) {
            return campaignPrice;
        }
        return price;
    }

    public double getRegularPrice() {
        return price;
    }

    public void setProductCampaign(ProductCampaign productCampaign) {
        this.productCampaign = productCampaign;
        setCampaignPrice();
    }

    public boolean isUnitPriceByWeight() {
        return unitPriceByWeight;
    }

    public void setUnitPriceByWeight(boolean unitPriceByWeight) {
        this.unitPriceByWeight = unitPriceByWeight;
    }

    public void checkCampaignCondition(double amountOfProduct) {
        if (amountOfProduct<campaignCondition){
            System.out.println("Köp " + (campaignCondition - amountOfProduct) + (unitPriceByWeight ? "kg ":" stycken ") + " till för att få " +
            productCampaign.getSalePercent() + "% rabatt");
        }else{
            System.out.println("wohoo! Du får " + productCampaign.getSalePercent() + "% rabatt");
        }
    }

    @Override
    public String toString() {
        if (unitPriceByWeight) {
            return "{" + "ProduktId: " + productId +
                    " Produktnamn='" + name + '\'' + ", Kategori='" + productGroup + '\'' + ", Pris=" + String.format("%.2f", getRegularPrice()) + " SEK" + " /kilo" + "}\n";
        } else {
            return "{" + "ProduktId: " + productId +
                    " Produktnamn='" + name + '\'' + ", Kategori='" + productGroup + '\'' + ", Pris=" + String.format("%.2f", getRegularPrice()) + " SEK" + " /styck" + "}\n";
        }
    }


}
