package transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionServices {

    private static List<Transaction> transactionDb;

    public TransactionServiceImpl() {
        this.transactionDb = new ArrayList<>();
    }


    @Override
    public void createTransaction(Transaction transaction) {
        transactionDb.add(transaction);


    }

    @Override
    public void updateTransaction(Transaction transaction) {
        transaction.setAmount(transaction.getAmount());
        transaction.setTimeStamp(transaction.getTimeStamp());



    }
//        transaction.setTransactionId(transaction.getFromAccountId());
//        transaction.setTimeStamp(transaction.getTimeStamp());



    @Override
    public void deleteTransaction(int transactionId) {
        Transaction transactionToRemove = null;
        for (Transaction transaction : transactionDb) {
            if (transaction.getTransactionId() == transactionId) {
                transactionToRemove = transaction;
                break;
            }
            if (transactionToRemove != null) {
                transactionDb.remove(transactionToRemove);
            }

        }
    }

    @Override
    public void displayTransaction() {
        if(transactionDb.isEmpty()){
            System.out.println("No transaction to display ");
        }else
            for (Transaction transaction : transactionDb) {
                System.out.println(transaction);

            }

    }

}
