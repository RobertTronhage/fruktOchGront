//Robert Tronhage, robert.tronhage@iths.se

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static ArrayList<Product> allFruits = new ArrayList<>();
    public static ArrayList<Product> allRootVegetables = new ArrayList<>();
    public static ArrayList<Product> allVegetables = new ArrayList<>();
    public static ArrayList<Product> allMushrooms = new ArrayList<>();
    public static ArrayList<Product> allUnassignedProductGroup = new ArrayList<>();
    public static String[] productGroupArray = {"Frukt", "Grönsaker", "Rotfrukt", "Svamp", "Ingen kategori"};
    // ska man ha en array för "huvudgrupper" Frukt, stenfrukt, rotfrukt, grönsaker.
    // och sedan arrayList under det för att ha typ, äpple, päron osv???

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Product p1 = new Product("potatis", "grönsak", 44, true);
        allVegetables.add(p1);

        System.out.println("Välkommen till Supervåg 3000");
        int menuOption = 0;
        boolean isUserInputValid = false;
        do {
            System.out.println("============================\nVälj ett alternativ nedan");
            System.out.println("0 - Avsluta program");
            System.out.println("1 - Visa produkter");
            System.out.println("2 - Sök efter specifik produkt");
            System.out.println("3 - Lägg till ny produkt");
            System.out.println("4 - Ändra en produkt");
            System.out.println("5 - Ta bort en produkt");
            System.out.println("6 - Pris på produkter");

            try {
                menuOption = input.nextInt();
                input.nextLine();
                if (menuOption < 0 || menuOption > 6) {
                    System.out.println("Felaktig inmatning, vänligen ange nummer 0-6...");
                    isUserInputValid = true;
                }else {
                    isUserInputValid = false;
                }
                switch (menuOption) {
                case 1 -> printProducts();
                //case 2 -> searchProduct();
                case 3 -> addProduct();
                //case 4 -> editProduct();
                //case 5 -> removeProduct();
                //case 6 -> priceOfProducts();
                }
            }catch (Exception e){
                input.nextLine();
                System.out.println("vänligen välj ett alternativ mellan 1-6...");
                isUserInputValid = true;
            }
        } while (menuOption != 0 || isUserInputValid);
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

    private static void addProduct() {
        int categoryChoice = 0;
        boolean unitPriceByWeight = true;
        double productPrice = 0;
        String productName;
        String productGroup = "";

        boolean isUserInputInvalid = false;
        do {
            System.out.println("Ange vilken kategori produkten tillhör");
            System.out.println("1 - " + productGroupArray[0] +
                    "\n2 - " + productGroupArray[1] +
                    "\n3 - " + productGroupArray[2] +
                    "\n4 - " + productGroupArray[3] +
                    "\n5 - " + productGroupArray[4]);
            try {
                categoryChoice = input.nextInt();
                input.nextLine();
                if (categoryChoice < 1 || categoryChoice > 5) {
                    System.out.println("felaktig inmatning, vänligen ange nummer 1-5...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                    switch (categoryChoice) {
                        case 1 -> productGroup = productGroupArray[0];
                        case 2 -> productGroup = productGroupArray[1];
                        case 3 -> productGroup = productGroupArray[2];
                        case 4 -> productGroup = productGroupArray[3];
                        case 5 -> productGroup = productGroupArray[4];
                    }
                }
            } catch (Exception e) {
                input.nextLine();
                System.out.println("felaktig inmatning, vänligen ange nummer 1-5...");
                isUserInputInvalid = true;
            }
        } while (isUserInputInvalid);

        do {
            System.out.println("Ange namn på produkten: ");
            productName = input.nextLine();
            if (productName.isEmpty()) {
                System.out.println("vänligen fyll i ett giltigt namn");
            }

        } while (productName.isEmpty());

        do {
            System.out.println("Lägg till pris på produkten:");
            try {
                productPrice = input.nextDouble();

                if (productPrice < 0 || productPrice > 2000) {
                    System.out.println("felaktig inmatning, vänligen ange pris i kronor...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                }
            } catch (Exception e) {
                System.out.println("felaktig inmatning, vänligen ange pris i kronor...");
                isUserInputInvalid = true;
            }
            input.nextLine();

        } while (isUserInputInvalid);

        do {
            System.out.println("Ange om produkten säljs styckvis eller kilovis\n1 - Styckvis\n2 - Kilopris");
            try {
                int userInput = input.nextInt();
                input.nextLine();
                if (userInput < 1 || userInput > 2) {
                    System.out.println("felaktig inmatning, vänligen ange nummer 1 eller 2...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                    if (userInput == 1) {
                        unitPriceByWeight = true;
                    } else {
                        unitPriceByWeight = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("felaktig inmatning, vänligen ange nummer 1 eller 2...");
                isUserInputInvalid = true;
            }

        } while (isUserInputInvalid);
        Product newProduct = new Product(productName, productGroup, productPrice, unitPriceByWeight);
        switch(categoryChoice){
            case 1 -> allFruits.add(newProduct);
            case 2 -> allVegetables.add(newProduct);
            case 3 -> allRootVegetables.add(newProduct);
            case 4 -> allMushrooms.add(newProduct);
            case 5 -> allUnassignedProductGroup.add(newProduct);
        }
    }

    /*public static void editProduct() {
        System.out.println("Vad vill du ändra?");
        System.out.println("1 - produktnamn\n 2 - Pris \n 3- produktgrupp");
        int userChoice = input.nextInt();
        input.nextLine();

        if (userChoice == 1) {
            System.out.println("Vad heter produkten du vill ändra?");
            String productChoice = input.nextLine();

            for (int i = 0; i > allProducts.size(); i++) {
                Product foundProduct = allProducts.get(i);
                if (foundProduct.getName().equals(productChoice)) {
                    System.out.println("Vad är det nya namnet på produkten?");
                    String newName = input.nextLine();
                    foundProduct.setName(newName);

                } else {
                    System.out.println("Det produktnamnet existerar inte...");
                }
            }
        }
        System.out.println("Vilken produkt vill du ändra?");
        String userInput = input.nextLine();


    }

    public static void removeProduct() {

    }

    public static void searchProduct() {

    }
*/
    private static void printProducts() {

        int userChoice;
        do {
            System.out.println("vilka produkter vill du visa?");
            System.out.println("1 - " + productGroupArray[0] +
                    "\n2 - " + productGroupArray[1] +
                    "\n3 - " + productGroupArray[2] +
                    "\n4 - " + productGroupArray[3] +
                    "\n5 - " + "Alla kategorier" +
                    "\n6 - Åter till huvudmeny");
            userChoice = input.nextInt();
            input.nextLine();

            switch (userChoice) {
                case 1 -> System.out.println("1");
                case 2 -> System.out.println("2");
                case 3 -> System.out.println("3");
                case 4 -> System.out.println("4");
//ska man loopa igenom alla arraylistor eller ska man skapa en separat "allProducts"?????
                case 5 -> System.out.println("5");
                case 6 -> {
                    return;
                }
            }

        } while (userChoice != 6);


    }
/*
    public static void priceOfProducts() {
        System.out.println(" ");
    }
*/

}