//Robert Tronhage, robert.tronhage@iths.se

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static ArrayList<Product> allFruits = new ArrayList<>();
    public static ArrayList<Product> allRootVegetables = new ArrayList<>();
    public static ArrayList<Product> allVegetables = new ArrayList<>();
    public static ArrayList<Product> allMushrooms = new ArrayList<>();
    public static ArrayList<Product> allUnassignedProductGroup = new ArrayList<>();
    public static ArrayList<ArrayList<Product>> allProducts = new ArrayList<>();
    public static ArrayList<Product> productsInCart = new ArrayList<>();
    public static ArrayList<Double> productAmounts = new ArrayList<>();
    public static String[] productGroupArray = {"Frukt", "Grönsaker", "Rotfrukt", "Svamp", "Ingen kategori"};
    
    public static int productIdTracker = 1;
    static Scanner input = new Scanner(System.in);
    static String GREEN = "\u001B[32m";
    static String RESET = "\u001B[0m";
    static User loggedInUser;
    static ShoppingCart shoppingCart;

    public static void main(String[] args) {

        initAllProducts();
        mainMenu();
        System.out.println("Tack för att du använder supervåg 3000,\n hejdååå! :)");
    }
    private static void initAllProducts() {

        Product p1 = new Product("aaaaaaaa", productGroupArray[0], 13, true);
        Product p2 = new Product("bbbbbbbbb", productGroupArray[1], 132, false);
        Product p3 = new Product("CCCCCCC", productGroupArray[2], 13.14, true);
        Product p4 = new Product("DDDDddddd", productGroupArray[4], 2.00, false);
        Product p5 = new Product("EEEEEEEEE", productGroupArray[3], 3, true);
        Product p6 = new Product("FffASdasd", productGroupArray[2], 1, false);
        Product p7 = new Product("gggggggg", productGroupArray[0], 1.3, true);

        PercentCampaign a = new PercentCampaign(p4.getPrice());
        p4.setcampaignCondition("KÖP MER!!!");
        p4.setProductCampaign(a);
        a.getCampaignCondition();


        allFruits.add(p1);
        allVegetables.add(p2);
        allRootVegetables.add(p3);
        allUnassignedProductGroup.add(p4);
        allMushrooms.add(p5);
        allRootVegetables.add(p6);
        allFruits.add(p7);

        allProducts.add(allFruits);
        allProducts.add(allVegetables);
        allProducts.add(allRootVegetables);
        allProducts.add(allMushrooms);
        allProducts.add(allUnassignedProductGroup);
    }
    private static void mainMenu() {
        System.out.println("Välkommen till Supervåg 3000");
        int menuOption = 6;
        do {
            System.out.println("============================\n"
                    + "Välj ett alternativ nedan\n"
                    + "1 - Sök efter specifik produkt\n"
                    + "2 - Se pris och köp produkter\n"
                    + "3 - Se aktuella Kampanjer\n"
                    + "4 - Se varukorg\n"
                    + "5 - Personal-inloggning\n"
                    + "6 - Avsluta program");

            menuOption = getValidIntegerInput(input, 1, 6);

            switch (menuOption) {
                case 1 -> searchMenu();
                case 2 -> calculatePriceOfProducts();
                case 3 -> printCampaigns();
                case 4 -> printShoppingCart();
                case 5 -> verifyUserCredentials();
            }
        } while (menuOption != 6);
    }
    private static void verifyUserCredentials() {
        System.out.print("Ange användarnamn: ");
        String userName = input.nextLine();
        System.out.print("Ange lösenord: ");
        String userPassword = input.nextLine();
        File fin = new File("users.txt");
        boolean loggedIn = false; // flagga för att kontrollera om inloggningen är framgångsrik.

        try (Scanner fileScan = new Scanner(fin)) {
            while (fileScan.hasNextLine()) {
                String userString = fileScan.nextLine();
                String[] splitUserString = userString.split(":");
                boolean active = Boolean.parseBoolean(splitUserString[3]);
                boolean admin = Boolean.parseBoolean(splitUserString[4]);
                int userId = Integer.parseInt(splitUserString[0]);

                if (userName.equals(splitUserString[1]) && userPassword.equals(splitUserString[2])) {
                    loggedInUser = new User(userId, splitUserString[1], splitUserString[2], active, admin);
                    System.out.print(GREEN);
                    loggedInUserMenu();
                    loggedIn = true; // Användaren är inloggad.
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("Felaktigt användarnamn eller lösenord");
            }
        } catch (FileNotFoundException e) {
            System.out.println("här vart det strul..Hittade inte filen :(");
        }
    }
    private static void loggedInUserMenu() {

        String userName = loggedInUser.getUserName();
        System.out.println("\n\nVälkommen " + userName);
        int menuOption;
        do {
            System.out.println("============================\n"
                    + "Välj ett alternativ nedan\n"
                    + "1 - Sök efter specifik produkt\n"
                    + "2 - Lägg till ny produkt\n"
                    + "3 - Ändra en produkt\n"
                    + "4 - Ta bort en produkt\n"
                    + "5 - Pris på produkter\n"
                    + "6 - lägg till Kampanjpris\n"
                    + "7 - Redigera användare\n"
                    + "8 - Logga ut");

            menuOption = getValidIntegerInput(input, 1, 8);

            switch (menuOption) {
                case 1 -> searchMenu();
                case 2 -> addProduct();
                case 3 -> editProduct();
                case 4 -> removeProduct();
                case 5 -> calculatePriceOfProducts();
                case 6 -> addCampaignOnProducts();
                case 7 -> editUsers(loggedInUser);
            }
        } while (menuOption != 8);
        System.out.print(RESET);
    }
    public static void editUsers(User loggedInUser) {


        int userChoice;
        do {
            if (!loggedInUser.isUserAdmin()){
                System.out.println("Endast Användare med Admin-behörighet kan komma åt denna tjänst...");
                return;
            }

            System.out.println("vad vill du göra\n" +
                    "1 - Lägg till en användare\n" +
                    "2 - Ändra behörighet för användare\n" +
                    "3 - Inaktivera användare\n" +
                    "4 - Ändra lösenord på användare\n" +
                    "5 - Visa alla användare\n" +
                    "6 - Åter till Personalmeny");

            userChoice = getValidIntegerInput(input, 1, 6);

            switch (userChoice) {
                case 1 -> addUser();
                case 2 -> System.out.println("change");
                case 3 -> System.out.println("disable user");
                case 4 -> System.out.println("change password for user");
                case 5 -> printAllUsers();
            }

            User user = new User("userPassword", "userUserName", true);
        }while(userChoice!=6);
    }
    public static void printAllUsers(){

        File fin = new File("users.txt");
        try (Scanner fileScan = new Scanner(fin)) {
            System.out.println("ID:Name:Password:isAdmin:isActive");
            while (fileScan.hasNextLine()) {
                String UserData = fileScan.nextLine();
                System.out.println(UserData);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ojdå");
        }
    }
    public static void addUser(){
        System.out.println("Ange användarnamn för den nya användaren:");
        String userName = input.nextLine();
        System.out.println("Ange lösenord för den nya användaren:");
        String userPassword = input.nextLine();
        System.out.println("ska den nya användaren ha Admin rättigheter?\n" +
                "1 - Ja\n" +
                "2 - Nej");
        int isUserAdmin = getValidIntegerInput(input,1,2);
        boolean isAdmin = (isUserAdmin == 1) ? true : false;

        User user = new User(userPassword,userName,isAdmin);
        System.out.println("Användare: " + userName + " har skapats");
    }
    public static int getValidIntegerInput(Scanner input, int minValue, int maxValue) {
        int userInput = 0;
        boolean isUserInputInvalid;

        do {
            isUserInputInvalid = false;
            try {
                userInput = input.nextInt();
                if (userInput < minValue || userInput > maxValue) {
                    System.out.println("Felaktig inmatning, vänligen ange en siffra mellan " + minValue + " och " + maxValue + "...");
                    isUserInputInvalid = true;
                }
            } catch (Exception e) {
                System.out.println("Felaktig inmatning, vänligen ange en siffra mellan " + minValue + " och " + maxValue + "...");
                isUserInputInvalid = true;
            }
            input.nextLine();
        } while (isUserInputInvalid);

        return userInput;
    }
    public static double getValidDoubleInput(Scanner input, double minValue) {
        double userInput = 0.0;
        boolean isUserInputInvalid;

        do {
            isUserInputInvalid = false;
            try {
                String inputString = input.nextLine().replace(',', '.');
                userInput = Double.parseDouble(inputString);

                if (userInput < minValue) {
                    System.out.println("Felaktig inmatning, vänligen ange ett tal över " + minValue + "...");
                    isUserInputInvalid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("felaktig inmatning, vänligen ange pris i kronor \n" +
                        "Om det är decimaler i priset måste detta anges med deicmal-komma...\n" +
                        "Ange pris: ");
                isUserInputInvalid = true;
            }

        } while (isUserInputInvalid);

        return userInput;
    }
    private static void addProduct() {
        String productGroup = setProductGroup();

        String productName = setProductName();

        System.out.println("Ange om produkten säljs kilovis eller styckvis\n1 - Kilopris\n2 - Styckvis");

        int userInput = getValidIntegerInput(input, 1, 2);

        boolean unitPriceByWeight = userInput == 1;

        System.out.println("Lägg till pris på produkten:");

        double productPrice = getValidDoubleInput(input, 0);

        Product newProduct = new Product(productName, productGroup, productPrice, unitPriceByWeight);
        if (productGroup.equals(productGroupArray[0])) {
            allFruits.add(newProduct);
        } else if (productGroup.equals(productGroupArray[1])) {
            allVegetables.add(newProduct);
        } else if (productGroup.equals(productGroupArray[2])) {
            allRootVegetables.add(newProduct);
        } else if (productGroup.equals(productGroupArray[3])) {
            allMushrooms.add(newProduct);
        } else if (productGroup.equals(productGroupArray[4])) {
            allUnassignedProductGroup.add(newProduct);
        }
        System.out.println("\nProdukten: '" + newProduct.getName() + "' har skapats\n");
    }
    private static String setProductName() {
        String productName;
        do {
            System.out.println("Ange namn på produkten: ");
            productName = input.nextLine();
            if (productName.isEmpty()) {
                System.out.println("vänligen fyll i ett giltigt namn");
            }
        } while (productName.isEmpty());
        return productName;
    }
    private static String setProductGroup() {
        int categoryChoice;
        String productGroup = "";

        System.out.println("Ange vilken kategori produkten ska tillhöra");
        System.out.println("1 - " + productGroupArray[0] +
                "\n2 - " + productGroupArray[1] +
                "\n3 - " + productGroupArray[2] +
                "\n4 - " + productGroupArray[3] +
                "\n5 - " + productGroupArray[4]);

        categoryChoice = getValidIntegerInput(input, 1, 5);

        switch (categoryChoice) {
            case 1 -> productGroup = productGroupArray[0];
            case 2 -> productGroup = productGroupArray[1];
            case 3 -> productGroup = productGroupArray[2];
            case 4 -> productGroup = productGroupArray[3];
            case 5 -> productGroup = productGroupArray[4];
        }
        return productGroup;
    }
    private static void searchMenu() {

        int userChoice;

        do {
            System.out.println("Vill du söka efter kategori eller fritextsöka?" +
                    "\n1 - Söka efter fritext\n2 - Söka efter kategori\n" +
                    "3 - Söka efter produkt ID\n4 - Åter till huvudmenyn");

            userChoice = getValidIntegerInput(input, 1, 4);

            switch (userChoice) {
                case 1 -> freeTextSearch();
                case 2 -> searchByCategory();
                case 3 -> searchById();
            }

        } while (userChoice != 4);
    }
    private static void freeTextSearch() {
        List<Product> searchResult = new ArrayList<>();

        System.out.println("Skriv in namnet på produkten du söker\nNamn:");

        String uniqueProduct = input.nextLine();

        for (ArrayList<Product> productGroup : allProducts) {
            for (Product product : productGroup) {
                if (product.getName().toLowerCase().contains(uniqueProduct.toLowerCase())) {
                    searchResult.add(product);
                }
            }
        }
        if (searchResult.isEmpty()) {
            System.out.println("Det produktnamnet finns inte...\n");

        } else {
            for (Product p : searchResult) {
                System.out.println(p);
            }
        }

    }
    private static Product searchById() {
        int userInput;
        Product foundProduct = null;

        System.out.println("ange produktID (PLU) på produkten:");
        while (true) {

            userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);

            for (ArrayList<Product> productGroup : allProducts) {
                for (Product product : productGroup) {
                    if (product.getProductId() == userInput) {
                        foundProduct = product;
                        break;
                    }
                }
                if (foundProduct != null) {
                    System.out.println(foundProduct);
                    return foundProduct;
                }
            }
            System.out.println("Ingen produkt med det ID\n");
            return null;
        }
    }
    private static void searchByCategory() {
        int userChoice;

        do {
            System.out.println("vilka produkter vill du visa?");
            System.out.println("1 - " + productGroupArray[0] +
                    "\n2 - " + productGroupArray[1] +
                    "\n3 - " + productGroupArray[2] +
                    "\n4 - " + productGroupArray[3] +
                    "\n5 - " + productGroupArray[4] +
                    "\n6 - " + "Alla kategorier" +
                    "\n7 - Åter till sökmenyn");

            userChoice = getValidIntegerInput(input, 1, 7);

            switch (userChoice) {
                case 1 -> printArrayList(allFruits);
                case 2 -> printArrayList(allVegetables);
                case 3 -> printArrayList(allRootVegetables);
                case 4 -> printArrayList(allMushrooms);
                case 5 -> printArrayList(allUnassignedProductGroup);
                case 6 -> printArrayList(allProducts);
            }
        } while (userChoice != 7);
    }
    public static <T> void printArrayList(ArrayList<T> list) {
        for (T p : list) {
            if (p instanceof ArrayList) {
                printArrayList((ArrayList<Product>) p);
            } else {
                System.out.println(p.toString());
            }
        }
    }
    private static void calculatePriceOfProducts(){

        Product foundProduct = searchById();

        if (foundProduct == null) {
            return;
        }

        System.out.println("Ange antal / mängd av produkten, Ange '0' för att återgå till huvudmenyn");

        double amount = getValidDoubleInput(input, 0);

            double calculatedAmount = (foundProduct.getPrice() * amount);
            if (amount != 0) {
                System.out.println("totalpriset är: " + String.format("%.2f", calculatedAmount) + " SEK\n");
                addToShoppingCart(foundProduct, amount);
            }
    }
    private static void addToShoppingCart(Product foundProduct, double amount) {
        System.out.println("Vill du lägga till följande till kundvagnen?\n" +
                "1 - Ja\n" +
                "2 - Nej");
        int userChoice = getValidIntegerInput(input,1,2);
        if (userChoice==1){
            if (shoppingCart == null){
                shoppingCart = new ShoppingCart(0.0);
            }

            shoppingCart.addProductToCart(foundProduct, amount);
            System.out.println((foundProduct.getName() + " har lagts till till kundvagnen\n"));
        }else {
            System.out.println("Produkten har inte lagts till i kundvagnen.");
        }
    }

    public static void printShoppingCart() {
        if (shoppingCart == null) {
            System.out.println("Det finns ingenting i varukorgen...");
            return;
        }
        productsInCart = shoppingCart.getAllProductsInCart();
        productAmounts = ShoppingCart.getProductAmounts();

        if (productsInCart.isEmpty()) {
            System.out.println("Varukorgen är tom.");
        } else {
            System.out.println("Varukorgsinnehåll:");

            for (int i = 0; i < productsInCart.size(); i++) {
                Product product = productsInCart.get(i);
                double amount = productAmounts.get(i);
                String priceUnit = product.isUnitPriceByWeight() ? "per kilo":"per styck";
                String amountSuffix = product.isUnitPriceByWeight() ? "kg, ":"st, ";
                System.out.println("Produkt: " + product.getName()+ ", " + amount + amountSuffix +
                        "Pris " + priceUnit + ": " + product.getPrice() + " SEK, Total: " + (amount * product.getPrice()));
            }

            System.out.println("\nAntal unika produkter i varukorgen: " + shoppingCart.getTotalItems());
            System.out.println("Totalt pris i varukorgen: " + shoppingCart.getTotalPrice() + " SEK");
            System.out.println("Vill du handla?\n" +
                    "1 - Handla\n" +
                    "2 - Fortsätt handla\n" +
                    "3 - Rensa varukorg");

            int userChoice = getValidIntegerInput(input,1,3);

            switch (userChoice){
                case 1 -> {
                    Receipt receipt = new Receipt();
                    Receipt.generateReceipt(shoppingCart);

                }
                case 2 -> {
                    return;
                }
                case 3 -> {
                    productsInCart.clear();
                    productAmounts.clear();
                }
            }
        }
    }
    private static void editProduct() {
        int userInput = 0;
        Product foundProduct = searchById();
        if (foundProduct == null) {
            return;
        }
        do {
            System.out.println("Vad vill du ändra?\n1 - Produktnamn\n2 - Pris \n3 - produktgrupp\n" +
                    "4 - Prissättningsmodell (vikt/styck)\n" +
                    "5 - Åter till personalmeny\n");

            userInput = getValidIntegerInput(input, 1, 5);

            switch (userInput) {
                case 1 -> {
                    System.out.println("Ange nytt namn på produkten: ");
                    String newName = input.nextLine();
                    foundProduct.setName(newName);
                    System.out.println("nytt namn är: " + newName + "\n");
                }
                case 2 -> {
                    System.out.println("Ange nytt pris på produkten: ");
                    double newPrice = input.nextDouble();
                    foundProduct.setPrice(newPrice);
                    System.out.println("Nytt pris är: " + String.format("%.2f", newPrice) + " SEK\n");
                }
                case 3 -> {
                    foundProduct.setProductGroup(setProductGroup());
                    for (ArrayList<Product> productGroup : allProducts) {
                        for (int i = 0; i < productGroup.size(); i++) {
                            if (productGroup.contains(foundProduct)) {
                                productGroup.remove(foundProduct);
                            }
                        }
                    }
                    String tempFoundProduct = foundProduct.getProductGroup();
                    if (tempFoundProduct.equals(productGroupArray[0])) {
                        allFruits.add(foundProduct);
                        System.out.println("Ny kategori för produkten: " + productGroupArray[0] + "\n");

                    } else if (tempFoundProduct.equals(productGroupArray[1])) {
                        allVegetables.add(foundProduct);
                        System.out.println("Ny kategori för produkten: " + productGroupArray[1] + "\n");

                    } else if (tempFoundProduct.equals(productGroupArray[2])) {
                        allRootVegetables.add(foundProduct);
                        System.out.println("Ny kategori för produkten: " + productGroupArray[2] + "\n");

                    } else if (tempFoundProduct.equals(productGroupArray[3])) {
                        allMushrooms.add(foundProduct);
                        System.out.println("Ny kategori för produkten: " + productGroupArray[3] + "\n");

                    } else if (tempFoundProduct.equals(productGroupArray[4])) {
                        allUnassignedProductGroup.add(foundProduct);
                        System.out.println("Ny kategori för produkten: " + productGroupArray[4] + "\n");

                    }
                }
                case 4 -> {

                    System.out.println("Ska produkten säljas efter vikt eller styckvis?\n" +
                            "1 - Vikt\n2 - Styckvis\n3 - åter till Ändringsmenyn");

                    int userChoice = getValidIntegerInput(input, 1, 3);

                    switch (userChoice) {
                        case 1 -> {
                            foundProduct.setUnitPriceByWeight(true);
                            System.out.println("produkten sälj nu efter vikt.\n");
                        }
                        case 2 -> {
                            foundProduct.setUnitPriceByWeight(false);
                            System.out.println("produkten säljs nu styckvis.\n");
                        }
                        case 3 -> {
                            return;
                        }
                    }
                }
                case 5 -> {
                    return;
                }
            }
        } while (userInput != 5);
    }
    private static void removeProduct() {

        Product foundProduct = searchById();

        assert foundProduct != null;
        System.out.println("Du har valt " + foundProduct.getName() +
                "\nÄr du säker på att du vill ta bort Produkten?\n" +
                "1 - Ta bort vald produkt\n2 - Avbryt och åter till personalmeny");

        int confirmDeletion = getValidIntegerInput(input, 1, 2);

        if (confirmDeletion == 1) {
            System.out.println(foundProduct.getName() + " raderas!");

            for (ArrayList<Product> productGroup : allProducts) {
                for (int i = 0; i < productGroup.size(); i++) {
                    if (productGroup.contains(foundProduct)) {
                        productGroup.remove(foundProduct);
                    }
                }
            }
        } else {
            System.out.println("Avbryter! " + foundProduct.getName() + " raderas EJ!");
        }
    }
    private static void addCampaignOnProducts() {
        int userinput;
        System.out.println("Vad vill du lägga till för slags kampanj?\n" +
                "1 - Kampanjpris i %\n" +
                "2 - Kampanjpris i SEK\n" +
                "3 - Kampanjpris i % på hel kategori\n" +
                "4 - Kampanjpris i SEK på hel kategori\n" +
                "5 - Åter till personalmeny");

        userinput = getValidIntegerInput(input, 1, 5);

        switch (userinput) {
            case 1 -> setCampaignInPercent();
            case 2 -> setCampaignInSek();
            case 3 -> setCampaignInPercentOnProductGroup();
            case 4 -> setCampaignInSekOnProductGroup();

        }
    }
    private static void setCampaignInPercent() {
        Product foundProduct = searchById();
        if (foundProduct == null){
            return;
        }
        System.out.println("Hur många % rabatt vill du lägga till?");
        double campaignInPercent = getValidDoubleInput(input, 0);
        System.out.println("vad ska det vara för vilkor för att rabatten ska gälla?");
        System.out.println("1 - Köp X antal av " + foundProduct.getName() + "\n" +
                "2 - Avbryt och åter...");
        int conditionForCampaign = getValidIntegerInput(input, 1,2);
        if (conditionForCampaign == 1) {
            foundProduct.setCampaignPrice(campaignInPercent);
        }
    }
    private static void setCampaignInSek() {

    }
    private static void setCampaignInPercentOnProductGroup() {

    }
    private static void setCampaignInSekOnProductGroup() {

    }
    private static void printCampaigns() {
        System.out.println("Aktuella kampanjer:");

        for (ArrayList<Product> productGroup : allProducts) {
            for (Product product : productGroup) {
                if (product.getProductCampaign() != null) {
                    double originalPrice = product.getPrice();
                    double discountedPrice = product.getCampaignPrice();
                    String campaignCondition = product.getProductCampaign().getCampaignCondition();

                    System.out.println("Produkt: " + product.getName() + ", Ordinarie pris: " + originalPrice + " SEK "+
                            "Kampanjpris: " + discountedPrice + " SEK " + "Villkor: " + campaignCondition);
                }
            }
        }
    }
}

