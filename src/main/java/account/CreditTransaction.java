package account;

import transaction.constants.TransactionResponse;

public class CreditTransaction {
    private TransactionResponse transactionResponse;
    private double balance;

    public CreditTransaction(TransactionResponse transactionResponse, double balance) {
        this.transactionResponse = transactionResponse;
        this.balance = balance;
    }

    public CreditTransaction(CreditTransaction source) {
        this.transactionResponse = source.transactionResponse;
        this.balance = source.balance;
    }

    public TransactionResponse getTransactionResponse() {
        return transactionResponse;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return getTransactionResponse() + ", balance: " + getBalance();
    }
}
