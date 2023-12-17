package transaction;

public interface TransactionServices {

    void createTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(int transactionId);
    void displayTransaction();


}
