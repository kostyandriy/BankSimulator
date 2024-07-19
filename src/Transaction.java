package src;

import java.util.UUID;

public class Transaction {
  public Transaction(int recepient, int sender, String transferCategory, int transferAmount) {
    if (!transferCategory.equals("debits") && !transferCategory.equals("credit")) {
      System.err.println("Wrong type of transaction");
      System.err.println(recepient + " " + sender);
      System.exit(-1);
    }
    if (transferCategory.equals("debits") && transferAmount < 0) {
      System.err.println("Incoming can't be negative");
      System.exit(-1);
    }
    if (transferCategory.equals("credit") && transferAmount > 0) {
      System.err.println("Outcoming can't be postive");
      System.exit(-1);
    }
    this.identifier = UUID.randomUUID();
    this.recepient = recepient;
    this.sender = sender;
    this.transferCategory = transferCategory;
    this.transferAmount = transferAmount;
  }

  public Transaction(
      UUID id, int recepient, int sender, String transferCategory, int transferAmount) {
    if (!transferCategory.equals("debits") && !transferCategory.equals("credit")) {
      System.err.println("Wrong type of transaction");
      System.err.println(recepient + " " + sender);
      System.exit(-1);
    }
    if (transferCategory.equals("debits") && transferAmount < 0) {
      System.err.println("Incoming can't be negative");
      System.exit(-1);
    }
    if (transferCategory.equals("credit") && transferAmount > 0) {
      System.err.println("Outcoming can't be postive");
      System.exit(-1);
    }
    this.identifier = id;
    this.recepient = recepient;
    this.sender = sender;
    this.transferCategory = transferCategory;
    this.transferAmount = transferAmount;
  }

  public void getInformation() {
    System.out.println("id: " + identifier);
    System.out.println("Recepient: " + recepient);
    System.out.println("Sender: " + sender);
    System.out.println("Type of transaction: " + transferCategory);
    System.out.println("Transferred: " + transferAmount);
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public int getSenderId() {
    return sender;
  }

  public int getRecepientId() {
    return recepient;
  }

  public int getTransferAmount() {
    return transferAmount;
  }

  public String getTransferCategory() {
    return transferCategory;
  }

  private UUID identifier;
  private int recepient;
  private int sender;
  private String transferCategory;
  private int transferAmount;
}
