package src;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
  public TransactionsLinkedList() {
    transactionsList = new LinkedList<Transaction>();
  }

  public void addTransaction(Transaction transaction) {
    transactionsList.add(transaction);
  }

  public boolean transactionExists(UUID id) {
    for (Transaction transaction : transactionsList) {
      if (id.equals(transaction.getIdentifier())) {
        return true;
      }
    }
    return false;
  }

  public void removeById(UUID id) {
    for (Transaction transaction : transactionsList) {
      if (id.equals(transaction.getIdentifier())) {
        transactionsList.remove(transaction);
        return;
      }
    }
    throw new RuntimeException("TransactionNotFoundException");
  }

  public Transaction[] transformIntoArray() {
    return transactionsList.toArray(new Transaction[0]);
  }

  private List<Transaction> transactionsList;
}
