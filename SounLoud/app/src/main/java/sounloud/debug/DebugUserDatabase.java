package sounloud.debug;

import sounloud.services.UserDatabase;
import sounloud.model.User;
import java.util.List;

public class DebugUserDatabase {
    public static void main(String[] args) {
        UserDatabase db = new UserDatabase();
        System.out.println("Trying to load users...");
        List<User> users = db.loadUsers();
        System.out.println("Loaded " + users.size() + " users");
        for (User user : users) {
            System.out.println("User: " + user.getNameUser());
        }
    }
}
