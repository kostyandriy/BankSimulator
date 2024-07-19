package src;

public class User {
  public int getIdentifier() {
    return identifier;
  }

  public User(String name, int balance) {
    if (balance < 0) {
      System.err.println("Balance can't be negative");
      System.exit(-1);
    }
    this.balance = balance;
    this.name = name;
    this.identifier = UserIdsGenerator.getInstance().generateId();
    this.userTransactions = new TransactionsLinkedList();
  }

  public String getName() {
    return name;
  }

  public void getInformation() {
    System.out.println("id: " + identifier);
    System.out.println("Name: " + name);
    System.out.println("Balance: " + balance);
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int newBalance) {
    this.balance = newBalance;
  }

  public TransactionsLinkedList userTransactions;

  private int identifier;
  private String name;
  private int balance;
}
