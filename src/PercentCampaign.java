import java.io.File;

public class PercentCampaign implements ProductCampaign{

    private double salePercent;

    private String campaignName;

    public PercentCampaign(double salePercent,String campaignName) {
        this.salePercent=salePercent;
        this.campaignName=campaignName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    @Override
    public double calculateCampaignPrice(double price) {
        double saleInDecimal = salePercent / 100;
        double remainingMultiplier = 1-saleInDecimal;
        return price * remainingMultiplier;
    }



    public static void setAmountOfDiscount(){

    }

}
