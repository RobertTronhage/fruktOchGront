import java.io.*;
import java.util.Scanner;

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

    public Product(int productId, String name, String productGroup, double price, boolean unitPriceByWeight) {
        this.name = name;
        this.productGroup = productGroup;
        this.price = price;
        this.unitPriceByWeight = unitPriceByWeight;
        this.productId = productId;
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
        if (productCampaign != null && amountOfProduct >= campaignCondition) {
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
        if (amountOfProduct < campaignCondition) {
            System.out.println("Köp " + (campaignCondition - amountOfProduct) + (unitPriceByWeight ? "kg " : " stycken ") + " till för att få " +
                    productCampaign.getSalePercent() + "% rabatt");
        } else {
            System.out.println("wohoo! Du får " + productCampaign.getSalePercent() + "% rabatt");
        }
    }

    public void saveProductToFile() {
        File directory = new File("Produkter");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Mappen 'Produkter' har skapats.");
            } else {
                System.out.println("Kunde inte skapa mappen 'Produkter'.");
            }
        }
        try {
            String productLine = productId + ":" + name + ":" + productGroup + ":" + price + ":" + unitPriceByWeight + "\n";
            String fileName = "Produkter/produkter.txt";
            FileWriter fileWriter = new FileWriter(fileName,true);
            fileWriter.write(productLine);
            fileWriter.close();
            System.out.println("SY GÖTT!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("he gick inte!");
        }
    }

    public void updateProductToFile() {
        try {
            File file = new File("Produkter/produkter.txt");
            File tempFile = new File("Produkter/temp_produkter.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter("Produkter/temp_produkter.txt"));

            String productLine;
            boolean isUpdated = false;

            while ((productLine = reader.readLine()) != null) {
                String[] splitProductLine = productLine.split(":");
                int productId = Integer.parseInt(splitProductLine[0]);

                if (getProductId() == productId) {
                    productLine = productId + ":" + getName() + ":" + getProductGroup() + ":" + getRegularPrice() + ":" + isUnitPriceByWeight();
                    isUpdated = true;
                }
                writer.write((productLine + "\n"));
            }

            reader.close();
            writer.close();

            if (file.delete()) {
            } else {
                System.out.println("HE GÅR IT!!!!!");
            }

            if (isUpdated) {
                if (tempFile.renameTo(file)) {
                    System.out.println("ASKDJNASDKJNASKDJNASKJDNASD");
                } else {
                    System.out.println("Det gick inte att döpa om filen...");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initProducts(String currentPath) {

        File productDirectory = new File(currentPath+"\\Produkter");

        if (!productDirectory.exists() || !productDirectory.isDirectory()) {
            System.out.println("hittar it Produktmappen!");
            return;
        }
        File fin = new File(currentPath+"/Produkter/Produkter.txt");

        try (Scanner fileScan = new Scanner(fin)) {
            while (fileScan.hasNextLine()) {
                String productString = fileScan.nextLine();
                String[] splitProductString = productString.split(":");
                int productId = Integer.parseInt(splitProductString[0]);
                String productName = (splitProductString[1]);
                String productGroup = (splitProductString[2]);
                double productPrice = Double.parseDouble(splitProductString[3]);
                boolean unitPriceByWeight = Boolean.parseBoolean(splitProductString[4]);
                Product product = new Product(productId, productName, productGroup, productPrice, unitPriceByWeight);
                Main.assignProductToCorrectProductGroupArrayList(productGroup, product);
                Main.productIdTracker++;
            }

        } catch (FileNotFoundException ex) {

            throw new RuntimeException(ex);
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
