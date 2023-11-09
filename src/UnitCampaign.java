public class UnitCampaign implements ProductCampaign{

    @Override
    public double calculateCampaignPrice(double price) {
        return 0;
    }

    @Override
    public double getSalePercent() {
        return 0;
    }


    public String getCampaignConditions() {
        return null;
    }
    private String campaignName;

//    File directory = new File("allCampaigns");
//            if (!directory.exists()) {
//        directory.mkdirs();

    public String getCampaignName() {
        return campaignName;
    }

    @Override
    public String setCampaignName() {
        return null;
    }

    @Override
    public void setSalePercent(double newSaleValue) {

    }


}
