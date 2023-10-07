//Robert Tronhage, robert.tronhage@iths.se


//Lägg till felhantering för alla metoder


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
        Product p1 = new Product("aaaaaaaaaa", "Rotfrukt", 44, true);
        Product p2 = new Product("bbbbbbbb", "Frukt", 44, false);
        Product p3 = new Product("cccccccc", "Rotfrukt", 44, true);
        Product p4 = new Product("ddddddddd", "Svamp", 44, false);
        Product p5 = new Product("eeeeeeee", "Rotfrukt", 44, true);
        Product p6 = new Product("ffffff", "Ingen kategori", 444, false);
        Product p7 = new Product("gggggggg", "Ingen kategori", 144, true);
        Product p8 = new Product("hhhhhhhhhh", "Grönsaker", 4, true);
        Product p9 = new Product("iiiiiiii", "Grönsaker", 44, true);

        allRootVegetables.add(p1);
        allFruits.add(p2);
        allRootVegetables.add(p3);
        allMushrooms.add(p4);
        allRootVegetables.add(p5);
        allUnassignedProductGroup.add(p6);
        allUnassignedProductGroup.add(p7);
        allVegetables.add(p8);
        allVegetables.add(p9);

        allProducts.add(allFruits);
        allProducts.add(allVegetables);
        allProducts.add(allRootVegetables);
        allProducts.add(allMushrooms);
        allProducts.add(allUnassignedProductGroup);

        System.out.println("Välkommen till Supervåg 3000");
        int menuOption = 6;
        boolean isUserInputInvalid = false;
        do {
            System.out.println("============================\n"
                    + "Välj ett alternativ nedan\n"
                    + "1 - Sök efter specifik produkt\n"
                    + "2 - Lägg till ny produkt\n"
                    + "3 - Ändra en produkt\n"
                    + "4 - Ta bort en produkt\n"
                    + "5 - Pris på produkter\n"
                    + "6 - Avsluta program");

            try {
                menuOption = input.nextInt();
                input.nextLine();
                if (menuOption < 1 || menuOption > 6) {
                    System.out.println("Felaktig inmatning, vänligen ange nummer 1-6...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                }
                switch (menuOption) {
                    case 1 -> searchMenu();
                    case 2 -> addProduct();
                    case 3 -> editProduct();
                    case 4 -> removeProduct();
                    case 5 -> calculatePriceOfProducts();

                }
            } catch (Exception e) {
                input.nextLine();
                System.out.println("vänligen välj ett alternativ mellan 1-6...");
                isUserInputInvalid = true;
            }
        } while (menuOption != 6 || isUserInputInvalid);
        System.out.println("Tack för att du använder supervåg 3000,\n hejdååå! :)");
    }

    private static void addProduct() {
        boolean unitPriceByWeight = true;
        double productPrice = 0;
        String productName;
        String productGroup = "";
        boolean isUserInputInvalid = false;

        productGroup = setProductGroup();

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
                input.nextLine(); // consumes rest of line...

                if (productPrice < 0) {
                    System.out.println("felaktig inmatning, vänligen ange pris i kronor...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                }
            } catch (Exception e) {
                input.nextLine(); // consume rest of line productPrice...
                System.out.println("felaktig inmatning, vänligen ange pris i kronor \n" +
                        "Om det är decimaler i priset måste detta anges med deicmal-komma...");
                isUserInputInvalid = true;
            }


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
                input.nextLine();//consumes rest of userInput...
                System.out.println("felaktig inmatning, vänligen ange nummer 1 eller 2...");
                isUserInputInvalid = true;
            }

        } while (isUserInputInvalid);
        Product newProduct = new Product(productName, productGroup, productPrice, unitPriceByWeight);
        //switch behövde konstanta cases, kan inte lägga productgrouparray[], därav if satser...
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
    }

    private static String setProductGroup() {
        int categoryChoice = 0;
        String productGroup = "";
        boolean isUserInputInvalid;

        do {
            System.out.println("Ange vilken kategori produkten ska tillhöra");
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
        return productGroup;
    }

    private static void searchMenu() {

        int userChoice = 0;
        boolean isUserInputInvalid = false;
        do {
            System.out.println("Vill du söka efter kategori eller fritextsöka?" +
                    "\n1 - Söka efter fritext\n2 - Söka efter kategori\n" +
                    "3 - Söka efter produkt ID\n4 - Åter till huvudmenyn");
            try {
                userChoice = input.nextInt();
                input.nextLine(); //consumes rest of line
                if (userChoice < 1 || userChoice > 4) {
                    System.out.println("Felaktig inmatning, vänligen ange nummer 1-4...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                }
                switch (userChoice) {
                    case 1 -> freeTextSearch();
                    case 2 -> printProductsByCategory();
                    case 3 -> searchById();
                    case 4 -> {
                        break;
                    } //to be super clear...
                }

            } catch (Exception e) {
                input.nextLine(); //consumes rest of line of userChoice
                System.out.println("Här gick det snett, vänligen välj alternativ 1 - 4...");
                isUserInputInvalid = true;
            }
        } while (userChoice != 4 || isUserInputInvalid);
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

        System.out.println("ange produktID:");
        while (true) {
            try {
                userInput = input.nextInt();
                input.nextLine();//consumes rest of line

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
                System.out.println("Ingen produkt med det ID");
                return null;

            } catch (Exception e) {
                input.nextLine();//consumes rest of line
                System.out.println("felaktig inmatning\nange produktID:");
            }
        }
    }

    private static void printProductsByCategory() {
        int userChoice = 7;
        boolean isUserInputInvalid = false;

        do {
            System.out.println("vilka produkter vill du visa?");
            System.out.println("1 - " + productGroupArray[0] +
                    "\n2 - " + productGroupArray[1] +
                    "\n3 - " + productGroupArray[2] +
                    "\n4 - " + productGroupArray[3] +
                    "\n5 - " + productGroupArray[4] +
                    "\n6 - " + "Alla kategorier" +
                    "\n7 - Åter till sökmenyn");
            try {
                userChoice = input.nextInt();
                input.nextLine();

                if (userChoice < 1 || userChoice > 7) {
                    System.out.println("felaktig inmatning, vänligen välj ett alternativ mellan 1-7...");
                    isUserInputInvalid = true;
                }

                switch (userChoice) {
                    case 1 -> {
                        for (Product allFruit : allFruits) {
                            System.out.print(allFruit.toString());
                        }
                    }
                    case 2 -> {
                        for (Product allVegetables : allVegetables) {
                            System.out.println(allVegetables.toString());
                        }
                    }
                    case 3 -> {
                        for (Product allRootVegetables : allRootVegetables) {
                            System.out.println(allRootVegetables.toString());
                        }
                    }
                    case 4 -> {
                        for (Product allMushrooms : allMushrooms) {
                            System.out.println(allMushrooms.toString());
                        }
                    }
                    case 5 -> {
                        for (Product allUnassigned : allUnassignedProductGroup) {
                            System.out.print(allUnassigned.toString());
                        }
                    }
                    case 6 -> {
                        for (ArrayList<Product> productGroup : allProducts) {
                            for (Product product : productGroup) {
                                System.out.println(product.toString());
                            }
                        }
                    }

                }
            } catch (Exception e) {
                input.nextLine();//consumes rest of Line of userChoice
                System.out.println("felaktig inmatning, vänligen välj ett alternativ mellan 1-7...");
                isUserInputInvalid=true;
            }

        } while (userChoice != 7 || isUserInputInvalid);
    }

    private static void calculatePriceOfProducts() {
        boolean isUserInputInvalid = false;
        Product foundProduct = searchById();
        do {
            System.out.println("Ange antal / mängd av produkten, Ange '0' för att återgå till huvudmenyn");
            try{

            double amount = input.nextDouble();

            double calculatedAmount = (foundProduct.getPrice() * amount);
            if (amount == 0) {
                return;
            } else {
                System.out.println("totalpriset är: " + String.format("%.2f", calculatedAmount) + " SEK\n");
                isUserInputInvalid=false;
            }
            }catch(Exception e){
                input.nextLine();
                System.out.println("felaktig inmatning, vänligen ange pris i kronor \n" +
                        "Om det är decimaler i antalet måste detta anges med deicmal-komma...\n");
                isUserInputInvalid = true;
            }
        }while(isUserInputInvalid);
    }

    private static void editProduct() {
        boolean isUserInputInvalid = false;
        int userInput = 0;
        Product foundProduct = searchById();
        do {

            System.out.println("Vad vill du ändra?\n1 - Produktnamn\n2 - Pris \n3- produktgrupp\n" +
                    "4 - Prissättningsmodell (vikt/styck)\n" +
                    "5 - Åter till huvudmenyn");
            try {
                userInput = input.nextInt();
                input.nextLine();
                if (userInput < 1 || userInput > 6) {
                    System.out.println("felaktig inmatning, vänligen ange nummer 1 eller 5...");
                    isUserInputInvalid = true;
                } else {
                    isUserInputInvalid = false;
                }
            } catch (Exception e) {
                input.nextLine();//consumes rest of userInput
                System.out.println("Oj där gick något snett, ange alternativ 1-5...");
                isUserInputInvalid = true;
            }


            switch (userInput) {
                case 1 -> {
                    System.out.println("Ange nytt namn på produkten: ");
                    String newName = input.nextLine();
                    foundProduct.setName(newName);
                    System.out.println("nytt namn är: " + newName);
                }
                case 2 -> {
                    System.out.println("Ange nytt pris på produkten: ");
                    double newPrice = input.nextDouble();
                    foundProduct.setPrice(newPrice);
                    System.out.println("Nytt pris är: " + String.format("%.2f", newPrice) + " SEK");
                }
                case 3 -> {
                    foundProduct.setProductGroup(setProductGroup());
                    for (ArrayList<Product> productGroup : allProducts) {
                        for (int i = 0; i < productGroup.size(); i++) {
                            if (productGroup.contains(foundProduct)) {
                                productGroup.remove(foundProduct);
                                //switch behövde konstanta cases, kan inte lägga productgrouparray[], därav if satser...
                                String tempFoundProduct = foundProduct.getProductGroup();
                                if (tempFoundProduct.equals(productGroupArray[0])) {
                                    allFruits.add(foundProduct);
                                    System.out.println("Ny kategori för produkten: " + productGroupArray[0]);
                                    break;
                                } else if (tempFoundProduct.equals(productGroupArray[1])) {
                                    allVegetables.add(foundProduct);
                                    System.out.println("Ny kategori för produkten: " + productGroupArray[1]);
                                    break;
                                } else if (tempFoundProduct.equals(productGroupArray[2])) {
                                    allRootVegetables.add(foundProduct);
                                    System.out.println("Ny kategori för produkten: " + productGroupArray[2]);
                                    break;
                                } else if (tempFoundProduct.equals(productGroupArray[3])) {
                                    allMushrooms.add(foundProduct);
                                    System.out.println("Ny kategori för produkten: " + productGroupArray[3]);
                                    break;
                                } else if (tempFoundProduct.equals(productGroupArray[4])) {
                                    allUnassignedProductGroup.add(foundProduct);
                                    System.out.println("Ny kategori för produkten: " + productGroupArray[4]);
                                    break;
                                }
                            }
                        }
                    }
                }
                case 4 -> {
                    do {
                        System.out.println("Ska produkten säljas efter vikt eller styckvis?\n" +
                                "1 - Vikt\n2 - Styckvis\n3 - åter till Ändringsmenyn");
                        try {
                            int userChoice = input.nextInt();
                            input.nextLine();

                            if (userInput < 1 || userInput > 3) {
                                System.out.println("felaktig inmatning, vänligen ange nummer 1 eller 3...");
                                isUserInputInvalid = true;
                            } else {
                                isUserInputInvalid = false;
                            }

                            switch (userChoice) {
                                case 1 -> {
                                    foundProduct.setUnitPriceByWeight(true);
                                    System.out.println("produkten sälj nu efter vikt.");
                                    isUserInputInvalid = false;
                                }
                                case 2 -> {
                                    foundProduct.setUnitPriceByWeight(false);
                                    System.out.println("produkten säljs nu styckvis.");
                                    isUserInputInvalid = false;
                                }
                                case 3 -> {
                                    isUserInputInvalid = false;
                                }
                            }

                        } catch (Exception e) {
                            input.nextLine();//consumes rest of userChoice...
                            System.out.println("felaktig inmatning, välj alternativ 1-3");
                            isUserInputInvalid = true;
                        }
                    } while (isUserInputInvalid);
                }
                case 5 -> {
                    return;
                }
            }
        } while (isUserInputInvalid || userInput != 5);
    }

    private static void removeProduct() {
        int userInput = 0;
        boolean isUserInputInvalid = false;

        Product foundProduct = searchById();
        do {
            System.out.println("Du har valt " + foundProduct.getName() +
                    "\n Är du säker på att du vill ta bort Produkten?\n" +
                    "1 - Ta bort vald produkt\n2 - Avbryt och åter till huvudmenyn");
            try {

                int confirmDeletion = input.nextInt();
                input.nextLine();
                if (confirmDeletion < 1 || confirmDeletion > 2) {
                    System.out.println("felaktig inmatning, vänligen ange nummer 1 eller 2...");
                    isUserInputInvalid = true;
                }

                if (confirmDeletion == 1) {
                    System.out.println(foundProduct.getName() + " raderas!");

                    for (ArrayList<Product> productGroup : allProducts) {
                        for (int i = 0; i < productGroup.size(); i++) {
                            if (productGroup.contains(foundProduct)) {
                                productGroup.remove(foundProduct);
                            } else {
                                System.out.println("Avbryter!" + foundProduct.getName() + " raderas EJ!");
                                isUserInputInvalid = false;
                                break;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                input.nextLine();
                System.out.println("vrobliem");
                isUserInputInvalid = true;

            }
        } while (isUserInputInvalid);

    }
}