//Robert Tronhage. robert.tronhage@iths.se
import java.io.*;
import java.util.Scanner;

public class User {
    private static int userId=1;
    private static String userName="";
    private static String password;
    private static boolean isUserAdmin;
    private static boolean isUserActive;

    //denna konstruktor används för att lägga till ny användare, den är alltid "aktiv"
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

    public int getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public boolean isUserActive() {
        return isUserActive;
    }
    public boolean isUserAdmin() {return isUserAdmin;}
    public String getUserName() {
        return userName;
    }
    public void setUserAdmin(boolean userAdmin) {
        isUserAdmin = userAdmin;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserActive(boolean userActive) {
        isUserActive = userActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isUserAdmin=" + isUserAdmin +
                ", isUserActive=" + isUserActive +
                '}';
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

    public static void addNewUserToFile(User newUser) {
        try {
            String userLine;

            FileWriter fileWriter = new FileWriter("users.txt", true);

            userLine = userId + ":" + userName + ":" + password + ":" + (isUserActive ? "1" : "0") + ":" + (isUserAdmin ? "1" : "0");
            fileWriter.write(userLine + "\n");

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
