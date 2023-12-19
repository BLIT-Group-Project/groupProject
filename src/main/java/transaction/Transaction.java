package transaction;


import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int fromAccountId;
    private int toAccountId;
    private double amount;
    private LocalDateTime timeStamp;

    public Transaction(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Transaction (){
        timeStamp = LocalDateTime.now();

    }
    public int getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Transaction(int transactionId, int fromAccountId, int toAccountId, double amount) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;



    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
