package account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountRepository {
    // this is a temp repository to provide a way to dummy up the service layer so that a real database repository can be dropped in later
    private Map<Integer, Account> accountMap;

    public AccountRepository() {
        this.accountMap = new HashMap<>();
    }

    protected Account save(Account account) {
        accountMap.put(account.getAccountId(), account);
        return accountMap.get(account.getAccountId());
    }

    protected Optional<Account> getByAccountId(int accountId) {
        return accountMap
            .values()
            .stream()
            .filter(a -> a.getAccountId() == accountId)
            .findFirst();
    }

    protected List<Account> getAllByUserId(int userId) {
        return accountMap
            .values()
            .stream()
            .filter(a -> (a.getUserId() == userId))
            .toList();
    }

    protected List<Account> getAll() {
        return accountMap
            .values()
            .stream()
            .toList();
    }

    protected void deleteById(int accountId) {
        accountMap.remove(accountId);
    }

    protected boolean existsByAccountId(int accountId) {
        if (accountMap.containsKey(accountId)) {
            return true;
        } else {
            return false;
        }
    }
}
