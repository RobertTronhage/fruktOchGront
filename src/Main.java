//Robert Tronhage, robert.tronhage@iths.se
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static ArrayList<Product> allProducts = new ArrayList<>();
    public static ArrayList<Product> allFruits = new ArrayList<>();
    public static ArrayList<Product> allRootVegetables = new ArrayList<>();
    public static ArrayList<Product> allVegetables = new ArrayList<>();
    public static String[] productGroupArray = {"Frukt","Grönsaker","Rotfrukt", "Ingen kategori"};
    //ska man ha en array för "huvudgrupper" Frukt, stenfrukt, rotfrukt, grönsaker.
    // och sedan arrayList under det för att ha typ, äpple, päron osv???

    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        Product p1 = new Product("potatis","grönsak",44,true);
        allProducts.add(p1);

        System.out.println("Välkommen till Supervåg 3000");
        int menuOption;
        do {
            System.out.println("============================\nVälj ett alternativ nedan");
            System.out.println("0 - Avsluta program");
            System.out.println("1 - Visa alla produkter");
            System.out.println("2 - Sök efter specifik produkt");
            System.out.println("3 - Lägg till ny produkt");
            System.out.println("4 - Ändra en produkt");
            System.out.println("5 - Ta bort en produkt");
            System.out.println("6 - Pris på produkter");

            menuOption = input.nextInt();
            input.nextLine();

            switch (menuOption) {
                    case 1 -> printAllProducts();
                    case 2 -> searchProduct();
                    case 3 -> addProduct();
                    case 4 -> editProduct();
                    case 5 -> removeProduct();
                    case 6 -> priceOfProducts();
                }
        }while (menuOption != 0);
        System.out.println("Tack för att du använder supervåg 3000,\n hejdååå! :)");
    }

    /*public static int menuOption() {
        while (true) {
            try {
                int n = input.nextInt();
                return n;
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Ojdå, här gick nåt snett, vänligen välj ett alternativ 1-5");
            }
        }
    }*/

    private static void addProduct(){
        System.out.println("Vilken kategori tillhör produkten?");
        System.out.println("1 - Frukt \n2 - Grönsak \n3 - Rotfrukt \n4 - Citrusfrukt \n5 - Ingen kategori");
        int categoryChoice = input.nextInt();
        input.nextLine();
        System.out.println("Ange namnet på produkten du vill lägga till");
        String productName = input.nextLine();
        System.out.println("ange pris på produkten (kr)");
        double priceofproduct = input.nextDouble();
        input.nextLine();

    }

    public static void editProduct() {
        System.out.println("Vad vill du ändra?");
        System.out.println("1 - produktnamn\n 2 - Pris \n 3- produktgrupp");
        int userChoice = input.nextInt();
        input.nextLine();

        if (userChoice == 1){
            System.out.println("Vad heter produkten du vill ändra?");
            String productChoice = input.nextLine();


            for(int i=0;i>allProducts.size();i++){
                Product foundProduct = allProducts.get(i);
                if(foundProduct.getName().equals(productChoice)){
                    System.out.println("Vad är det nya namnet på produkten?");
                    String newName = input.nextLine();
                    foundProduct.setName(newName);

                }else {
                    System.out.println("Det produktnamnet existerar inte...");
                }
            }
        }
        System.out.println("Vilken produkt vill du ändra?");
        String userInput= input.nextLine();


    }
    public static void removeProduct(){

    }

    public static void searchProduct(){

    }

    private static void printAllProducts() {
        for (Product product : allProducts) {
            allProducts.getClass();
            //kunna visa alla produkter under viss kategori också?
        }
    }

    public static void priceOfProducts(){
    System.out.println(" ");
    }






}