package transaction;

public interface TransactionServices {

    void createTransaction(Transaction transaction);
    void updateTransaction(int transactionId);
    void displayTransaction(int accountId);
    Transaction findTransactionById(int id);


}
