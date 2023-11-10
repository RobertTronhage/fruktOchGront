public interface ProductCampaign {
    double calculateCampaignPrice(double price);
    double getSalePercent();
    String getCampaignName();

    void setCampaignName(String campaignName);

    void setSalePercent(double newSaleValue);
}
