package user;

import java.util.List;

public interface UserRepository {
    public void insertUser(User user);
    public int updateUser(User user);
    public User getUserById(int userId);
    public User getUserByUsername(String username);
    public int deleteUser(int userId);
}
