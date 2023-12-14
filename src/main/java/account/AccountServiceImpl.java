package account;

import java.util.Calendar;
import java.util.List;

import account.exceptions.AccountNotFoundException;;

public class AccountServiceImpl implements AccountService{

    // PROBABLY NOT DONE PLZ NO TOUCHY
    // to do:
    
    private AccountRepository repo = new AccountRepository();

    @Override
    public Account getAccountById(String accountId) {
        return violenlyShakeOptionalAccount(accountId);
    }

    @Override
    public List<Account> getAllAccountsByUserId(int userId) {
        if (!repo.getAllAccountsByUserId(userId).isEmpty() && repo.getAllAccountsByUserId(userId) != null) {
            return repo.getAllAccountsByUserId(userId);
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public Account createAccount(Account account) {
        return repo.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        if (repo.existsByAccountId(account.getAccountId())) {
            return repo.updateAccount(account);
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void deleteAccountById(String accountId) {
        if (repo.existsByAccountId(accountId)) {
            repo.deleteAccountById(accountId);
        } else {
            throw new AccountNotFoundException();
        }
        System.out.println("Account deleted");
    }

    // look, I have NO IDEA how to really implement this
    // this should check if it's 1am on the 28th of february or 30th or any other month, calculate interest and then add it to the balance
    // it might need to be run as a cron job to check the date and then apply interest, but right now can just be run whenever the account service is called
    @Override
    public void applyInterest(Calendar cal, Account account) {
        if (cal.get(Calendar.DAY_OF_MONTH) == 28 && cal.get(Calendar.MONTH) == 1 && cal.get(Calendar.HOUR_OF_DAY) == 1) {
            account.deposit(account.getBalance() * account.getInterestRate());
        } else if (cal.get(Calendar.DAY_OF_MONTH) == 30 && cal.get(Calendar.MONTH) != 1 && cal.get(Calendar.HOUR_OF_DAY) == 1) {
            account.deposit(account.getBalance() * account.getInterestRate());
        }
    }

    // you have to treat optionals like a christmas present from a practical joker, and shake it to see if anything is even in there before opening it
    private Account violenlyShakeOptionalAccount(String accountId) {
        return repo.getAccountByAccountId(accountId)
            .orElseThrow(() -> new AccountNotFoundException());
    }
    
}
