package src;

import java.util.ArrayList;
import java.util.List;

public class UsersArrayList implements UsersList {
  public UsersArrayList() {
    userList = new ArrayList<User>(10);
  }

  public void addUser(User user) {
    userList.add(user);
  }

  public boolean userExists(int id) {
    for (User user : userList) {
      if (user.getIdentifier() == id) {
        return true;
      }
    }
    return false;
  }

  public String getUserName(int id) {
    for (User user : userList) {
      if (user.getIdentifier() == id) {
        return user.getName();
      }
    }
    return "";
  }

  public User getUserById(int id) {
    for (User user : userList) {
      if (user.getIdentifier() == id) {
        return user;
      }
    }
    throw new RuntimeException("UserNotFoundException");
  }

  public User getUserByIndex(int index) {
    if (index >= 0 && index < userList.size()) {
      return userList.get(index);
    }
    throw new RuntimeException("UserNotFoundException");
  }

  public int getUserCount() {
    return userList.size();
  }

  public User[] toArray() {
    return userList.toArray(new User[0]);
  }

  private List<User> userList;
}
