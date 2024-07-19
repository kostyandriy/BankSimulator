package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TransactionsService {
  public TransactionsService() {
    userList = new UsersArrayList();
  }

  public void addUser(User user) {
    userList.addUser(user);
  }

  public boolean userExists(int id) {
    return userList.userExists(id);
  }

  public int getUserBalance(int id) {
    return userList.getUserById(id).getBalance();
  }

  public String getUserName(int id) {
    return userList.getUserName(id);
  }

  public int getUserCount() {
    return userList.getUserCount();
  }

  public Transaction getUserTransaction(int userId, UUID transactionId) {
    for (Transaction transaction :
        userList.getUserById(userId).userTransactions.transformIntoArray()) {
      if (transaction.getIdentifier().equals(transactionId)) {
        return transaction;
      }
    }
    return null;
  }

  public void transfer(int sender, int recepient, int amount) {
    if (sender == recepient) {
      System.out.println("Transaction can't be between the same person");
      System.exit(-1);
    }
    int senderBalance = getUserBalance(sender);
    int recepientBalance = getUserBalance(recepient);
    if (senderBalance >= amount) {
      User userSender = userList.getUserById(sender);
      User userRecepient = userList.getUserById(recepient);

      userSender.setBalance(senderBalance - amount);
      userRecepient.setBalance(recepientBalance + amount);

      UUID uuid = UUID.randomUUID();
      userSender.userTransactions.addTransaction(
          new Transaction(uuid, recepient, sender, "credit", -amount));
      userRecepient.userTransactions.addTransaction(
          new Transaction(uuid, recepient, sender, "debits", amount));
      return;
    }
    throw new RuntimeException("IllegalTransactionException");
  }

  public Transaction[] userTransfers(int id) {
    return userList.getUserById(id).userTransactions.transformIntoArray();
  }

  public boolean transactionExists(UUID transactionId, int userId) {
    return userList.getUserById(userId).userTransactions.transactionExists(transactionId);
  }

  public void removeTransaction(UUID transactionId, int userId) {
    userList.getUserById(userId).userTransactions.removeById(transactionId);
  }

  public Transaction[] checkValidityTransactions() {
    List<Transaction> unpairTransactions = new LinkedList<Transaction>();
    User[] user1 = userList.toArray();
    User[] user2 = userList.toArray();
    int count = userList.getUserCount();

    for (int i = 0; i < count; i++) {
      Transaction[] transactions1 = user1[i].userTransactions.transformIntoArray();
      for (Transaction transaction1 : transactions1) {
        boolean flag = false;
        for (int j = 0; j < count; j++) {
          if (user1[i].getIdentifier() != user2[j].getIdentifier()) {
            Transaction[] transactions2 = user2[j].userTransactions.transformIntoArray();
            for (Transaction transaction2 : transactions2) {
              if (transaction1.getIdentifier().equals(transaction2.getIdentifier())) {
                flag = true;
              }
            }
          }
        }
        if (flag == false) {
          unpairTransactions.add(transaction1);
        }
      }
    }
    return unpairTransactions.toArray(new Transaction[0]);
  }

  private UsersArrayList userList;
}