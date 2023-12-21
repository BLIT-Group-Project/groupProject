package transaction;

import java.util.List;

public interface TransactionRepository {
    int insertTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccountId(int accountId);
    Transaction getTransactionById(int transactionId);
    void updateTransaction(Transaction transaction);
}
