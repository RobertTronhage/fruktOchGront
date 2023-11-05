
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShoppingCart {
    private double totalPrice;
    private  ArrayList<Product> productsInCart;
    private  ArrayList<Double> productAmounts;
    private ShoppingCart receiptContent;

    public ShoppingCart(double totalPrice) {
        this.totalPrice = totalPrice;
        productsInCart = new ArrayList<>();
        productAmounts = new ArrayList<>();
    }

    public  void addProductToCart(Product product, double amount) {
        productsInCart.add(product);
        productAmounts.add(amount);
    }

    public  ArrayList<Double> getProductAmounts() {
        return productAmounts;
    }

    public int getTotalItems() {
        return productsInCart.size();
    }

    public ShoppingCart getReceiptContent() {
        return receiptContent;
    }

//    public static int isCampaignConditionMet(){
//
//    }

    public double getTotalPrice() {
        double total = 0.0;
        for (int i = 0; i < productsInCart.size(); i++) {
            Product product = productsInCart.get(i);
            double amount = productAmounts.get(i);
            total += product.getPrice(amount) * amount;
        }
        return total;
    }

    public ArrayList<Product> getAllProductsInCart() {
        return productsInCart;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "totalPrice=" + String.format("%.2f",totalPrice) + '}';
    }
}
