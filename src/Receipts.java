import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipts {

    public void generateReceipt(ShoppingCart cart) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String receiptContent = "Receipt Date: " + dateFormat.format(date) + "\n";
        receiptContent += "Number of Items: " + cart.getTotalItems() + "\n";
        receiptContent += "Total Price: " + cart.getTotalPrice() + " SEK\n";

        // Visa kvitto på konsolen
        System.out.println(receiptContent);

        // Spara kvitto i en textfil
        saveReceiptToFile(receiptContent);
    }

    private void saveReceiptToFile(String receiptContent) {
        File directory = new File("Receipts");
        if (!directory.exists()) {
            directory.mkdirs();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "Receipts/Receipt_" + dateFormat.format(new Date()) + ".txt";
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(receiptContent);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

