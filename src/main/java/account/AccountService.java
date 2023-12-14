package account;

import java.util.Calendar;
import java.util.List;

public interface AccountService {
    
    Account getAccountById(String accountId);
    List<Account> getAllAccountsByUserId(int userId);
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccountById(String AccountId);
    void applyInterest(Calendar cal, Account account);
    
}
