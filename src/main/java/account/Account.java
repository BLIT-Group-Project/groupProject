package account;

import java.util.UUID;

import account.constants.AccountType;

public class Account {
    // figure out how to make accountType final without causing exceptions
    private String accountId;
    private int userId;
    private double balance;
    private AccountType accountType;

    public Account(int userId, AccountType accountType) {
        this.accountId = UUID.randomUUID().toString();
        this.userId = userId;
        this.balance = 0.00;
        this.accountType = accountType;
    }

    public Account(Account source) {
        this.accountId = source.accountId;
        this.userId = source.userId;
        this.balance = source.balance;
        this.accountType = source.accountType;
    }

    // for creating credit accounts where account type should be fixed
    protected Account(int userId) {
        this.accountId = UUID.randomUUID().toString();
        this.userId = userId;
        this.balance = 0.00;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType.toString();
    }

    // commented out since you should not be able to change account types
    // public void setAccountType(AccountType accountType) {
    //     this.accountType = accountType;
    // }

    public double deposit(double amount) {
        return balance += amount;
    }

    public double withdraw(double amount) {
        if (balance >= amount) {
            return balance -= amount;
        } else {
            System.out.println("Insufficient Funds");
            return balance;
        }
    }

    @Override
    public String toString() {
        return "Account ID: " + getAccountId() + ", account type: " + getAccountType() + ", balance: " + getBalance();
    }

}
