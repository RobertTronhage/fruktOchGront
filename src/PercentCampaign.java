//Robert Tronhage, robert.tronhage@iths.se
import java.io.*;
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
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public void setSalePercent(double salePercent) {
        this.salePercent = salePercent;
    }

    @Override
    public double calculateCampaignPrice(double price) {
        double saleInDecimal = salePercent / 100;
        double remainingMultiplier = 1 - saleInDecimal;
        return price * remainingMultiplier;
    }

    public static void saveCampaignToFile(String currentPath, double salePercent, String campaignName) {
        File directory = new File(currentPath + "\\Kampanjer");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Mappen 'Kampanjer' har skapats.");
            } else {
                System.out.println("Kunde inte skapa mappen 'Kampanjer'.");
            }
        }
        try {

            String fileName = currentPath + "\\Kampanjer\\Kampanj_" + campaignName + ".txt";
            File newFile = new File(fileName);
            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write(salePercent + ":" + campaignName);
            fileWriter.close();
            System.out.println("Kampanjdokument har skapats, du hittar det i mappen 'Kampanjer' i roten på projektet...");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("he gick inte!");
        }
    }

    public static void updatedCampaignToFile(String currentPath, ProductCampaign productCampaign, String oldCampaignName) {
        removeCampaignFile(currentPath, oldCampaignName);
        saveCampaignToFile(currentPath, productCampaign.getSalePercent(), productCampaign.getCampaignName());

    }

    public static void saveCampaignOnProductToFile(String currentPath, Product foundProduct, ProductCampaign foundCampaign) {
        File directory = new File(currentPath + "\\Kampanj_Produkter");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Mappen 'Kampanj_Produkter' har skapats.");
            } else {
                System.out.println("Kunde inte skapa mappen 'Kampanj_Produkter'.");
            }
        }
        try {
            String productLine = foundCampaign.getCampaignName() + ":" +
                    foundProduct.getCampaignCondition() + ":" + foundProduct.getProductId() + "\n";

            String fileName = currentPath + "/Kampanj_Produkter/Kampanj_Produkter.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(productLine);
            fileWriter.close();
            System.out.println("Kampanjdokument har skapats, du hittar det i mappen 'Produkter' i roten på projektet...");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("he gick inte!");
        }
    }

    public static void removeCampaignFile(String currentPath, String campaignName) {
        File file = new File(currentPath + "\\Kampanjer\\Kampanj_" + campaignName + ".txt");

        if (file.delete()) {
        } else {
            System.out.println("HE GÅR IT ATT TA BORT FILHELVETET!");
        }
    }

    public static void editCampaignOnProductFile(String currentPath, Product foundProduct, ProductCampaign foundCampaign) {
        try {
            File file = new File(currentPath + "\\Kampanj_Produkter\\Kampanj_Produkter.txt");
            File tempFile = new File(currentPath + "\\Kampanj_Produkter\\temp_Kampanj_Produkter.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(currentPath + "/Kampanj_Produkter/temp_Kampanj_Produkter.txt"));

            String productLine;
            boolean isUpdated = false;
            int productId = foundProduct.getProductId();

            while ((productLine = reader.readLine()) != null) {
                String[] splitProductLine = productLine.split(":");
                int productIdFromFile = Integer.parseInt(splitProductLine[2]);

                if (productId == productIdFromFile) {
                    productLine = foundCampaign.getCampaignName() + ":" +
                            foundProduct.getCampaignCondition() + ":" + foundProduct.getProductId() + "\n";
                    isUpdated = true;
                }
                writer.write((productLine));
            }

            reader.close();
            writer.close();

            if (file.delete()) {
            } else {
                System.out.println("HE GÅR IT!!!!!");
            }

            if (isUpdated) {
                if (tempFile.renameTo(file)) {
                    System.out.println("Nu är filen uppdaterad!");
                } else {
                    System.out.println("Det gick inte att uppdatera filen...");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeCampaignOnProductFromFile(String currentPath, Product foundProduct) {
        try {
            File file = new File(currentPath + "\\Kampanj_Produkter\\Kampanj_Produkter.txt");
            File tempFile = new File(currentPath + "\\Kampanj_Produkter\\temp_Kampanj_Produkter.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String productLine;

            while ((productLine = reader.readLine()) != null) {
                String[] splitProductLine = productLine.split(":");
                int productIdFromFile = Integer.parseInt(splitProductLine[2]);

                if (foundProduct.getProductId() != productIdFromFile) {
                    writer.write(productLine + "\n");
                }
            }

            reader.close();
            writer.close();


            if (file.delete()) {

                if (tempFile.renameTo(file)) {
                    System.out.println("Kampanjprodukten borttagen!");
                } else {
                    System.out.println("Det gick inte att byta namn på den temporära filen.");
                }
            } else {
                System.out.println("Det gick inte att ta bort den ursprungliga filen.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initCampaignOnProducts(String currentPath) {

        File productCampaignDirectory = new File(currentPath + "\\Kampanj_Produkter");

        if (!productCampaignDirectory.exists() || !productCampaignDirectory.isDirectory()) {
            System.out.println("hittar it Produkt_kampanjmappen!");
            return;
        }
        File fin = new File(currentPath + "/Kampanj_Produkter/Kampanj_Produkter.txt");

        try (Scanner fileScan = new Scanner(fin)) {
            while (fileScan.hasNextLine()) {
                String productString = fileScan.nextLine();
                String[] splitProductString = productString.split(":");
                String campaignName = splitProductString[0];
                int condition = Integer.parseInt(splitProductString[1]);
                int productId = Integer.parseInt(splitProductString[2]);
                Product product = Main.searchByProductId(productId);

                for (int i = 0; i < Main.allCampaigns.size(); i++) {
                    if (Main.allCampaigns.get(i).getCampaignName().equals(campaignName)) {
                        if (product == null) {
                            return;
                        }
                        product.setProductCampaign(Main.allCampaigns.get(i));
                        product.setcampaignCondition(condition);
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }


    }

    public static void initCampaigns(String currentPath) {
        File campaignDirectory = new File(currentPath + "/Kampanjer");

        if (!campaignDirectory.exists() || !campaignDirectory.isDirectory()) {
            System.out.println("hittar it kampanjmappen!");
            return;
        }

        File[] fileArray = campaignDirectory.listFiles();


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
