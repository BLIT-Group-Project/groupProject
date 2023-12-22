package transaction;

import account.*;
import account.constants.AccountType;
import transaction.constants.TransactionResponse;
import transaction.constants.TransactionType;
import user.UserRepository;
import user.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Thread transactionProcessing = new Thread(() -> {
            try{
                Thread.sleep(1 * 60 * 1000);
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            if(tId > 0) {
                updateTransaction(tId);
            }
        });
        transactionProcessing.setDaemon(true);
        transactionProcessing.start();
    }

    @Override
    public void updateTransaction(int transactionId) {
        Transaction savedTransaction=findTransactionById(transactionId);
        Account toAccount = accountService.getAccountById(savedTransaction.getToAccountId());
        if(savedTransaction.getFromAccountId() != 0){
            Account fromAccount = accountService.getAccountById(savedTransaction.getFromAccountId());
            if(accountService.getBalance(fromAccount) >= savedTransaction.getAmount() &&
                    savedTransaction.getTransactionType().toString().equalsIgnoreCase(TransactionType.WITHDRAW.toString())) {
                accountService.withdraw(fromAccount, savedTransaction.getAmount());
                accountService.deposit(toAccount, savedTransaction.getAmount());
                accountService.updateAccount(fromAccount);
                accountService.updateAccount(toAccount);
                savedTransaction.setStatus(TransactionResponse.ACCEPTED);
                transactionRepository.updateTransaction(savedTransaction);
                return;
            }
            if (accountService.getBalance(fromAccount) <= savedTransaction.getAmount() &&
                    savedTransaction.getTransactionType().toString().equalsIgnoreCase(TransactionType.WITHDRAW.toString())){
                savedTransaction.setStatus(TransactionResponse.DECLINED);
                transactionRepository.updateTransaction(savedTransaction);
                return;
            }
        }
        if(savedTransaction.getTransactionType().toString().equalsIgnoreCase(TransactionType.DEPOSIT.toString())) {
            accountService.deposit(toAccount, savedTransaction.getAmount());
            accountService.updateAccount(toAccount);
            savedTransaction.setStatus(TransactionResponse.ACCEPTED);
            transactionRepository.updateTransaction(savedTransaction);
            return;
        }
        if(accountService.getBalance(toAccount) + savedTransaction.getAmount() >= 1500
                && savedTransaction.getTransactionType().toString().equalsIgnoreCase(TransactionType.CHARGE.toString())) {
            savedTransaction.setStatus(TransactionResponse.DECLINED);
            transactionRepository.updateTransaction(savedTransaction);
            return;
        }
        if(accountService.getBalance(toAccount) + savedTransaction.getAmount() <= 1500
                && savedTransaction.getTransactionType().toString().equalsIgnoreCase(TransactionType.CHARGE.toString())) {
            accountService.charge((CreditAccount) toAccount, savedTransaction.getAmount());
            accountService.updateAccount(toAccount);
            savedTransaction.setStatus(TransactionResponse.ACCEPTED);
            transactionRepository.updateTransaction(savedTransaction);
            return;
        }
        if(savedTransaction.getTransactionType().toString().equalsIgnoreCase(TransactionType.MAKE_PAYMENT.toString())) {
            accountService.makePayment((CreditAccount) toAccount, savedTransaction.getAmount());
            accountService.updateAccount(toAccount);
            savedTransaction.setStatus(TransactionResponse.ACCEPTED);
            transactionRepository.updateTransaction(savedTransaction);
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
