import java.io.File;

public class PercentCampaign implements ProductCampaign{

    private double campaignPrice;
    private String campaigncondition;

    public PercentCampaign(double price) {
        campaignPrice = price /2;
    }

    @Override
    public double getCampaignPrice() {
        return campaignPrice;
    }


//    File directory = new File("allCampaigns");
//            if (!directory.exists()) {
//        directory.mkdirs();

    @Override
    public String getCampaignCondition() {
        return campaigncondition;
    }

}
