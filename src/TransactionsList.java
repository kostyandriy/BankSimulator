package src;

import java.util.UUID;

public interface TransactionsList {
  public void addTransaction(Transaction transaction);
  public void removeById(UUID id);
  public Transaction[] transformIntoArray();
}
