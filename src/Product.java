//Robert Tronhage, robert.tronhage@iths.se
public class Product {
    private String name;
    private String productGroup;
    //Price in SEK
    private double price;
    private double campaignPrice;
    private ProductCampaign productCampaign;
    private String campaignCondition;
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
    public double getPrice() {
        if(productCampaign != null){
           return campaignPrice;
        }
        return price;
    }

    public String getCampaignCondition() {
        return campaignCondition;
    }

    public void setcampaignCondition(String campaignCondition) {
        this.campaignCondition = campaignCondition;
    }

    public String printCampaignPrice(){
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
    public void setCampaignPrice(double campaignPrice) {
        this.campaignPrice = campaignPrice;
    }

    public double getCampaignPrice() {
        return campaignPrice;
    }

    public void setProductCampaign(ProductCampaign productCampaign) {
        this.productCampaign = productCampaign;
    }

    public boolean isUnitPriceByWeight() {
        return unitPriceByWeight;
    }

    public void setUnitPriceByWeight(boolean unitPriceByWeight) {
        this.unitPriceByWeight = unitPriceByWeight;
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
