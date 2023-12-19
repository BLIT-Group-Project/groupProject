package user;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{
    @Override
    public void insertUser(User user) {
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("INSERT INTO users (username, password, salt) VALUE (?,?,?)")){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSalt());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int updateUser(User user) {
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("UPDATE users SET password = ? WHERE user_id = ?")) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getUserId());

            int row = statement.executeUpdate();

            if(row > 0) {
                return row;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User getUserById(int userId) {
        User user = new User();
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet res = statement.executeQuery()){
                if (res.next()) {
                    user.setUserId(res.getInt("user_id"));
                    user.setUsername(res.getString("username"));
                    user.setPassword(res.getString("password"));
                    user.setSalt(res.getString("salt"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return new User();
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM users WHERE username = ?")){
            statement.setString(1, username);
            try(ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    user.setUserId(res.getInt("user_id"));
                    user.setUsername(res.getString("username"));
                    user.setPassword(res.getString("password"));
                    user.setSalt(res.getString("salt"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return new User();
    }

    @Override
    public int deleteUser(int userId) {
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            statement.setInt(1, userId);
            int row = statement.executeUpdate();
            if(row > 0) {
                return row;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
