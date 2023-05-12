package ejb;

import model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserBean {
    void createUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
}
