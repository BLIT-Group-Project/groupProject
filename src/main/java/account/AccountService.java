package account;

import java.util.Calendar;
import java.util.List;

import transaction.constants.TransactionResponse;

public interface AccountService {
    
    // basic crud stuff
    Account getAccountById(int accountId);
    List<Account> getAllAccountsByUserId(int userId);
    List<Account> getAll();
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccountById(int AccountId);

    // forcing the program to use the service layer to manage account objects:
    int getAccountId(Account account);
    int getUserId(Account account);
    double getBalance(Account account);
    String getAccountType(Account account);
    double getInterestRate(Account account);
    double deposit(Account account, double amount);
    double withdraw(Account account, double amount);
    double getCreditLimit(CreditAccount account);
    void setCreditLimit(CreditAccount account, double amount);
    double getMinimumPayment(CreditAccount account);
    double makePayment(CreditAccount account, double amount);
    CreditTransaction charge(CreditAccount account, double amount);
    TransactionResponse getTransactionResponse(CreditAccount account, double amount);

    // wishful thinking about implementing a method to add interest to accounts at a specified time:
    void applyInterest(Calendar cal, Account account);
    
}
