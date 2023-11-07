
import java.util.ArrayList;

public class ShoppingCart {
    private double totalPrice;
    private  ArrayList<Product> productsInCart;
    private  ArrayList<Double> productAmounts;

    private int productTotAmount;

    public ShoppingCart(double totalPrice) {
        this.totalPrice = totalPrice;
        productsInCart = new ArrayList<>();
        productAmounts = new ArrayList<>();
    }

    public  void addProductToCart(Product product, double amount) {
        productsInCart.add(product);
        productAmounts.add(amount);
    }

    public double getProductTotAmountInPcs() {
        double productTotAmountInPcs=0;
        for (int i = 0; i<productsInCart.size();i++) {
            Product product = productsInCart.get(i);
            if (!product.isUnitPriceByWeight()){
                productTotAmountInPcs += productAmounts.get(i);
            }

        }
        return productTotAmountInPcs;
    }

    public double getProductTotAmountInKg() {
        double productTotAmountInKg=0;
        for (int i = 0; i<productsInCart.size();i++) {
            Product product = productsInCart.get(i);
            if (product.isUnitPriceByWeight()){
                productTotAmountInKg += productAmounts.get(i);
            }

        }
        return productTotAmountInKg;
    }

    public  ArrayList<Double> getProductAmounts() {
        return productAmounts;
    }

    public int getTotalItems() {
        return productsInCart.size();
    }


    public double getTotalPrice() {
        double totalPriceOfShoppingCart = 0.0;
        for (int i = 0; i < productsInCart.size(); i++) {
            Product product = productsInCart.get(i);
            double amount = productAmounts.get(i);
            totalPriceOfShoppingCart += product.getPrice(amount) * amount;
        }
        return totalPriceOfShoppingCart;
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
