package account;

import transaction.constants.TransactionResponse;

public class CreditAccount extends Account{

    private double minimumPayment;
    private double creditLimit;

    public CreditAccount(int userId) {
        super(userId);
        this.minimumPayment = 0;
        this.creditLimit = 1500.00;
    }

    // copy constuctor
    public CreditAccount(CreditAccount source) {
        super(source);
        this.minimumPayment = source.minimumPayment;
        this.creditLimit = source.creditLimit;
    }

    // to catch attempts to use the account object to make a credit account
    public CreditAccount(Account source) {
        super(source);
        this.minimumPayment = 0.00;
        this.creditLimit = 1500.00;
    }

    protected void setMinimumPayment(double balance) {
        if (balance < 500.00) {
            minimumPayment =  balance * .1;
        } else {
            minimumPayment = 50.00;
        }
    }

    protected double getMinimumPayment() {
        return minimumPayment;
    }

    protected void setCreditLimit(double amount) {
        this.creditLimit = amount;
    }

    protected double getCreditLimit() {
        return creditLimit;
    }

    // these 'do nothing'.  The program should be prevented from using these on a credit account
    @Override
    protected double deposit(double amount) {
        return getBalance();
    }

    @Override
    protected double withdraw(double amount) {
        return getBalance();
    }

    // adjust the balance by making payments and charges:
    protected double makePayment(double amount) {
        if (amount >= minimumPayment && amount <= getBalance()) {
            super.withdraw(amount);
            setMinimumPayment(getBalance());
            return getBalance();
        } else if (amount > getBalance()) {
            System.out.println("You cannot overpay your balance of $" + String.format("%.2f", getBalance()));
            return getBalance();
        } else {
            System.out.println("Your payment is below you minimum payment of $" + String.format("%.2f", getMinimumPayment()));
            return getBalance();
        }
    }

    // in case it's useful for transactions to get the response code
    protected TransactionResponse acceptOrDeclineCharge(double amount) {
        if (creditLimit - getBalance() > amount) {
            return TransactionResponse.ACCEPTED;
        } else {
            return TransactionResponse.DECLINED;
        }
    }

    protected CreditTransaction charge(double amount) {
        if (creditLimit - getBalance() > amount) {
            System.out.println(TransactionResponse.ACCEPTED);
            super.deposit(amount);
            setMinimumPayment(getBalance() + amount);
            return new CreditTransaction(TransactionResponse.ACCEPTED, getBalance());
        } else {
            System.out.println(TransactionResponse.DECLINED);
            return new CreditTransaction(TransactionResponse.DECLINED, getBalance());
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Credit Limit: " + getCreditLimit() + " Minimum Payment: " + getMinimumPayment();
    }
    
}
