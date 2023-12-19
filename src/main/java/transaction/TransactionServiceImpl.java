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
        Transaction savedTransaction=findTransactionById(transaction.getTransactionId());
        savedTransaction.setAmount(transaction.getAmount());
        savedTransaction.setTimeStamp(transaction.getTimeStamp());



    }
//        transaction.setTransactionId(transaction.getFromAccountId());
//        transaction.setTimeStamp(transaction.getTimeStamp());



    @Override
    public void deleteTransaction(int transactionId) {
        Transaction transactionToRemove = null;
        for (Transaction transaction : transactionDb) {
            if (transaction.getTransactionId() == transactionId) {
                transactionToRemove = transaction;
            }
            if (transactionToRemove != null) {
                transactionDb.remove(transactionToRemove);
            }

        }
    }

    @Override
    public void displayTransaction(int accountId) {
        if(transactionDb.isEmpty()){
            System.out.println("No transaction to display ");
        }else
            for (Transaction transaction : transactionDb) {
                if(transaction.getFromAccountId()==accountId || transaction.getToAccountId()== accountId){
                System.out.println(transaction);}

            }

    }
    @Override
    public Transaction findTransactionById(int id) {
        for (Transaction transaction : transactionDb) {
            if(transaction.getTransactionId()==id){
                return transaction;
            }

        }
        return null;
    }
    


}
