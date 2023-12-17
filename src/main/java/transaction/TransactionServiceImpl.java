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
        // If updating the transaction need to find the transaction by transactionId before you can make changes to the transaction.
        // Will want to know more about your idea on the updateTransaction.
        
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
                // I would remove this as it will break out of the For loop
                break;
            }
            if (transactionToRemove != null) {
                transactionDb.remove(transactionToRemove);
            }

        }
    }

    @Override
    public void displayTransaction() {
        // This one we are going want to makesure to limit who sees what transactions by checking if their accountId is in the FromId or ToId
        // Just like a bank we would not want to have every customer see every transaction that goes through the bank itself just information
        // Important to them.
        if(transactionDb.isEmpty()){
            System.out.println("No transaction to display ");
        }else
            for (Transaction transaction : transactionDb) {
                System.out.println(transaction);

            }

    }

}
