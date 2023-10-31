import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private final int userId;
    private String userName;
    private String password;
    private boolean isUserAdmin;
    private boolean isUserActive;

    public User(String password, String userName, boolean isUserAdmin) {
        this.password = password;
        this.userName = userName;
        this.isUserAdmin = isUserAdmin;
        this.userId = generateUserId();
        this.isUserActive = true;
    }

    public User(int userId, String userName, String password, boolean isUserAdmin, boolean isUserActive) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.isUserAdmin = isUserAdmin;
        this.isUserActive = isUserActive;
    }

    public String getUserName() {
        return userName;
    }

    private static int generateUserId(){
        int userIdCounter = 1;
        File fin = new File("users.txt");
        try (Scanner fileScan = new Scanner(fin)) {
            while (fileScan.hasNextLine()) {
                fileScan.nextLine();
                userIdCounter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("FEL");
        }
        return userIdCounter;
    }


}
