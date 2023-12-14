package account;

import account.constants.TransactionResponse;

public class CreditAccount extends Account{

    // might be done, maybe not

    private double minimumPayment;
    private double creditLimit;

    protected CreditAccount(int userId, double creditLimit) {
        super(userId);
        this.minimumPayment = calculateMinimumPayment(getBalance());
        this.creditLimit = creditLimit;
    }

    protected double calculateMinimumPayment(double balance) {
        double minimumPayment = 0.00;
        if (balance >= 500.00) {
            minimumPayment = balance * .1;
        } else {
            minimumPayment = 50.00;
        }
        return minimumPayment;
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

    // these 'do nothing'.  Use makePayment and Charge to manipulate the account balance
    @Override
    protected double deposit(double amount) {
        return getBalance();
    }

    @Override
    protected double withdraw(double amount) {
        return getBalance();
    }

    protected double makePayment(double amount) {
        if (amount >= minimumPayment) {
            super.withdraw(amount);
            return getBalance();
        } else {
            System.out.println("Your payment is below the minimum payment.");
            return getBalance();
        }
    }

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
            return super.deposit(amount);
        } else {
            System.out.println(TransactionResponse.DECLINED);
            return getBalance();
        }
    }
    
}
