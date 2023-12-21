package transaction;

import database.DatabaseConnection;
import transaction.constants.TransactionResponse;
import transaction.constants.TransactionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository{
    @Override
    public int insertTransaction(Transaction transaction) {
        try(PreparedStatement statement = DatabaseConnection
                .getConnection()
                .prepareStatement("INSERT INTO transactions (from_id, to_id, amount, transaction_status, transaction_type)" +
                        "VALUE (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transaction.getFromAccountId());
            statement.setInt(2, transaction.getToAccountId());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getStatus().toString());
            statement.setString(5, transaction.getTransactionType().toString());

            int row = statement.executeUpdate();

            if(row > 0) {
                System.out.println("TRANSACTION INSERTED SUCCESSFULLY");
                try(ResultSet res = statement.getGeneratedKeys()){
                    while (res.next()){
                        return (int) res.getInt(1);
                    }
                }
            } else {
                System.out.println("TRANSACTION INSERTION FAILED");
                return row;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactionList = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection
                .getConnection()
                .prepareStatement("SELECT * FROM transactions WHERE from_id = ? OR to_id = ?")) {
            statement.setInt(1, accountId);
            statement.setInt(2, accountId);
            try(ResultSet res = statement.executeQuery()){
                while (res.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(res.getInt("transaction_id"));
                    transaction.setFromAccountId(res.getInt("from_id"));
                    transaction.setToAccountId(res.getInt("to_id"));
                    transaction.setAmount(res.getDouble("amount"));
                    transaction.setTimeStamp(res.getTimestamp("_timestamp").toLocalDateTime());
                    transaction.setTransactionType(TransactionType.valueOf(res.getString("transaction_type").toUpperCase()));
                    transaction.setStatus(TransactionResponse.valueOf(res.getString("transaction_status").toUpperCase()));
                    transactionList.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return transactionList;
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        Transaction transaction = new Transaction();
        try (PreparedStatement statement = DatabaseConnection
                .getConnection()
                .prepareStatement("SELECT * FROM transactions WHERE transaction_id = ?")) {
            statement.setInt(1, transactionId);
            try(ResultSet res = statement.executeQuery()) {
                while (res.next()) {
                    transaction.setTransactionId(res.getInt("transaction_id"));
                    transaction.setFromAccountId(res.getInt("from_id"));
                    transaction.setToAccountId(res.getInt("to_id"));
                    transaction.setAmount(res.getDouble("amount"));
                    transaction.setTimeStamp(res.getTimestamp("_timestamp").toLocalDateTime());
                    transaction.setTransactionType(TransactionType.valueOf(res.getString("transaction_type").toUpperCase()));
                    transaction.setStatus(TransactionResponse.valueOf(res.getString("transaction_status").toUpperCase()));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try (PreparedStatement statement = DatabaseConnection
                .getConnection()
                .prepareStatement("UPDATE transactions SET transaction_status = ? WHERE transaction_id = ?")) {
            statement.setString(1, transaction.getStatus().toString());
            statement.setInt(2, transaction.getTransactionId());
            int row = statement.executeUpdate();
            if (row > 0 ) {
                System.out.println("TRANSACTION UPDATED");
            }
            else {
                System.out.println("TRANSACTION UPDATE FAILED");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
