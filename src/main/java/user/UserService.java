package user;

public interface UserService {
    public void saveUser(User user);
    public void updateUser(int userId, User user);
    public User getUserById(int userId);
    public void deleteUserById(int userId);
}
