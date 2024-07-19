package src;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
  public Menu(String[] argsIn) {
    this.service = new TransactionsService();
    this.args = argsIn;
  }

  public void start() {
    String profile = "default";
    for (String arg : args) {
      if (arg.startsWith("--profile=")) {
        profile = arg.substring("--profile=".length());
      }
    }
    if (profile.equals("default")) {
      startMenuNormal();
    } else if (profile.equals("dev")) {
      startMenuDev();
    } else {
      System.err.println("Unknown profile");
      System.exit(-1);
    }
  }

  private void startMenuNormal() {
    boolean flag = true;
    Scanner scanner = new Scanner(System.in);
    while (flag) {
      int itemMenu = 0;
      displayMenuNormal();
      try {
        itemMenu = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again.");
        System.out.println("--------------------------------");
        scanner.next();
        continue;
      }
      if (itemMenu < 1 || itemMenu > 5) {
        System.err.println("Error: not an item of menu. Try again.");
        System.out.println("--------------------------------");
        continue;
      }
      switch (itemMenu) {
        case 1:
          addUser(scanner);
          break;
        case 2:
          viewUserBalance(scanner);
          break;
        case 3:
          performTransfer(scanner);
          break;
        case 4:
          viewUserTransactions(scanner);
          break;
        case 5:
          flag = false;
          break;
        default:
          break;
      }
    }
    scanner.close();
  }

  private void addUser(Scanner scanner) {
    String name = "";
    int balance = 0;
    boolean flag = true;

    while (flag) {
      try {
        System.out.println("Enter a user name and a balance");
        name = scanner.next();
        balance = scanner.nextInt();
        if (balance < 0) {
          System.err.println("Error: balance can't be negative. Try again...");
        } else {
          flag = false;
        }
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again...");
        scanner.next();
        continue;
      }
    }

    service.addUser(new User(name, balance));
    System.out.println("User with id = " + UserIdsGenerator.getInstance().getId() + " is added");
    System.out.println("--------------------------------");
  }

  private void viewUserBalance(Scanner scanner) {
    int id = 0;
    boolean flag = true;

    while (flag) {
      try {
        System.out.println("Enter a user ID");
        id = scanner.nextInt();
        flag = false;
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again...");
        scanner.next();
        continue;
      }
    }
    if (service.userExists(id)) {
      System.out.println(service.getUserName(id) + " - " + service.getUserBalance(id));
    } else {
      System.err.println("Error: not such a user");
    }
    System.out.println("--------------------------------");
  }

  private void performTransfer(Scanner scanner) {
    int senderId = 0;
    int recepientId = 0;
    int transferAmount = 0;
    boolean flag = true;

    if (service.getUserCount() < 2) {
      System.err.println("There are not enough users to perform a transfer. Must be at least two.");
      System.out.println("--------------------------------");
      flag = false;
    }
    while (flag) {
      try {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        senderId = scanner.nextInt();
        recepientId = scanner.nextInt();
        transferAmount = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again...");
        scanner.next();
        continue;
      }
      if (!service.userExists(recepientId) || !service.userExists(senderId)) {
        System.err.println("Error: not such a user. Try again...");
        continue;
      } else {
        if (senderId == recepientId) {
          System.err.println("Error: Transaction can't be between the same person. Try again...");
          continue;
        }
        if (transferAmount <= 0) {
          System.err.println("Error: Outcoming must be larger than 0. Try again...");
          continue;
        }
        if (service.getUserBalance(senderId) < transferAmount) {
          System.err.println("Error: transfer amount is larger than sender balance. Try again...");
          System.out.println("--------------------------------");
          flag = false;
        } else {
          service.transfer(senderId, recepientId, transferAmount);
          System.out.println("The transfer is completed.");
          System.out.println("--------------------------------");
          flag = false;
        }
      }
    }
  }

  private void viewUserTransactions(Scanner scanner) {
    int userId = 0;
    boolean flag = true;

    while (flag) {
      try {
        System.out.println("Enter a user ID");
        userId = scanner.nextInt();
        flag = false;
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again...");
        scanner.next();
        continue;
      }
    }
    if (service.userExists(userId)) {
      Transaction[] transactions = service.userTransfers(userId);
      if (transactions.length == 0) {
        System.out.println("There are no transactions for user " + service.getUserName(userId));
      } else {
        for (Transaction transaction : transactions) {
          String res = "";
          String user = "";
          int senderId = 0;
          if (transaction.getTransferCategory().equals("debits")) {
            res += "From ";
            senderId = transaction.getSenderId();
            user = service.getUserName(senderId);
          } else {
            res += "To ";
            senderId = transaction.getRecepientId();
            user = service.getUserName(senderId);
          }
          res += user + "(id = " + senderId + ") " + transaction.getTransferAmount();
          res += " with id = " + transaction.getIdentifier().toString();
          System.out.println(res);
        }
      }
    } else {
      System.err.println("Error: not such a user");
    }
    System.out.println("--------------------------------");
  }

  private void displayMenuNormal() {
    System.out.println("1. Add a user");
    System.out.println("2. View user balance");
    System.out.println("3. Perform a transfer");
    System.out.println("4. View all transactions for a specific user");
    System.out.println("5. Finish execution");
  }

  private void startMenuDev() {
    boolean flag = true;
    Scanner scanner = new Scanner(System.in);
    while (flag) {
      int itemMenu = 0;
      displayMenuDev();
      try {
        itemMenu = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again.");
        System.out.println("--------------------------------");
        scanner.next();
        continue;
      }
      if (itemMenu < 1 || itemMenu > 7) {
        System.err.println("Error: not an item of menu. Try again.");
        System.out.println("--------------------------------");
        continue;
      }
      switch (itemMenu) {
        case 1:
          addUser(scanner);
          break;
        case 2:
          viewUserBalance(scanner);
          break;
        case 3:
          performTransfer(scanner);
          break;
        case 4:
          viewUserTransactions(scanner);
          break;
        case 5:
          removeTransaction(scanner);
          break;
        case 6:
          checkValidity();
          break;
        case 7:
          flag = false;
          break;
        default:
          break;
      }
    }
    scanner.close();
  }

  private void displayMenuDev() {
    System.out.println("1. Add a user");
    System.out.println("2. View user balance");
    System.out.println("3. Perform a transfer");
    System.out.println("4. View all transactions for a specific user");
    System.out.println("5. DEV - remove a transfer by ID");
    System.out.println("6. DEV - check transfer validity");
    System.out.println("7. Finish execution");
  }

  private void removeTransaction(Scanner scanner) {
    int userId = 0;
    String transferId = "";
    UUID uuid = UUID.randomUUID();
    boolean flag = true;

    while (flag) {
      try {
        System.out.println("Enter a user ID and a transfer ID");
        userId = scanner.nextInt();
        transferId = scanner.next();
        uuid = UUID.fromString(transferId);
        flag = false;
      } catch (InputMismatchException e) {
        System.err.println("Error: not an integer. Try again.");
        scanner.next();
        continue;
      } catch (IllegalArgumentException e) {
        System.err.println("Invalid UUID format: ");
      }
    }
    if (service.userExists(userId)) {
      Transaction[] transactions = service.userTransfers(userId);
      if (transactions.length == 0) {
        System.out.println("There are no transactions for user " + service.getUserName(userId));
      } else {
        if (service.transactionExists(uuid, userId)) {
          String res = "Transfer";
          String user = "";
          int senderId = 0;
          Transaction transaction = service.getUserTransaction(userId, uuid);
          int transferAmount = transaction.getTransferAmount();
          if (transferAmount < 0) {
            transferAmount *= -1;
          }
          if (transaction.getTransferCategory().equals("debits")) {
            res += " from ";
            senderId = transaction.getSenderId();
            user = service.getUserName(senderId);
          } else {
            res += " to ";
            senderId = transaction.getRecepientId();
            user = service.getUserName(senderId);
          }
          res += user + "(id = " + senderId + ") " + transferAmount;
          res += " removed";
          System.out.println(res);
          service.removeTransaction(uuid, userId);
        } else {
          System.err.println("There is no transaction with such id");
        }
      }
    } else {
      System.err.println("Error: not such a user");
    }
    System.out.println("--------------------------------");
  }

  private void checkValidity() {
    System.out.println("Check results:");
    Transaction[] transactions = service.checkValidityTransactions();
    if (transactions.length == 0) {
      System.out.println("There are no unknowledged transfers");
    } else {
      for (Transaction transaction : transactions) {
        String res = "";
        String userRecepient = "";
        String userSender = "";
        String predlog = " from ";
        int userSenderId = 0;
        int transferAmount = transaction.getTransferAmount();
        if (transferAmount < 0) {
          transferAmount *= -1;
          predlog = " to ";
        }
        if (transaction.getTransferCategory().equals("debits")) {
          userRecepient = service.getUserName(transaction.getRecepientId());
          res += userRecepient + "(id = " + transaction.getRecepientId() + ")";
          userSenderId = transaction.getSenderId();
          userSender = service.getUserName(userSenderId);
        } else {
          userRecepient = service.getUserName(transaction.getSenderId());
          res += userRecepient + "(id = " + transaction.getSenderId() + ")";
          userSenderId = transaction.getRecepientId();
          userSender = service.getUserName(userSenderId);
        }
        res += " has an unacknowledged transfer id = " + transaction.getIdentifier();
        res += predlog + userSender + "(id = " + userSenderId + ") for " + transferAmount;
        System.out.println(res);
      }
    }
    System.out.println("--------------------------------");
  }

  private TransactionsService service;
  private String[] args;
}
