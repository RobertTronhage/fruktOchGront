import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PercentCampaign implements ProductCampaign {

    private double salePercent;

    private String campaignName;

    public PercentCampaign(double salePercent, String campaignName) {
        this.salePercent = salePercent;
        this.campaignName = campaignName;
    }

    public double getSalePercent() {
        return salePercent;
    }

    public String getCampaignName() {
        return campaignName;
    }

    @Override
    public double calculateCampaignPrice(double price) {
        double saleInDecimal = salePercent / 100;
        double remainingMultiplier = 1 - saleInDecimal;
        return price * remainingMultiplier;
    }


    public void generateCampaign() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String campaignContent = "";


    }

    public static void saveCampaignToFile(double salePercent, String campaignName) {
        File directory = new File("Kampanjer");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Mappen 'Kampanjer' har skapats.");
            } else {
                System.out.println("Kunde inte skapa mappen 'Kampanjer'.");
            }
        }
        try {

            String fileName = "Kampanjer/Kampanj_" + campaignName + ".txt";
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(salePercent + ":" + campaignName);
            fileWriter.close();
            System.out.println("Kampanjdokument har skapats, du hittar det i mappen 'Kampanjer' i roten på projektet...");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("he gick inte!");
        }
    }

    public static void initCampaigns() {
        File campaignDirectory = new File("Kampanjer");

        if (!campaignDirectory.exists() || !campaignDirectory.isDirectory()) {
            System.out.println("hittar it kampanjmappen!");
            return;
        }

        File[] fileArray = campaignDirectory.listFiles();

        assert fileArray != null;

        for (File f : fileArray) {
            if (f.getName().endsWith(".txt")) {
                try (Scanner fileScan = new Scanner(f)) {
                    while (fileScan.hasNextLine()) {
                        String campaignLine = fileScan.nextLine();
                        String[] splitCampaignLine = campaignLine.split(":");
                        double campaignLineDouble = Double.parseDouble(splitCampaignLine[0]);
                        String campaignLineString = splitCampaignLine[1];

                        PercentCampaign a = new PercentCampaign(campaignLineDouble, campaignLineString);
                        Main.allCampaigns.add(a);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Hittade inte filen: " + f.getName());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "PercentCampaign{" +
                "salePercent=" + salePercent +
                ", campaignName='" + campaignName + '\'' +
                '}';
    }
}
