//Robert Tronhage, robert.tronhage@iths.se

//Användare som finns:
// Användarnamn: admin Lösenord: password
// Användarnamn: oskar Lösenord: oskar

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    public static ArrayList<Product> allFruits = new ArrayList<>();
    public static ArrayList<Product> allRootVegetables = new ArrayList<>();
    public static ArrayList<Product> allVegetables = new ArrayList<>();
    public static ArrayList<Product> allMushrooms = new ArrayList<>();
    public static ArrayList<Product> allUnassignedProductGroup = new ArrayList<>();
    public static ArrayList<ArrayList<Product>> allProducts = new ArrayList<>();
    public static ArrayList<ProductCampaign> allCampaigns = new ArrayList<>();
    public static String[] productGroupArray = {"Frukt", "Grönsaker", "Rotfrukt", "Svamp", "Ingen kategori"};
    public static int productIdTracker = 1;
    static Scanner input = new Scanner(System.in);
    static String GREEN = "\u001B[32m";
    static String PURPLE = "\u001B[35m";
    static String RESET = "\u001B[0m";
    static User loggedInUser;
    static ShoppingCart shoppingCart;
    public static String currentPath;

    public static void main(String[] args) {

        initFromFile();
        mainMenu();
        System.out.println("Tack för att du använder supervåg 3000,\n hejdååå! :)");
    }

    private static void initFromFile() {
        initCurrentPath();
        Product.initProducts(currentPath);
        PercentCampaign.initCampaigns(currentPath);
        allProducts.add(allFruits);
        allProducts.add(allVegetables);
        allProducts.add(allRootVegetables);
        allProducts.add(allMushrooms);
        allProducts.add(allUnassignedProductGroup);
        PercentCampaign.initCampaignOnProducts(currentPath);
    }

    private static void mainMenu() {

        System.out.println("Välkommen till Supervåg 3000");
        int menuOption;
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

    public static void initCurrentPath() {
        Console console = System.console();
        try {
            currentPath = new File(".").getCanonicalPath();

            if (console != null) {
                currentPath = currentPath.substring(0, currentPath.length() - 4);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void verifyUserCredentials() {
        Console console = System.console();
        String userPassword = "";
        String userName = "";
        File fin;
        if (console == null) {
            System.out.println(PURPLE + "NOTERA ATT OM DU ANVÄNDER EN IDE SYNS DITT LÖSENORD\nFÖR ATT DÖLJA LÖSENORDET, ANVÄND KOMMANDOTOLKEN ELLER ANNAN KONSOL!\n" + RESET);
            System.out.print("Ange användarnamn: ");
            userName = input.nextLine();
            System.out.print("Ange Lösenord: ");
            userPassword = input.nextLine();
            fin = new File(currentPath + "/users.txt");

        } else {
            System.out.print("Ange användarnamn: ");
            userName = input.nextLine();
            System.out.println("notera att du inte kommer se de tecknen du matar in..");
            char[] passwordChars = console.readPassword("Ange lösenord: ");
            userPassword = new String(passwordChars);
            //root is src in console, file is not in src.
            currentPath = currentPath.substring(0, currentPath.length() - 4);
            fin = new File(currentPath + "/users.txt");
        }

        boolean loggedIn = false;

        try (Scanner fileScan = new Scanner(fin)) {
            while (fileScan.hasNextLine()) {
                String userString = fileScan.nextLine();
                String[] splitUserString = userString.split(":");
                boolean active = Integer.parseInt(splitUserString[3]) == 1;
                boolean admin = Integer.parseInt(splitUserString[4]) == 1;
                int userId = Integer.parseInt(splitUserString[0]);

                if (userName.equals(splitUserString[1]) && userPassword.equals(splitUserString[2])) {
                    loggedInUser = new User(userId, splitUserString[1], splitUserString[2], admin, active);
                    if (active) {
                        System.out.print(GREEN);
                        loggedIn = true;
                    }
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("Felaktigt användarnamn eller lösenord\nOm felet kvarstår, kontakta helpdesk: 070-58 26 773");
            } else {
                fileScan.close();
                loggedInUserMenu();
            }
        } catch (FileNotFoundException e) {
            System.out.println("här vart det strul..Hittade inte filen :(");
        }
    }

    private static void loggedInUserMenu() {
        String userName = loggedInUser.getUserName();
        System.out.println("\n\nVälkommen " + userName + ", Så länge du är inloggad är textfärgen grön. Glöm inte att logga ut när du är färdig!");
        int menuOption;
        do {
            System.out.println("============================\n"
                    + "Välj ett alternativ nedan\n"
                    + "1 - Sök efter specifik produkt\n"
                    + "2 - Lägg till ny produkt\n"
                    + "3 - Ändra en produkt\n"
                    + "4 - Ta bort en produkt\n"
                    + "5 - Pris på produkter\n"
                    + "6 - Kampanjer\n"
                    + "7 - Redigera användare\n"
                    + "8 - Logga ut");

            menuOption = getValidIntegerInput(input, 1, 8);

            switch (menuOption) {
                case 1 -> searchMenu();
                case 2 -> addProduct();
                case 3 -> editProduct();
                case 4 -> removeProduct();
                case 5 -> calculatePriceOfProducts();
                case 6 -> campaignsMenu();
                case 7 -> editUsers();
            }
        } while (menuOption != 8);
        System.out.print(RESET);
        loggedInUser = null;
    }

    public static void editUsers() {
        int userChoice;

        if (!loggedInUser.isUserAdmin()) {
            System.out.println("Endast Användare med Admin-behörighet kan komma åt denna tjänst...");
            return;
        }

        do {
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
                case 2 -> editUserAdminPrivilege();
                case 3 -> editUserActive();
                case 4 -> editUserPassword();
                case 5 -> printAllUsers();
            }

        } while (userChoice != 6);
    }

    public static void editUserPassword() {
        User foundUser = findUserById();
        if (foundUser == null) {
            return;
        }

        System.out.println("välj nedan:\n" +
                "1 - Ändra Lösenordet för användaren\n" +
                "2 - Avbryt och åter redigeringsmeny");

        int userChoice = getValidIntegerInput(input, 1, 2);

        if (userChoice == 1) {
            System.out.println("Ange nytt lösenord (notera att du inte kan använda tecknet ':'): ");
            String newUserPassword = getValidStringInput(input);
            foundUser.setPassword(newUserPassword);
            foundUser.updateUserToFile(currentPath);
        } else if (userChoice == 2) {
            System.out.println("Avbryter.. Lösenordet ändras INTE");
        }
    }

    public static User findUserById() {
        System.out.println("Ange användarens ID:");
        int userId = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        boolean admin;
        boolean active;
        String password;
        String userName;
        User foundUser = null;

        File fin = new File("users.txt");
        try (Scanner fileScan = new Scanner(fin)) {
            while (fileScan.hasNextLine()) {
                String userString = fileScan.nextLine();
                String[] splitUserString = userString.split(":");
                int id = Integer.parseInt(splitUserString[0]);

                if (id == userId) {
                    userName = splitUserString[1];
                    password = splitUserString[2];
                    active = Integer.parseInt(splitUserString[3]) == 1;
                    admin = Integer.parseInt(splitUserString[4]) == 1;
                    foundUser = new User(userId, userName, password, active, admin);
                }
            }
            fileScan.close();
            if (foundUser == null) {
                System.out.println("användaren hittades inte");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return foundUser;
    }

    public static void editUserAdminPrivilege() {
        User foundUser = findUserById();

        if (foundUser == null) {
            return;
        }

        System.out.println("välj nedan:\n" +
                "1 - Ge användare Admin-rättigheter\n" +
                "2 - Ta bort användarens Admin-rättigheter\n" +
                "3 - Avbryt och åter redigeringsmeny");

        int userChoice = getValidIntegerInput(input, 1, 3);

        if (userChoice == 1) {
            foundUser.setUserAdmin(true);
            foundUser.updateUserToFile(currentPath);
        } else if (userChoice == 2) {
            foundUser.setUserAdmin(false);
        }


        System.out.println("det funkade! " + foundUser.getUserName() + " har nu " + (foundUser.isUserAdmin() ? "Adminrättigheter" : "INTE Adminrättigheter"));
    }

    public static void editUserActive() {
        User foundUser = findUserById();

        if (foundUser == null) {
            return;
        }

        System.out.println("Välj vad du vill göra nedan:\n" +
                "1 - Aktivera användare\n" +
                "2 - Inaktivera användare\n" +
                "3 - Åter till redigerings-meny");

        int userChoice = getValidIntegerInput(input, 1, 3);

        if (userChoice == 1) {
            foundUser.setUserActive(true);
        } else if (userChoice == 2) {
            foundUser.setUserActive(false);
        }
        foundUser.updateUserToFile(currentPath);

        System.out.println("användaren: " + foundUser.getUserName() + (foundUser.isUserActive() ? " har Aktiverats..." : " har inaktiverats..."));
//
    }

    public static void printAllUsers() {
        File fin = new File("users.txt");
        try (Scanner fileScan = new Scanner(fin)) {
            System.out.println("ID:Name:Password:isActive:isAdmin");
            while (fileScan.hasNextLine()) {
                String UserData = fileScan.nextLine();
                System.out.println(UserData);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ojdå");
        }
    }

    public static void addUser() {

        System.out.println("Ange användarnamn för den nya användaren:");
        String userName = getValidStringInput(input);

        System.out.println("Ange lösenord för den nya användaren:");
        String userPassword = getValidStringInput(input);

        System.out.println("ska den nya användaren ha Admin rättigheter?\n" +
                "1 - Ja\n" +
                "2 - Nej");
        int isUserAdmin = getValidIntegerInput(input, 1, 2);
        boolean isAdmin = isUserAdmin == 1;

        User newUser = new User(userPassword, userName, isAdmin);
        System.out.println("Användare: " + userName + " har skapats");
        System.out.println(newUser);
        newUser.addNewUserToFile(currentPath);

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

    //Åtgärdat från labb 1! Nu kan man ta in både ',' samt '.' som input!
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
                System.out.println("felaktig inmatning, vänligen ange pris i kronor \nAnge pris: ");
                isUserInputInvalid = true;
            }

        } while (isUserInputInvalid);

        return userInput;
    }

    public static String getValidStringInput(Scanner input) {
        String userInput;
        boolean isUserInputInvalid;

        do {
            userInput = input.nextLine();
            if (!userInput.matches("[a-zA-ZåäöÅÄÖ0-9]+")) {
                System.out.println("Felaktig inmatning, du kan inte använda specialtecken!");
                isUserInputInvalid = true;
            } else if (userInput.isEmpty()) {
                System.out.println("du måste ange minst en bokstav..");
                isUserInputInvalid = true;
            } else {
                isUserInputInvalid = false;
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
        assignProductToCorrectProductGroupArrayList(productGroup, newProduct);
        System.out.println("\nProdukten: '" + newProduct.getName() + "' har skapats\n");
        newProduct.saveProductToFile();
    }

    public static void assignProductToCorrectProductGroupArrayList(String productGroup, Product product) {
        if (productGroup.equals(productGroupArray[0])) {
            allFruits.add(product);
        } else if (productGroup.equals(productGroupArray[1])) {
            allVegetables.add(product);
        } else if (productGroup.equals(productGroupArray[2])) {
            allRootVegetables.add(product);
        } else if (productGroup.equals(productGroupArray[3])) {
            allMushrooms.add(product);
        } else if (productGroup.equals(productGroupArray[4])) {
            allUnassignedProductGroup.add(product);
        }
    }

    private static String setProductName() {

        System.out.println("Ange namn på produkten: ");
        String productName = getValidStringInput(input);

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
                case 3 -> {
                    System.out.println("ange produktID (PLU) på produkten:");
                    int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
                    Product foundProduct = searchByProductId(userInput);
                    if (foundProduct != null) {
                        System.out.println(foundProduct);
                    }

                }
            }

        } while (userChoice != 4);
    }

    private static void freeTextSearch() {
        List<Product> searchResult = new ArrayList<>();

        System.out.println("Skriv in namnet på produkten du söker\nNamn:");

        String uniqueProduct = getValidStringInput(input);

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

    public static Product searchByProductId(int productId) {

        Product foundProduct = null;

        while (true) {
            for (ArrayList<Product> productGroup : allProducts) {
                for (Product product : productGroup) {
                    if (product.getProductId() == productId) {
                        foundProduct = product;
                        break;
                    }
                }
                if (foundProduct != null) {

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

    private static void calculatePriceOfProducts() {

        System.out.println("ange produktID (PLU) på produkten:");
        int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        Product foundProduct = searchByProductId(userInput);

        if (foundProduct == null) {
            return;
        } else {
            System.out.println(foundProduct);
        }

        System.out.println("Ange antal / mängd av produkten, Ange '0' för att återgå till huvudmenyn");

        double amountOfProduct = getValidDoubleInput(input, 0);

        double calculatedPrice = (foundProduct.getPrice(amountOfProduct) * amountOfProduct);
        if (amountOfProduct != 0) {
            if (foundProduct.getProductCampaign() != null) {
                foundProduct.checkCampaignCondition(amountOfProduct);
            }
            System.out.println("totalpriset är: " + String.format("%.2f", calculatedPrice) + " SEK\n");
            if (loggedInUser == null) {
                addToShoppingCart(foundProduct, amountOfProduct);
            }
        }
    }

    private static void addToShoppingCart(Product foundProduct, double amount) {
        System.out.println("Vill du lägga till följande till kundvagnen?\n" +
                "1 - Ja\n" +
                "2 - Nej");
        int userChoice = getValidIntegerInput(input, 1, 2);
        if (userChoice == 1) {
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart(0.0);
            }
            boolean updatedProductInCart = false;
            for (int i = 0; i < shoppingCart.getAllProductsInCart().size(); i++) {
                if (foundProduct.getProductId() == shoppingCart.getAllProductsInCart().get(i).getProductId()) {
                    shoppingCart.getProductAmounts().set(i, shoppingCart.getProductAmounts().get(i) + amount);
                    updatedProductInCart = true;
                }
            }
            if (!updatedProductInCart) {
                shoppingCart.addProductToCart(foundProduct, amount);
            }
            System.out.println((foundProduct.getName() + " har lagts till till kundvagnen\n"));
        } else {
            System.out.println("Produkten har inte lagts till i kundvagnen.");
        }
    }

    public static void printShoppingCart() {
        if (shoppingCart == null) {
            System.out.println("Det finns ingenting i varukorgen...");
            return;
        }

        if (shoppingCart.getAllProductsInCart().isEmpty()) {
            System.out.println("Varukorgen är tom.");
        } else {
            System.out.println("Varukorgsinnehåll:");

            for (int i = 0; i < shoppingCart.getAllProductsInCart().size(); i++) {
                Product product = shoppingCart.getAllProductsInCart().get(i);
                double amount = shoppingCart.getProductAmounts().get(i);
                String priceUnit = product.isUnitPriceByWeight() ? "per kilo" : "per styck";
                String amountSuffix = product.isUnitPriceByWeight() ? "kg, " : "st, ";
                System.out.println(i + 1 + " Produkt: " + product.getName() + ", " + amount + amountSuffix +
                        "Pris " + priceUnit + ": " + String.format("%.2f", product.getPrice(amount)) + " SEK, Total: " + String.format("%.2f", (amount * product.getPrice(amount))) + " SEK");
            }

            System.out.println("\nAntal unika produkter i varukorgen: " + shoppingCart.getTotalItems());
            System.out.println("Totalt pris i varukorgen: " + shoppingCart.getTotalPrice() + " SEK");
            System.out.println("Vill du handla?\n" +
                    "1 - Handla\n" +
                    "2 - Fortsätt handla\n" +
                    "3 - Rensa varukorg");

            int userChoice = getValidIntegerInput(input, 1, 3);

            switch (userChoice) {
                case 1 -> {
                    Receipt.generateReceipt(shoppingCart);
                    shoppingCart.getAllProductsInCart().clear();
                    shoppingCart.getProductAmounts().clear();
                }
                case 2 -> {

                }
                case 3 -> {
                    shoppingCart.getAllProductsInCart().clear();
                    shoppingCart.getProductAmounts().clear();
                }
            }
        }
    }

    private static void editProduct() {
        System.out.println("ange produktID (PLU) på produkten:");
        int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        Product foundProduct = searchByProductId(userInput);
        if (foundProduct == null) {
            return;
        } else {
            System.out.println(foundProduct);
        }
        do {
            System.out.println("Vad vill du ändra?\n1 - Produktnamn\n2 - Pris \n3 - produktgrupp\n" +
                    "4 - Prissättningsmodell (vikt/styck)\n" +
                    "5 - Åter till personalmeny\n");

            userInput = getValidIntegerInput(input, 1, 5);

            switch (userInput) {
                case 1 -> {
                    System.out.println("Ange nytt namn på produkten: ");
                    String newName = getValidStringInput(input);
                    foundProduct.setName(newName);
                    System.out.println("nytt namn är: " + newName + "\n");
                    foundProduct.updateProductToFile();

                }
                case 2 -> {
                    System.out.println("Ange nytt pris på produkten: ");
                    double newPrice = getValidDoubleInput(input, 0);
                    foundProduct.setPrice(newPrice);
                    System.out.println("Nytt pris är: " + String.format("%.2f", newPrice) + " SEK\n");
                    foundProduct.updateProductToFile();
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
                    foundProduct.updateProductToFile();
                }
                case 4 -> {

                    System.out.println("Ska produkten säljas efter vikt eller styckvis?\n" +
                            "1 - Vikt\n2 - Styckvis\n3 - åter till Ändringsmenyn");

                    int userChoice = getValidIntegerInput(input, 1, 3);

                    switch (userChoice) {
                        case 1 -> {
                            foundProduct.setUnitPriceByWeight(true);
                            System.out.println("produkten sälj nu efter vikt.\n");
                            foundProduct.updateProductToFile();
                        }
                        case 2 -> {
                            foundProduct.setUnitPriceByWeight(false);
                            System.out.println("produkten säljs nu styckvis.\n");
                            foundProduct.updateProductToFile();
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
        System.out.println("ange produktID (PLU) på produkten:");
        int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        Product foundProduct = searchByProductId(userInput);
        if (foundProduct == null) {
            return;
        } else {
            System.out.println(foundProduct);
        }

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

    private static void campaignsMenu() {
        int userinput;
        do {


            System.out.println("Vad vill du göra?\n" +
                    "1 - Skapa en ny kampanj\n" +
                    "2 - Visa alla Aktiva kampanjer\n" +
                    "3 - Ändra kampanj\n" +
                    "4 - Ändra en kampanj på produkt\n" +
                    "5 - Lägga till kampanj på produkt\n" +
                    "6 - Ta bort kampanj från produkt\n" +
                    "7 - Ta bort en kampanj\n" +
                    "8 - Åter till personalmeny");

            userinput = getValidIntegerInput(input, 1, 8);

            switch (userinput) {
                case 1 -> createNewCampaign();
                case 2 -> printCampaigns();
                case 3 -> editCampaign();
                case 4 -> editCampaignOnProduct();
                case 5 -> setCampaignOnProduct();
                case 6 -> removeCampaignOnProduct();
                case 7 -> removeCampaign();
            }
        } while (userinput != 8);
    }

    private static void printCampaigns() {
        System.out.println("Aktuella kampanjer: ");
        int foundCampaignOnProductsCounter = 0;

        for (ArrayList<Product> productGroup : allProducts) {
            for (Product product : productGroup) {
                if (product.getProductCampaign() != null) {
                    double originalPrice = product.getRegularPrice();
                    double discountedPrice = product.getPrice();
                    int campaignCondition = product.getCampaignCondition();
                    foundCampaignOnProductsCounter++;

                    System.out.println("ProduktID: " + product.getProductId() + ", Produkt: " + product.getName() + ", Ordinarie pris: " + originalPrice + " SEK " +
                            "Kampanjpris: " + String.format("%.2f", discountedPrice) + " SEK " + "Villkor för kampanj: Köp minst " + campaignCondition + (product.isUnitPriceByWeight() ? "kg " : " stycken "));
                }
            }
        }
        if (foundCampaignOnProductsCounter == 0) {
            System.out.println("hittade inga aktuella kampanjer");
        }
    }

    private static void createNewCampaign() {

        System.out.println("vad vill du kalla din kampanj? t ex 'Black Friday REA'\n" +
                "observera att om du döper din kampanj till en kampanj som redan finns så kommer den ersätta den gamla kampanjen..\n" +
                "Ange '0' för att Avbryta och återgå till personalmeny");
        String nameOfCampaign = getValidStringInput(input);
        if (nameOfCampaign.equals("0")) {
            return;
        }
        System.out.println("Hur många % rabatt vill du lägga till?");
        double campaignInPercent = getValidDoubleInput(input, 0);
        if (campaignInPercent>100){
            System.out.println("du kan inte ange mer än 100%, ange rabatt:");
            campaignInPercent = getValidDoubleInput(input,0);
        }
        ProductCampaign percentCampaign = new PercentCampaign(campaignInPercent, nameOfCampaign);
        allCampaigns.add(percentCampaign);
        PercentCampaign.saveCampaignToFile(currentPath, campaignInPercent, nameOfCampaign);
    }

    private static ProductCampaign getProductCampaign(String reasonForSearch) {
        System.out.println(reasonForSearch);
        for (int i = 0; i < allCampaigns.size(); i++) {
            System.out.println(i + 1 + " " + allCampaigns.get(i).getCampaignName());
        }
        int userChoice = getValidIntegerInput(input, 1, Integer.MAX_VALUE);

        ProductCampaign foundCampaign = allCampaigns.get(userChoice - 1);
        return foundCampaign;
    }

    private static void editCampaign() {
        String newCampaignName;
        double newSaleValue = 0;

        ProductCampaign foundCampaign = getProductCampaign("ange vilken Kampanj du vill ändra");

        System.out.println("vad vill du ändra?\n" +
                "1 - Namn på kampanjen\n" +
                "2 - hur mycket rabatt kampanjen ska ha\n" +
                "3 - Avbryt och åter ");

        int userInput = getValidIntegerInput(input, 1, 3);

        if (userInput == 1) {
            System.out.println("ange det nya namnet på kampanjen:");
            newCampaignName = getValidStringInput(input);
            String oldCampaignName = foundCampaign.getCampaignName();
            foundCampaign.setCampaignName(newCampaignName);
            PercentCampaign.updatedCampaignToFile(currentPath, foundCampaign, oldCampaignName);


        } else if (userInput == 2) {
            System.out.println("Ange hur mycket rabatt kampanjen ska ha i % :");
            newSaleValue = getValidDoubleInput(input, 0);
            if (newSaleValue>100){
                System.out.println("du kan inte ange mer än 100%, ange rabatt:");
                newSaleValue = getValidDoubleInput(input,0);
            }
            String oldCampaignName = foundCampaign.getCampaignName();
            foundCampaign.setSalePercent(newSaleValue);
            PercentCampaign.updatedCampaignToFile(currentPath, foundCampaign, oldCampaignName);

        }
    }

    private static void removeCampaign() {
        ProductCampaign foundCampaign = getProductCampaign("ange vilken Kampanj du vill ta bort");
        allCampaigns.remove(foundCampaign);
        PercentCampaign.removeCampaignFile(currentPath, foundCampaign.getCampaignName());
    }

    private static void setCampaignOnProduct() {
        System.out.println("ange produktID (PLU) på produkten:");
        int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        Product foundProduct = searchByProductId(userInput);
        if (foundProduct == null) {
            return;
        } else {
            System.out.println(foundProduct);
        }

        System.out.println("Om produkten redan har en kampanj kommer den kampanjen du väljer här ersätta den.");
        ProductCampaign foundCampaign = getProductCampaign("vilken kampanj vill du ha på produkten?");
        foundProduct.setProductCampaign(foundCampaign);
        System.out.println("Ange hur många enheter (kg/styck) kund måste köpa för att rabatten ska gälla:");
        foundProduct.setcampaignCondition(getValidIntegerInput(input, 1, Integer.MAX_VALUE));
        PercentCampaign.saveCampaignOnProductToFile(currentPath, foundProduct, foundCampaign);

    }

    private static void editCampaignOnProduct() {

        //KampanjNAMN,VILLKOR,PRODUKTID

        printCampaigns();

        System.out.println("ange produktID (PLU) på Kampanjprodukten du vill ta bort:");
        int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        Product foundProduct = searchByProductId(userInput);
        if (foundProduct == null) {
            return;
        }

        System.out.println("Ange hur många enheter (kg/styck) kund måste köpa för att rabatten ska gälla:");
        foundProduct.setcampaignCondition(getValidIntegerInput(input, 1, Integer.MAX_VALUE));
        PercentCampaign.editCampaignOnProductFile(currentPath, foundProduct, foundProduct.getProductCampaign());

    }

    private static void removeCampaignOnProduct() {
        printCampaigns();

        System.out.println("ange produktID (PLU) på produkten:");
        int userInput = getValidIntegerInput(input, 1, Integer.MAX_VALUE);
        Product foundProduct = searchByProductId(userInput);
        if (foundProduct == null) {
            return;
        }

        for (ArrayList<Product> productGroup : allProducts) {
            for (int i = 0; i < productGroup.size(); i++) {
                if (productGroup.contains(foundProduct)) {
                    productGroup.remove(foundProduct);
                }
            }
        }

        Product replaceProduct = new Product(foundProduct.getProductId(), foundProduct.getName(), foundProduct.getProductGroup(), foundProduct.getPrice(), foundProduct.isUnitPriceByWeight());
        assignProductToCorrectProductGroupArrayList(foundProduct.getProductGroup(), replaceProduct);

        PercentCampaign.removeCampaignOnProductFromFile(currentPath, foundProduct);
    }


}

