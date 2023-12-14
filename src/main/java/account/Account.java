package account;

import java.util.UUID;

import account.constants.AccountType;

public class Account {
    
    // figure out how to add interest rates interest rates
    private String accountId;
    private int userId;
    private double balance;
    private final AccountType accountType;
    private double interestRate;

    public Account(int userId, AccountType accountType) {
        this.accountId = UUID.randomUUID().toString();
        this.userId = userId;
        this.balance = 0.00;
        this.accountType = accountType;
        this.interestRate = setInterestRate(accountType);
    }

    public Account(Account source) {
        this.accountId = source.accountId;
        this.userId = source.userId;
        this.balance = source.balance;
        this.accountType = source.accountType;
        this.interestRate = source.interestRate;
    }

    // for creating credit accounts where account type should be fixed
    protected Account(int userId) {
        this.accountId = UUID.randomUUID().toString();
        this.userId = userId;
        this.balance = 0.00;
        this.accountType = AccountType.CREDIT;
        this.interestRate = setInterestRate(accountType);
    }

    protected String getAccountId() {
        return accountId;
    }

    protected int getUserId() {
        return userId;
    }

    protected void setUserId(int userId) {
        this.userId = userId;
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

    protected double setInterestRate(AccountType accountType) {
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
