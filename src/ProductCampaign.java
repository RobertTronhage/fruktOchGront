public interface ProductCampaign {
    double calculateCampaignPrice(double price);
    double getSalePercent();
    String getCampaignName();

    String setCampaignName();

    void setSalePercent(double newSaleValue);
}
