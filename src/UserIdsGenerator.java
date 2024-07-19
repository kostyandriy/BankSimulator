package src;

public class UserIdsGenerator {
  public static UserIdsGenerator getInstance() {
    if (instance == null) {
      instance = new UserIdsGenerator();
    }
    return instance;
  }

  public int generateId() {
    return ++id;
  };

  public int getId() {
    return id;
  }

  private static UserIdsGenerator instance;

  private int id = 0;
}
