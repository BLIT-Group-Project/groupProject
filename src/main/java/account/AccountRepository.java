package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import account.constants.AccountType;
import account.exceptions.AccountNotFoundException;
import database.DatabaseConnection;

public class AccountRepository {
    private Connection connection; 

    public AccountRepository() {
        this.connection = DatabaseConnection.getConnection();
    } 

    // Look on my kludge, ye mighty, and despair
    protected Optional<Account> save(Account account) {
        int newid = 0;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts(user_id, balance, account_type, interest_rate, minimum_payment, credit_limit) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, account.getUserId());
            statement.setDouble(2, account.getBalance());
            statement.setString(3, account.getAccountType().toString());
            statement.setDouble(4, account.getInterestRate());
            if (account instanceof CreditAccount) {
                statement.setDouble(5, ((CreditAccount)account).getMinimumPayment());
                statement.setDouble(6, ((CreditAccount)account).getCreditLimit());
            } else {
                statement.setDouble(5, 0.00);
                statement.setDouble(6, 0.00);
            }
            int row = statement.executeUpdate();
            if (row > 0)
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                newid = rs.getInt(row);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return getByAccountId(newid);
    }

    protected Optional<Account> getByAccountId(int id) {
        if (existsByAccountId(id)) {
            Account account = null;
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE account_id = ?")) {
                statement.setInt(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    if (rs.getString("account_type").equalsIgnoreCase("credit")) {
                        account = new CreditAccount(
                            new Account(
                                rs.getInt("account_id"),
                                rs.getInt("user_id"), 
                                stringToAccountType(rs.getString("account_type")), 
                                rs.getDouble("balance")));
                        ((CreditAccount)account).setMinimumPayment(rs.getDouble("balance"));
                        ((CreditAccount)account).setCreditLimit(rs.getDouble("credit_limit"));
                    } else {
                        account = new Account(
                            rs.getInt("account_id"),
                            rs.getInt("user_id"), 
                            stringToAccountType(rs.getString("account_type")), 
                            rs.getDouble("balance"));
                    }
                    }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return Optional.ofNullable(account);
        } else {
            throw new AccountNotFoundException();
        }
    }

    protected List<Account> getAllByUserId(int id) {
        List<Account> tempList = new ArrayList<>();
        Account account = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE user_id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("account_type").equalsIgnoreCase("credit")) {
                        account = new CreditAccount(
                                new Account(
                                rs.getInt("account_id"),
                                rs.getInt("user_id"), 
                                stringToAccountType(rs.getString("account_type")), 
                                rs.getDouble("balance")));
                        ((CreditAccount)account).setMinimumPayment(rs.getDouble("balance"));
                        ((CreditAccount)account).setCreditLimit(rs.getDouble("minimum_payment"));
                        tempList.add(account);
                    } else {
                        account = new Account(
                            rs.getInt("account_id"),
                            rs.getInt("user_id"), 
                            stringToAccountType(rs.getString("account_type")), 
                            rs.getDouble("balance"));
                            tempList.add(account);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tempList;
    }

    protected List<Account> getAll() {
        List<Account> tempList = new ArrayList<>();
        Account account = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("account_type").equalsIgnoreCase("credit")) {
                        account = new CreditAccount(
                                new Account(
                                rs.getInt("account_id"),
                                rs.getInt("user_id"), 
                                stringToAccountType(rs.getString("account_type")), 
                                rs.getDouble("balance")));
                        ((CreditAccount)account).setMinimumPayment(rs.getDouble("balance"));
                        ((CreditAccount)account).setCreditLimit(rs.getDouble("minimum_payment"));
                        tempList.add(account);
                    } else {
                        account = new Account(
                            rs.getInt("account_id"),
                            rs.getInt("user_id"), 
                            stringToAccountType(rs.getString("account_type")), 
                            rs.getDouble("balance"));
                            tempList.add(account);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tempList;
    }

    protected Account update(Account account) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = ?, minimum_payment = ? WHERE account_id = ?")) {
            statement.setDouble(1, account.getBalance());
            if (account instanceof CreditAccount) {
                statement.setDouble(2, ((CreditAccount)account).getMinimumPayment());
            } else {
                statement.setDouble(2, 0.00);
            }
            statement.setInt(3, account.getAccountId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return getByAccountId(account.getAccountId()).get();
    }

    protected void deleteById(int id) {
        if (existsByAccountId(id)) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE account_id = ?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new AccountNotFoundException();
        }
    }

    protected boolean existsByAccountId(int id) {
        boolean exists = false;
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1) FROM accounts WHERE account_id=?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    exists = true;
                } 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return exists;
    }

    private AccountType stringToAccountType(String s) {
        switch (s.toLowerCase()) {
            case "credit": return AccountType.CREDIT;
            case "checking": return AccountType.CHECKING;
            case "savings": return AccountType.SAVINGS;
            default: return null;
        }
    }
}
