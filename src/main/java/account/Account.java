package account;

import java.util.Random;

import account.constants.AccountType;

public class Account {
    
    // figure out how to add interest rates interest rates
    private final int accountId;
    private final int userId;
    private double balance;
    private final AccountType accountType;
    private double interestRate;

    public Account(int userId, AccountType accountType) {
        this.accountId = new Random().nextInt();
        this.userId = userId;
        this.balance = 0.00;
        this.accountType = accountType;
        this.interestRate = setInterestRate(accountType);
    }

    // copy constructor
    public Account(Account source) {
        this.accountId = source.accountId;
        this.userId = source.userId;
        this.balance = source.balance;
        this.accountType = source.accountType;
        this.interestRate = source.interestRate;
    }

    // Only for the CreditAccount class to use as a super constructor
    protected Account(int userId) {
        this.accountId = new Random().nextInt();
        this.userId = userId;
        this.balance = 0.00;
        this.accountType = AccountType.CREDIT;
        this.interestRate = setInterestRate(accountType);
    }

    // just in case it turns out we need this later (This should be addressed in the service layer by saving the account based on the account type field):
    // throw an error if the program attempts to create a credit account without using the CreditAccount constuctor
    // private AccountType forceType(AccountType accountType) {
    //     if (!accountType.toString().matches("(?i)checking|savings")) {
    //         throw new AccountMismatchException();
    //     } else {
    //         return accountType;
    //     }
    // }

    protected int getAccountId() {
        return accountId;
    }

    protected int getUserId() {
        return userId;
    }

    protected double getBalance() {
        return balance;
    }

    protected String getAccountType() {
        return accountType.toString();
    }

    protected double deposit(double amount) {
        return balance += amount;
    }

    protected double withdraw(double amount) {
        if (balance >= amount) {
            return balance -= amount;
        } else {
            System.out.println("Insufficient Funds");
            return balance;
        }
    }

    private double setInterestRate(AccountType accountType) {
        if (accountType == AccountType.CHECKING) {
            return 0.00;
        } else if (accountType == AccountType.SAVINGS) {
            return 0.05;
        } else{
            return .075;
        }
    }

    protected double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "Account ID: " + getAccountId() + ", account type: " + getAccountType() + ", balance: " + getBalance();
    }

}
