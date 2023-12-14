package account;

import java.util.UUID;

import account.constants.AccountType;

public class Account {
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

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

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
