import java.util.HashMap;
import java.util.Map;

// Custom exception for insufficient balance
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Custom exception for invalid account
class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
}

// Custom exception for invalid transaction amount
class InvalidTransactionAmountException extends Exception {
    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}

// Account class
class Account {
    private final int accountNumber;
    private final String accountHolder;
    private double balance;

    public Account(int accountNumber, String accountHolder,
                   double initialBalance)
            throws InvalidTransactionAmountException {

        if (initialBalance < 0) {
            throw new InvalidTransactionAmountException(
                "Initial balance cannot be negative."
            );
        }

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount)
            throws InvalidTransactionAmountException {

        validateAmount(amount);
        balance += amount;

        System.out.println("Deposit successful.");
    }

    public void withdraw(double amount)
            throws InsufficientBalanceException,
                   InvalidTransactionAmountException {

        validateAmount(amount);

        if (amount > balance) {
            throw new InsufficientBalanceException(
                "Insufficient balance. Available: " + balance
            );
        }

        balance -= amount;
        System.out.println("Withdrawal successful.");
    }

    private void validateAmount(double amount)
            throws InvalidTransactionAmountException {

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidTransactionAmountException(
                "Transaction amount must be positive and finite."
            );
        }
    }

    public void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.printf("Balance: %.2f%n", balance);
    }
}

// Banking system
public class BankingSystem {
    private final Map<Integer, Account> accounts = new HashMap<>();

    public void createAccount(int accountNumber, String name,
                              double initialBalance)
            throws InvalidAccountException,
                   InvalidTransactionAmountException {

        if (accounts.containsKey(accountNumber)) {
            throw new InvalidAccountException(
                "Account number already exists."
            );
        }

        Account account =
            new Account(accountNumber, name, initialBalance);

        accounts.put(accountNumber, account);
        System.out.println("Account created successfully.");
    }

    private Account findAccount(int accountNumber)
            throws InvalidAccountException {

        Account account = accounts.get(accountNumber);

        if (account == null) {
            throw new InvalidAccountException(
                "Account " + accountNumber + " does not exist."
            );
        }

        return account;
    }

    public void deposit(int accountNumber, double amount)
            throws InvalidAccountException,
                   InvalidTransactionAmountException {

        findAccount(accountNumber).deposit(amount);
    }

    public void withdraw(int accountNumber, double amount)
            throws InvalidAccountException,
                   InsufficientBalanceException,
                   InvalidTransactionAmountException {

        findAccount(accountNumber).withdraw(amount);
    }

    public void checkBalance(int accountNumber)
            throws InvalidAccountException {

        Account account = findAccount(accountNumber);
        System.out.printf("Current balance: %.2f%n",
                          account.getBalance());
    }

    public void transfer(int fromAccount, int toAccount, double amount)
            throws InvalidAccountException,
                   InsufficientBalanceException,
                   InvalidTransactionAmountException {

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidTransactionAmountException(
                "Transfer amount must be positive and finite."
            );
        }

        Account sender = findAccount(fromAccount);
        Account receiver = findAccount(toAccount);

        if (sender == receiver) {
            throw new InvalidAccountException(
                "Cannot transfer to the same account."
            );
        }

        // Withdraw first; deposit cannot fail for a valid amount.
        sender.withdraw(amount);
        receiver.deposit(amount);

        System.out.println("Transfer successful.");
    }

    public static void main(String[] args) {
        BankingSystem bank = new BankingSystem();

        try {
            bank.createAccount(101, "Siva", 5000);
            bank.createAccount(102, "Ravi", 2000);

            System.out.println("\n--- Deposit ---");
            bank.deposit(101, 1500);

            System.out.println("\n--- Withdrawal ---");
            bank.withdraw(101, 1000);

            System.out.println("\n--- Transfer ---");
            bank.transfer(101, 102, 500);

            System.out.println("\n--- Account Details ---");
            bank.findAccount(101).displayDetails();
            System.out.println();
            bank.findAccount(102).displayDetails();

            System.out.println("\n--- Error Handling Tests ---");

            // Test insufficient balance
            try {
                bank.withdraw(101, 100000);
            } catch (InsufficientBalanceException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Test invalid account
            try {
                bank.checkBalance(999);
            } catch (InvalidAccountException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Test invalid transaction amount
            try {
                bank.deposit(101, -500);
            } catch (InvalidTransactionAmountException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (InvalidAccountException
                 | InvalidTransactionAmountException
                 | InsufficientBalanceException e) {

            System.out.println("Banking error: " + e.getMessage());
        }
    }
}
