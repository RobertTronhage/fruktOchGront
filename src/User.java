//Robert Tronhage. robert.tronhage@iths.se

import java.io.*;
import java.util.Scanner;

public class User {
    private int userId = 1;
    private String userName = "";
    private String password;
    private boolean isUserAdmin;
    private boolean isUserActive;

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

    public boolean isUserAdmin() {
        return isUserAdmin;
    }

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

    private static int generateUserId() {
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

    public void addNewUserToFile() {
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

    public void updateUserToFile() {
        try {
            File file = new File("users.txt");
            File tempFile = new File("temp_users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp_users.txt"));



            String userLine;
            boolean isUpdated = false;

            while ((userLine = reader.readLine()) != null) {
                String[] splitUserString = userLine.split(":");
                int userId = Integer.parseInt(splitUserString[0]);

                if (getUserId() == userId) {
                    userLine = getUserId() + ":" + userName + ":" + password + ":" +
                            (isUserActive ? "1" : "0") + ":" + (isUserAdmin ? "1" : "0");
                    isUpdated = true;
                }
                writer.write(userLine + "\n");
            }

            reader.close();
            writer.close();

//            if (isUpdated){
//                replaceUserFile();
//            }

            if (file.delete()) {
                System.out.println("Deleted the file: " + file.getName());
            } else {
                System.out.println("HE GÅR IT!!!!!");
            }

            if (isUpdated) {
                if (new File("temp_users.txt").renameTo(file)) {
                    System.out.println("Updated the file: " + file.getName());
                } else {
                    System.out.println("jahsbdjahsbdjahbsd.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

