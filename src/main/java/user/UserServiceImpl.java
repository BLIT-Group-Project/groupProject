package user;

import helper.PasswordEncoder;

import java.util.ArrayList;

public class UserServiceImpl implements UserService{

    private final ArrayList<User> userList;

    public UserServiceImpl() {
        this.userList = new ArrayList<>();
    }

    @Override
    public void saveUser(User user) {
        if(userList.isEmpty()){
            user.setSalt(PasswordEncoder.generateSalt());
            user.setPassword(PasswordEncoder.hashPassword(user.getPassword(), user.getSalt()));
            userList.add(user);
            return;
        }

        for (User u : userList) {
            if(u.getUsername().equals(user.getUsername())){
                System.out.println("USERNAME ALREADY TAKEN, PLEASE CHOSE ANOTHER!");
                return;
            }
        }
        user.setSalt(PasswordEncoder.generateSalt());
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword(), user.getSalt()));
        userList.add(user);
    }

    @Override
    public void updateUser(int userId, User user) {
        if (userList.isEmpty()) {
            System.out.println("NO USER IN SYSTEM TO UPDATE, PLEASE CREATE A USER BEFORE UPDATING!");
            return;
        }

        for (User u : userList) {
            if(u.getUserId() == userId){
                u.setUsername(user.getUsername());
                u.setPassword(PasswordEncoder.hashPassword(user.getPassword(), u.getSalt()));
            } else System.out.println("USER WITH ID " + userId + " DOESN'T EXIST");
        }
    }

    @Override
    public User getUserById(int userId) {
        for (User user : userList) {
            if(user.getUserId() == userId){
                System.out.println(user);
                return user;
            }
        }
        return new User();
    }

    @Override
    public void deleteUserById(int userId) {
        for (User user : userList) {
            if(user.getUserId() == userId) {
                userList.remove(user);
                System.out.println("USER REMOVED SUCCESSFULLY");
                return;
            } else System.out.println("USER WITH ID " + userId + " DOESN'T EXIST");
        }
    }
}
