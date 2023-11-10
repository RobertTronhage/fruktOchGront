//Här hade jag som ambition att använda mig av Strategy pattern efter inspiration från google samt snack med bekanta
// för att hantera olika sorters kampanjer, tiden vart dock knapp och jag hann bara bena ut PercentCampaign.


public interface ProductCampaign {
    double calculateCampaignPrice(double price);
    double getSalePercent();
    String getCampaignName();

    void setCampaignName(String campaignName);

    void setSalePercent(double newSaleValue);
}
