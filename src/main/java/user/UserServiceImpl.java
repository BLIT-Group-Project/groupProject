package user;

import helper.PasswordEncoder;

import java.util.List;


public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public User saveUser(User user){
        user.setSalt(PasswordEncoder.generateSalt());
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword(), user.getSalt()));
        userRepository.insertUser(user);
        user = userRepository.getUserByUsername(user.getUsername());
        return user;
    }

    @Override
    public void updateUser(int userId, User user) {
        User oldUser = userRepository.getUserById(userId);
        if (oldUser == null) {
            System.out.println("USER DOESN'T EXISTS");
        }
        if(oldUser.getUserId() == userId){
            oldUser.setPassword(PasswordEncoder.hashPassword(user.getPassword(), oldUser.getSalt()));
            userRepository.updateUser(oldUser);
        } else System.out.println("USER WITH ID " + userId + " DOESN'T EXIST");
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public void deleteUserById(int userId) {
        int row = userRepository.deleteUser(userId);
        if(row > 0) {
            System.out.println("USER REMOVED SUCCESSFULLY");
            return;
        } else System.out.println("USER WITH ID " + userId + " DOESN'T EXIST");
    }

    @Override
    public User login(String username, String password, User user) {
        if (user == null | user == new User()) {
            System.out.println("YOU ARE ALREADY LOGGED IN");
            return user;
        } else {
            user = userRepository.getUserByUsername(username);
            if(PasswordEncoder.verifyPassword(password, user.getPassword(), user.getSalt())){
                System.out.println("USER LOGGED IN SUCCESSFULLY!");
                return user;
            }
            else {
                System.out.println("LOGIN FAILED");
                return new User();
            }
        }
    }

    @Override
    public User logout() {
        return new User();
    }

}
