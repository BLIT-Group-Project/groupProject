package account;

import java.util.Calendar;
import java.util.List;

import account.exceptions.AccountMismatchException;
import account.exceptions.AccountNotFoundException;;

public class AccountServiceImpl implements AccountService{
    
    private AccountRepository repo = new AccountRepository();

    // dependency injection to be removed when this grows up and becomes a real application
    public AccountServiceImpl(AccountRepository repo) {
        this.repo = repo;
    }

    //crud stuff:
    @Override
    public Account getAccountById(int accountId) {
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
    public void deleteAccountById(int accountId) {
        if (repo.existsByAccountId(accountId)) {
            repo.deleteAccountById(accountId);
            System.out.println("Account deleted");
        } else {
            throw new AccountNotFoundException();
        }
        
    }

    // object manipulation should only be done through the Service class rather than directly through the object:
    @Override
    public int getAccountId(Account account) {
        return account.getAccountId();
    }
    
    @Override
    public int getUserId(Account account) {
        return account.getUserId();
    }

    @Override
    public double getBalance(Account account) {
        return account.getBalance();
    }

    // returns a string of the enum
    @Override
    public String getAccountType(Account account) {
        return account.getAccountType();
    }

    @Override
    public double getInterestRate(Account account) {
        return account.getInterestRate();
    }

    // these *should* only work with checking and savings accounts
    @Override
    public double deposit(Account account, double amount) {
        if (!(account instanceof CreditAccount)) {
            return account.deposit(amount);
        } else {
            throw new AccountMismatchException("Deposit", account.getAccountType());
        }
    }

    @Override
    public double withdraw(Account account, double amount) {
        if (!(account instanceof CreditAccount)) {
            return account.withdraw(amount);
        } else {
            throw new AccountMismatchException("Withdrawal", account.getAccountType());
        }
    }

    // these *should* only work with credit accounts

    @Override 
    public double getMinimumPayment(CreditAccount account) {
        if (account instanceof CreditAccount) {
            return account.getMinimumPayment();
        } else {
            throw new AccountMismatchException("Get mimimum payment", account.getAccountType());
        }
    }

    @Override 
    public double makePayment(CreditAccount account, double amount) {
        if (account instanceof CreditAccount) {
            return account.makePayment(amount);
        } else {
            throw new AccountMismatchException("Make payment", account.getAccountType());
        }
    }
    @Override
    public double charge(CreditAccount account, double amount) {
        if (account instanceof CreditAccount) {
            return account.charge(amount);
        } else {
            throw new AccountMismatchException("Charge", account.getAccountType());
        }
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
    private Account violenlyShakeOptionalAccount(int accountId) {
        return repo.getAccountByAccountId(accountId)
            .orElseThrow(() -> new AccountNotFoundException());
    }
    
}
