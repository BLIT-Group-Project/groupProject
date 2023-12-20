package account;

import account.constants.AccountType;

public class Account {
    
    private final int accountId;
    private final int userId;
    private double balance;
    private final AccountType accountType;
    private double interestRate;

    public Account(int userId, AccountType accountType) {
        this.accountId = 0;
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
        this.accountId = 0;
        this.userId = userId;
        this.balance = 0.00;
        this.accountType = AccountType.CREDIT;
        this.interestRate = setInterestRate(accountType);
    }
    
    // Only to get objects out of the database without things getting really weird
    protected Account(int accountId, int userId, AccountType accountType, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.accountType = accountType;
        this.interestRate = setInterestRate(accountType);
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + accountId;
        result = prime * result + userId;
        result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (accountId != other.accountId)
            return false;
        if (userId != other.userId)
            return false;
        if (accountType != other.accountType)
            return false;
        return true;
    }

}
