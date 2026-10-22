# 🏦 Banking System with Custom Exception Handling in Java

A robust, console-based Banking System application implemented in Java, illustrating **Object-Oriented Programming (OOP)**, strict **data encapsulation**, **in-memory data structures**, and **custom checked exception hierarchies** for resilient error handling.

---

## 🚀 Features

- **👤 Account Creation & Management**:
  - Registers accounts with a unique account number, holder name, and non-negative initial deposit.
  - Guards against duplicate account numbers using hash map key lookups.
- **💵 Deposit Operations**:
  - Deposits funds safely while validating amounts against non-positive and non-finite values (`NaN`, infinity).
- **💳 Withdrawal Operations**:
  - Verifies available funds before debiting to prevent overdrafts.
- **🔄 Account-to-Account Transfers**:
  - Validates both accounts and ensures sender has sufficient funds.
  - Enforces order of operations (withdraw from sender first, deposit into receiver).
  - Blocks self-transfers (`sender == receiver`).
- **📊 Balance & Account Inquiries**:
  - Displays formatted account details and balances.
- **🛡️ Custom Exception Handling**:
  - Implements tailored checked exception classes providing descriptive error messages without crashing the application.

---

## 🛠️ Technologies & Concepts

- **Language**: Java (JDK 8 or higher)
- **Data Structure**: `java.util.Map<Integer, Account>` backed by `java.util.HashMap` for $O(1)$ average time complexity lookups
- **OOP Principles**: Encapsulation, custom exception handling, multi-catch exception blocks, validation guards
- **Data Types**: `double` with `Double.isFinite(amount)` safety validation

---

## 📂 Project Structure

```text
├── BankingSystem.java   # Core system containing Account model, custom exceptions, and BankingSystem service
├── README.md            # Comprehensive project documentation
└── .gitignore           # Ignores compiled Java bytecode (*.class)
```

---

## 🏗️ Architecture & Component Overview

All core components are encapsulated in [`BankingSystem.java`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java):

| Component | Type | Description |
| :--- | :--- | :--- |
| [`Account`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java#L26-L98) | Model Class | Encapsulates account number, holder name, and balance with internal mutation controls. |
| [`BankingSystem`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java#L101-L237) | Service / Controller | Manages the repository of accounts (`Map<Integer, Account>`) and orchestrates banking transactions. |
| [`InsufficientBalanceException`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java#L5-L9) | Custom Checked Exception | Thrown when withdrawal or transfer exceeds the account balance. |
| [`InvalidAccountException`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java#L12-L16) | Custom Checked Exception | Thrown when an account cannot be found or an account number already exists. |
| [`InvalidTransactionAmountException`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java#L19-L23) | Custom Checked Exception | Thrown when an amount is negative, zero, or non-finite (`Double.NaN` / Infinity). |

---

## 🛡️ Custom Exception Hierarchy

```text
java.lang.Throwable
   └── java.lang.Exception
         ├── InsufficientBalanceException
         ├── InvalidAccountException
         └── InvalidTransactionAmountException
```

### Exception Scenarios

| Exception | Trigger Condition | Example Error Message |
| :--- | :--- | :--- |
| `InsufficientBalanceException` | `amount > balance` | `Insufficient balance. Available: 5000.0` |
| `InvalidAccountException` | Account ID not present in map or duplicate ID registered | `Account 999 does not exist.` / `Account number already exists.` |
| `InvalidTransactionAmountException` | `!Double.isFinite(amount) || amount <= 0` | `Transaction amount must be positive and finite.` |

---

## 🔄 Transaction Flow & Safety

```text
[ Transfer Request: Account 101 -> Account 102 ($500) ]
                       │
                       ▼
             Validate amount > 0 & isFinite
                       │
                       ▼
          Lookup sender & receiver accounts
                       │
                       ▼
        Verify sender != receiver (not same account)
                       │
                       ▼
       Sender.withdraw($500) [Checks balance >= 500]
                       │
                       ▼
             Receiver.deposit($500)
                       │
                       ▼
              [Transfer Complete]
```

---

## ⚙️ Getting Started

### Prerequisites

Verify that the Java Development Kit (JDK) is installed and accessible in your environment:

```powershell
javac -version
java -version
```

### Compilation

Compile [`BankingSystem.java`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java) using `javac`:

```powershell
javac BankingSystem.java
```

### Execution

Run the application:

```powershell
java BankingSystem
```

---

## 📋 Sample Output

Running the demo routine in [`BankingSystem.main`](file:///c:/Users/sivas/OneDrive/Desktop/java%20intership/BankingSystem.java#L186-L236) yields:

```text
Account created successfully.
Account created successfully.

--- Deposit ---
Deposit successful.

--- Withdrawal ---
Withdrawal successful.

--- Transfer ---
Withdrawal successful.
Deposit successful.
Transfer successful.

--- Account Details ---
Account Number: 101
Account Holder: Siva
Balance: 5000.00

Account Number: 102
Account Holder: Ravi
Balance: 2500.00

--- Error Handling Tests ---
Error: Insufficient balance. Available: 5000.0
Error: Account 999 does not exist.
Error: Transaction amount must be positive and finite.
```

---

## 🛡️ Edge Cases Handled

1. **Negative Initial Balance**: Attempting to initialize an account with `initialBalance < 0` triggers `InvalidTransactionAmountException`.
2. **Duplicate Account Number**: Creating an account with an existing account ID fails with `InvalidAccountException`.
3. **Overdraft Prevention**: Checking `amount > balance` prevents negative balances.
4. **Non-Finite Floating Point Numbers**: Protects calculations against `Double.NaN`, `Double.POSITIVE_INFINITY`, and `Double.NEGATIVE_INFINITY`.
5. **Self-Transfer Guard**: Transferring funds from an account to itself is blocked with an informative exception.

---

## 📄 Notes

Developed as part of the Java Internship practical assignments.
