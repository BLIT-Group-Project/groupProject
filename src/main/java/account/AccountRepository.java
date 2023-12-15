package account;

import java.util.Map.Entry;

import account.exceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountRepository {
    // this is a temp repository to provide a way to dummy up the service layer so that a real database repository can be dropped in later
    private Map<Integer, Account> accountMap;
    private int counter;

    public AccountRepository() {
        this.accountMap = new HashMap<>();
        this.counter = 0;
    }

    protected Account save(Account account) {
        accountMap.put(counter, account);
        counter++;
        return accountMap.get(counter-1);
    }

    protected Optional<Account> getAccountByAccountId(int accountId) {
        return accountMap
            .values()
            .stream()
            .filter(a -> a.getAccountId() == accountId)
            .findFirst();
    }

    protected List<Account> getAllAccountsByUserId(int userId) {
        return accountMap
            .values()
            .stream()
            .filter(a -> (a.getUserId() == userId))
            .toList();
    }

    protected Account updateAccount(Account updatedAccount) {
        int accountId = updatedAccount.getAccountId();
        accountMap.put(getKeyFromValue(accountId), updatedAccount);
        return new Account(accountMap.get(getKeyFromValue(accountId)));
    }

    protected void deleteAccountById(int accountId) {
        accountMap.remove(getKeyFromValue(accountId));
    }

    protected boolean existsByAccountId(int accountId) {
        Optional<Account> findAccount = accountMap.values().stream()
            .filter(a -> a.getAccountId() == accountId)
            .findFirst();
        if (findAccount.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    // utility method to extract the corresponding Integer key from the accountID field
    private int getKeyFromValue(int accountId) {
        for (Entry<Integer, Account> entry : accountMap.entrySet()) {
            if (entry.getValue().getAccountId() == accountId) {
                return entry.getKey();
            } else {
                throw new AccountNotFoundException();
            }
        }
        return -1;
    }
}
