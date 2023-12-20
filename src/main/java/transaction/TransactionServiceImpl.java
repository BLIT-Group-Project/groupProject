package transaction;

import account.Account;
import account.AccountRepository;
import account.AccountService;
import account.AccountServiceImpl;
import transaction.constants.TransactionResponse;
import transaction.constants.TransactionType;
import user.UserRepository;
import user.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionServices {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl() {
        this.transactionRepository = new TransactionRepositoryImpl();
        this.accountService = new AccountServiceImpl();
    }


    @Override
    public void createTransaction(Transaction transaction) {
        int tId = transactionRepository.insertTransaction(transaction);
        try{
            Thread.sleep(5 * 60 * 1000);
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        updateTransaction(tId);
    }

    @Override
    public void updateTransaction(int transactionId) {
        Transaction savedTransaction=findTransactionById(transactionId);
        Account fromAccount = accountService.getAccountById(savedTransaction.getFromAccountId());
        Account toAccount = accountService.getAccountById(savedTransaction.getToAccountId());
        if(accountService.getBalance(fromAccount) >= savedTransaction.getAmount() &&
                savedTransaction.getTransactionType() == TransactionType.WITHDRAW) {
            accountService.withdraw(fromAccount, savedTransaction.getAmount());
            accountService.deposit(toAccount, savedTransaction.getAmount());
            savedTransaction.setStatus(TransactionResponse.ACCEPTED);
            transactionRepository.updateTransaction(savedTransaction);
            return;
        } else {
            savedTransaction.setStatus(TransactionResponse.DECLINED);
            transactionRepository.updateTransaction(savedTransaction);
            return;
        }
    }

    @Override
    public void displayTransaction(int accountId) {
        List<Transaction> transactions = transactionRepository.getTransactionsByAccountId(accountId);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
    @Override
    public Transaction findTransactionById(int id) {
        return transactionRepository.getTransactionById(id);
    }
}
