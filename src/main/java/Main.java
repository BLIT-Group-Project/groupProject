import account.Account;
import account.AccountService;
import account.AccountServiceImpl;
import account.CreditAccount;
import account.constants.AccountType;
import database.DatabaseConnection;
import transaction.Transaction;
import transaction.TransactionServiceImpl;
import transaction.TransactionServices;
import transaction.constants.TransactionResponse;
import transaction.constants.TransactionType;
import user.User;
import user.UserService;
import user.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        UserService userService = new UserServiceImpl();
        AccountService accountService = new AccountServiceImpl();
        TransactionServices transactionServices = new TransactionServiceImpl();
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
        boolean exit = true;
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
                    try {
                        scanner.nextLine();
                        System.out.println("Please input your username:");
                        String username = scanner.nextLine();
                        System.out.println("Please input your password:");
                        String password = scanner.nextLine();
                        user = userService.login(username, password, user);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                        break;
                    case 2:
                    try {
                        scanner.nextLine();
                        System.out.println("Please input a username:");
                        String newUsername = scanner.nextLine();
                        System.out.println("Please input a password:");
                        String newPassword = scanner.nextLine();
                        user = userService.saveUser(new User(newUsername, newPassword));
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
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
                5) Create Account
                6) Display Owned Accounts
                7) Delete Account By Id
                8) Create Transaction
                9) Display Transaction by Account Id
                10) Display Transaction by Id
                11) Exit
                """);
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                        try {
                            scanner.nextLine();
                            System.out.println(userService.getUserById(user.getUserId()));
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                    try {
                        scanner.nextLine();
                        User updateInfo = new User();
                        System.out.println("Please enter a new password:");
                        updateInfo.setPassword(scanner.nextLine());
                        userService.updateUser(user.getUserId(), updateInfo);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                        break;
                    case 3:
                    try {
                        scanner.nextLine();
                        System.out.println("""
                                Are you sure you want to delete the 
                                1) Yes
                                2) No
                                """);
                        input = scanner.nextInt();
                        switch (input) {
                            case 1:
                                System.out.println("DELETING ACCOUNT");
                                userService.deleteUserById(user.getUserId());
                                user = new User();
                                break;
                            case 2:
                                break;
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                        break;
                    case 4:
                        System.out.println("LOGGING OUT, GOOD BYE " + user.getUsername());
                        user = userService.logout();
                        break;
                    case 5:
                    try {

                        exit = true;
                        while (exit) {
                            System.out.println("""
                                    Please Select the type of Account you want to make.
                                    1) Checking
                                    2) Saving
                                    3) Credit
                                    """);
                            input = scanner.nextInt();
                            switch (input) {
                                case 1:
                                    System.out.println("CREATING CHECKING ACCOUNT");
                                    Account checking = new Account(user.getUserId(), AccountType.CHECKING);
                                    accountService.createAccount(checking);
                                    exit = false;
                                    System.out.println("CHECKING ACCOUNT SUCCESSFUL");
                                    break;
                                case 2:
                                    System.out.println("CREATING SAVING ACCOUNT");
                                    Account saving = new Account(user.getUserId(), AccountType.SAVINGS);
                                    accountService.createAccount(saving);
                                    exit = false;
                                    System.out.println("SAVING ACCOUNT SUCCESSFUL");
                                    break;
                                case 3:
                                    System.out.println("CREATING CREDIT ACCOUNT");
                                    CreditAccount creditAccount = new CreditAccount(user.getUserId());
                                    accountService.createAccount(creditAccount);
                                    exit = false;
                                    System.out.println("CREDIT ACCOUNT SUCCESSFUL");
                                    break;
                                default:
                                    System.out.println("INPUT INCORRECT PLEASE TRY AGAIN");
                                    break;
                            }
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                        break;
                    case 6:
                        try {
                            List<Account> accounts = accountService.getAllAccountsByUserId(user.getUserId());
                            for (Account account : accounts) {
                                System.out.println(account);
                            }
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            List <Account> accounts = accountService.getAllAccountsByUserId(user.getUserId());
                            for (Account account : accounts) {
                                System.out.println(account);
                            }
                            System.out.println("Which account do you want to delete, Please input id");
                            input = scanner.nextInt();
                            System.out.println("DELETING ACCOUNT WITH ID: " + input);
                            accountService.deleteAccountById(input);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            Transaction transaction = new Transaction();
                            exit = true;
                            while (exit){
                                System.out.println("""
                                What type of transaction to make
                                1) Deposit
                                2) Withdraw
                                3) Charge (Credit)
                                4) Make Payment (Credit)
                                """);
                                input = scanner.nextInt();
                                switch (input) {
                                    case 1:
                                        transaction.setTransactionType(TransactionType.DEPOSIT);
                                        System.out.println("Which Account are you depositing to?");
                                        transaction.setToAccountId(scanner.nextInt());
                                        System.out.println("How much are you depositing");
                                        transaction.setAmount(scanner.nextDouble());
                                        transaction.setStatus(TransactionResponse.PENDING);
                                        System.out.println("SENDING DEPOSIT OVER");
                                        transactionServices.createTransaction(transaction);
                                        System.out.println("TRANSACTION CREATED");
                                        exit = false;
                                        break;
                                    case 2:
                                        transaction.setTransactionType(TransactionType.WITHDRAW);
                                        System.out.println("Which account are you withdrawing from?");
                                        transaction.setFromAccountId(scanner.nextInt());
                                        System.out.println("Which account are you depositing to?");
                                        transaction.setToAccountId(scanner.nextInt());
                                        System.out.println("How much?");
                                        transaction.setAmount(scanner.nextDouble());
                                        transaction.setStatus(TransactionResponse.PENDING);
                                        System.out.println("CREATING TRANSACTION");
                                        transactionServices.createTransaction(transaction);
                                        System.out.println("TRANSACTION CREATED");
                                        exit = false;
                                        break;
                                    case 3:
                                        transaction.setTransactionType(TransactionType.CHARGE);
                                        System.out.println("Which Credit Account do you want to charge");
                                        transaction.setToAccountId(scanner.nextInt());
                                        System.out.println("How much?");
                                        transaction.setAmount(scanner.nextDouble());
                                        transaction.setStatus(TransactionResponse.PENDING);
                                        System.out.println("CREATING TRANSACTION");
                                        transactionServices.createTransaction(transaction);
                                        System.out.println("TRANSACTION CREATED");
                                        exit = false;
                                        break;
                                    case 4:
                                        transaction.setTransactionType(TransactionType.MAKE_PAYMENT);
                                        System.out.println("Which account are you making a payment to?");
                                        transaction.setToAccountId(scanner.nextInt());
                                        System.out.println("How much?");
                                        transaction.setAmount(scanner.nextDouble());
                                        transaction.setStatus(TransactionResponse.PENDING);
                                        System.out.println("CREATING TRANSACTION");
                                        transactionServices.createTransaction(transaction);
                                        System.out.println("TRANSACTION CREATED");
                                        exit = false;
                                        break;
                                    default:
                                        System.out.println("Invalid Choice");
                                        break;
                                }
                            }
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        try {
                            System.out.println("Which Account's Transactions would you like to see");
                            transactionServices.displayTransaction(scanner.nextInt());
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 10:
                        try {
                            System.out.println("Which Transaction would you like to see");
                            System.out.println(transactionServices.findTransactionById(scanner.nextInt()));
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 11:
                        scanner.close();
                        System.out.println("EXITING SYSTEM, GOODBYE");
                        System.exit(0);
                }
            }
        }
    }
}
