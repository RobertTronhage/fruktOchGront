import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {

    public static void generateReceipt(ShoppingCart cart) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String receiptContent = "Kvitto datum: " + dateFormat.format(date) + "\n";
        receiptContent += "Totalt antal produkter: " + cart.getTotalItems() + "\n";
        receiptContent += "Totalt pris: " + cart.getTotalPrice() + " SEK\n";

        // Visa kvitto på konsolen
        System.out.println(receiptContent);

        // Spara kvitto i en textfil
        saveReceiptToFile(receiptContent);
    }

    public static void saveReceiptToFile(String receiptContent) {
        File directory = new File("Receipts");
        if (!directory.exists()) {
            directory.mkdirs();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "Receipts/Receipt_" + dateFormat.format(new Date()) + ".txt";
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(receiptContent);
                fileWriter.close();
                System.out.println("kvitto har skapats, du hittar den i roten på projektet...");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("he gick inte!");
            }
        }
    }
}

