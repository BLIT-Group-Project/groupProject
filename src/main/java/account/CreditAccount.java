package account;

import account.constants.TransactionResponse;

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
        if (amount > minimumPayment) {
            super.withdraw(amount);
            setMinimumPayment(getBalance());
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

    protected double charge(double amount) {
        if (acceptOrDeclineCharge(amount).equals(TransactionResponse.ACCEPTED)) {
            System.out.println(TransactionResponse.ACCEPTED);
            setMinimumPayment(getBalance() + amount);
            return super.deposit(amount);
        } else {
            System.out.println(TransactionResponse.DECLINED);
            return getBalance();
        }
    }
    
}
