import database.DatabaseConnection;
import user.User;
import user.UserService;
import user.UserServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        UserService userService = new UserServiceImpl();
        Scanner scanner = new Scanner(System.in);
        int input;
        try {
            if(DatabaseConnection.getConnection().isValid(30)){
                System.out.println("DATABASE CONNECTED");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        while (true) {
            if(user.getUsername() == null){
                System.out.println("""
                Welcome to the Binary Banking System
                Here are the following options
                1) Login
                2) Create User Credentials
                3) Exit
                """);
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                        scanner.nextLine();
                        System.out.println("Please input your username:");
                        String username = scanner.nextLine();
                        System.out.println("Please input your password:");
                        String password = scanner.nextLine();
                        user = userService.login(username, password, user);
                        break;
                    case 2:
                        scanner.nextLine();
                        System.out.println("Please input a username:");
                        String newUsername = scanner.nextLine();
                        System.out.println("Please input a password:");
                        String newPassword = scanner.nextLine();
                        user = userService.saveUser(new User(newUsername, newPassword));
                        break;
                    case 3:
                        scanner.close();
                        System.out.println("EXITING SYSTEM, GOODBYE");
                        System.exit(0);
                }
            } else {
                System.out.println("""
                Welcome to the Binary Banking System
                Here are the following options
                1) Display User Information
                2) Update Password
                3) Delete User
                4) Logout
                5) Exit
                """);
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                        scanner.nextLine();
                        System.out.println(userService.getUserById(user.getUserId()));
                        break;
                    case 2:
                        scanner.nextLine();
                        User updateInfo = new User();
                        System.out.println("Please enter a new password:");
                        updateInfo.setPassword(scanner.nextLine());
                        userService.updateUser(user.getUserId(), updateInfo);
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("""
                                Are you sure you want to delete the 
                                1) Yes
                                2) No
                                """);
                        input = scanner.nextInt();
                        switch (input){
                            case 1:
                                System.out.println("DELETING ACCOUNT");
                                userService.deleteUserById(user.getUserId());
                                user = new User();
                                break;
                            case 2:
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("LOGGING OUT, GOOD BYE " + user.getUsername());
                        user = userService.logout();
                        break;
                    case 5:
                        scanner.close();
                        System.out.println("EXITING SYSTEM, GOODBYE");
                        System.exit(0);
                }
            }
        }
    }
}
