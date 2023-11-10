//Robert Tronhage, robert.tronhage@iths.se
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {

    public static void generateReceipt(ShoppingCart cart) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String receiptContent = "Kvitto No: " + dateFormat.format(date) + "\n\n\n" +
                                "Antal\t\t\tProdukt\t\t\tTot\n" +
                                generateProductAmountInReceipt(cart) + " SEK" +
                                "\n\n\n========================\nTot Antal (Styck): "+
                                cart.getProductTotAmountInPcs() +
                                "\nTot Antal (kg): " + cart.getProductTotAmountInKg()+ "\nTotalt pris: " +
                                String.format("%.2f", cart.getTotalPrice());

        // Visa kvitto på konsolen
        System.out.println(receiptContent);

        // Spara kvitto i en textfil
        saveReceiptToFile(receiptContent);
    }

    public static String generateProductAmountInReceipt(ShoppingCart cart){
        String productAmount="";

        for (int i = 0; i < cart.getProductAmounts().size(); i++) {
            productAmount += cart.getProductAmounts().get(i)+"\t\t\t" + cart.getAllProductsInCart().get(i).getName()+
                    "\t\t\t"+ cart.getAllProductsInCart().get(i).getPrice()*cart.getProductAmounts().get(i) + " SEK\n";
        }
        return productAmount;
    }
    public static void saveReceiptToFile(String receiptContent) {
        File directory = new File("Kvitton");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Mappen 'Kvitton' har skapats.");
            } else {
                System.out.println("Kunde inte skapa mappen 'Kvitton'.");
            }
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = "Kvitton/Kvitto_" + dateFormat.format(new Date()) + ".txt";
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(receiptContent);
            fileWriter.close();
            System.out.println("kvitto har skapats, du hittar det i mappen 'Kvitton' i roten på projektet...");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("he gick inte!");
        }
    }
}

