//Robert Tronhage, robert.tronhage@iths.se

import java.util.*;

public class Main {
    public static ArrayList<Product> allFruits = new ArrayList<>();
    public static ArrayList<Product> allRootVegetables = new ArrayList<>();
    public static ArrayList<Product> allVegetables = new ArrayList<>();
    public static ArrayList<Product> allMushrooms = new ArrayList<>();
    public static ArrayList<Product> allUnassignedProductGroup = new ArrayList<>();
    public static ArrayList<ArrayList<Product>> allProducts = new ArrayList<>();
    public static String[] productGroupArray = {"Frukt", "Grönsaker", "Rotfrukt", "Svamp", "Ingen kategori"};
    public static int productIdTracker = 1;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Product p1 = new Product("aaaaaaaaaa", "Rotfrukt", 44, true,productIdTracker++);
        Product p2 = new Product("bbbbbbbb", "Frukt", 44, false,productIdTracker++);
        Product p3 = new Product("cccccccc", "Rotfrukt", 44, true,productIdTracker++);
        Product p4 = new Product("ddddddddd", "Svamp", 44, true,productIdTracker++);
        Product p5 = new Product("eeeeeeee", "Rotfrukt", 44, true,productIdTracker++);
        Product p6 = new Product("ffffff", "Ingen kategori", 44, true,productIdTracker++);
        Product p7 = new Product("gggggggg", "Ingen kategori", 44, true,productIdTracker++);
        Product p8 = new Product("hhhhhhhhhh", "Grönsaker", 44, true,productIdTracker++);
        Product p9 = new Product("iiiiiiii", "Grönsaker", 44, true,productIdTracker++);

        allVegetables.add(p1);
        allFruits.add(p2);
        allRootVegetables.add(p3);
        allMushrooms.add(p4);
        allRootVegetables.add(p5);
        allUnassignedProductGroup.add(p6);
        allUnassignedProductGroup.add(p7);
        allVegetables.add(p8);
        allVegetables.add(p9);

        allProducts.addAll(Collections.singleton(allFruits));
        allProducts.addAll(Collections.singleton(allVegetables));
        allProducts.addAll(Collections.singleton(allRootVegetables));
        allProducts.addAll(Collections.singleton(allMushrooms));
        allProducts.addAll(Collections.singleton(allUnassignedProductGroup));

        System.out.println("Välkommen till Supervåg 3000");
        int menuOption = 6;
        boolean isUserInputInvalid = false;
        do {
            System.out.println("============================\nVälj ett alternativ nedan");
            System.out.println("1 - Sök efter specifik produkt");
            System.out.println("2 - Lägg till ny produkt");
            System.out.println("3 - Ändra en produkt");
            System.out.println("4 - Ta bort en produkt");
            System.out.println("5 - Pris på produkter");
            System.out.println("6 - Avsluta program");

            try {
                menuOption = input.nextInt();
                input.nextLine();
                if (menuOption < 1 || menuOption > 7) {
                    System.out.println("Felaktig inmatning, vänligen ange nummer 0-6...");
                    isUserInputInvalid = true;
                }else {
                    isUserInputInvalid = false;
                }
                switch (menuOption) {
                case 1 -> searchMenu();
                case 2 -> addProduct();
                //case 3 -> editProduct();
                //case 4 -> removeProduct();
                //case 5 -> priceOfProducts();

                }
            }catch (Exception e){
                input.nextLine();
                System.out.println("vänligen välj ett alternativ mellan 0-6...");
                isUserInputInvalid = true;
            }
        } while (menuOption != 6 || isUserInputInvalid);
        System.out.println("Tack för att du använder supervåg 3000,\n hejdååå! :)");
    }

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
                System.out.println("felaktig inmatning, vänligen ange pris i kronor \n" +
                        "Om det är decimaler i priset måste detta anges deicmal-komma...");
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
        Product newProduct = new Product(productName, productGroup, productPrice, unitPriceByWeight,productIdTracker++);
        switch(categoryChoice){
            case 1 -> allFruits.add(newProduct);
            case 2 -> allVegetables.add(newProduct);
            case 3 -> allRootVegetables.add(newProduct);
            case 4 -> allMushrooms.add(newProduct);
            case 5 -> allUnassignedProductGroup.add(newProduct);
        }
    }

  /*  public static void editProduct() {
        System.out.println("Vad vill du ändra?");
        System.out.println("1 - Produktnamn\n 2 - Pris \n 3- produktgrupp");
        int userChoice = input.nextInt();
        input.nextLine();

        if (userChoice == 1) {
            System.out.println("Vad heter produkten du vill ändra?");
            String productChoice = input.nextLine();

            for (int i = 0; i < allProducts.size(); i++) {
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
    }*/

    public static void removeProduct() {
        int userInput = 3;
        do {
            System.out.println("Fyll i vad produkten du vill ta bort heter:");
        }while(userInput != 3);
    }

    public static void searchMenu() {
//kategorisök och fritextsök!
        int userChoice = 0;
        boolean isUserInputInvalid = false;
        do {
            System.out.println("Vill du söka efter kategori eller fritextsöka?" +
                    "\n1 - Fritext\n2 - söka efter kategori\n3 - Åter till huvudmenyn");
            try{
                userChoice = input.nextInt();
                input.nextLine(); //consumes rest of line
                if (userChoice < 1 || userChoice > 3) {
                    System.out.println("Felaktig inmatning, vänligen ange nummer 1-3...");
                    isUserInputInvalid = true;
                }else {
                    isUserInputInvalid = false;
                }
                    switch (userChoice) {
                        case 1 -> freeTextSearch();
                        case 2 -> printProducts();
                        case 3 -> {break;} //to be super clear...
                    }

            }catch(Exception e){
                input.nextLine(); //consumes rest of line of userChoice
                System.out.println("Här gick det snett, vänligen välj alternativ 1 - 3...");
                isUserInputInvalid = true;
            }
        }while(userChoice != 3 || isUserInputInvalid);
    }

    public static void freeTextSearch(){
        List<Product> searchResult= new ArrayList<>();

        System.out.println("Skriv in namnet på produkten du söker\nNamn:");
        String uniqueProduct = input.nextLine();

        for (ArrayList<Product> productGroup: allProducts){
            for (Product product : productGroup){
                if (product.getName().toLowerCase().contains(uniqueProduct.toLowerCase())){
                    searchResult.add(product);
                }
            }
        }
        if(searchResult.isEmpty()) {
            System.out.println("Det produktnamnet finns inte...\n" +
                    "Försök igen eller testa söka via kategori\n" +
                    "1 - sök igen\n2 - sök efter kategori\n3 - åter till huvudmeny");
        }else {
            for (Product p : searchResult) {
                System.out.println(p);
            }
        }
    }

    private static void printProducts() {
        int userChoice;
        do {
            System.out.println("vilka produkter vill du visa?");
            System.out.println("1 - " + productGroupArray[0] +
                    "\n2 - " + productGroupArray[1] +
                    "\n3 - " + productGroupArray[2] +
                    "\n4 - " + productGroupArray[3] +
                    "\n5 - " + productGroupArray[4] +
                    "\n6 - " + "Alla kategorier" +
                    "\n7 - Åter till huvudmeny");
            userChoice = input.nextInt();
            input.nextLine();

            switch (userChoice) {
                case 1 -> {
                    for (Product allFruit : allFruits) {
                        System.out.print(allFruit.toString());
                    }
                }
                case 2 -> {
                    for (Product allVegetables: allVegetables){
                        System.out.println(allVegetables.toString());
                    }
                }
                case 3 -> {
                    for (Product allRootVegetables: allRootVegetables){
                        System.out.println(allRootVegetables.toString());
                    }

                }
                case 4 -> {
                    for (Product allMushrooms: allMushrooms){
                        System.out.println(allMushrooms.toString());
                    }
                    return;
                }
//ska man loopa igenom alla arraylistor eller ska man skapa en separat "allProducts"????? hur sorterar man den om man vill det isf???
                case 5 -> {
                    for (Product allUnassigned : allUnassignedProductGroup) {
                        System.out.print(allUnassignedProductGroup.toString());
                    }
                    return;
                }
                case 6 -> {
                    for (ArrayList<Product> productGroup : allProducts) {
                        for (Product product: productGroup) {
                            System.out.println(product.toString());
                        }
                    }
                        return;
                }
                case 7 -> {
                    return;
                }
            }

        } while (userChoice != 6);
    }

    public static void priceOfProducts() {
        //låna metoden av searchProducts!
        System.out.println("piset är:");
    }


}