# Student Record Management System

A simple, interactive console-based Student Record Management application built in Java. It allows users to store, view, search, and update student records persistently using a file-based storage (`students.txt`).

---

## 🚀 Features

- **Add Student**: Add new records with Student ID, Name, Age, and Marks. Automatically validates for duplicate IDs.
- **Read All Students**: View all stored student records formatted from file.
- **Search Student**: Quickly locate any student's details using their unique ID.
- **Update Student**: Modify existing student information (Name, Age, Marks) by ID.
- **Persistent Storage**: Saves data to a comma-separated values file (`students.txt`).

---

## 🛠️ Prerequisites

- **Java Development Kit (JDK)**: JDK 14 or higher (due to modern switch expression syntax `case ->`).

---

## 📂 Project Structure

```text
java intership/
│
├── Practise.java      # Main application source code
├── students.txt       # Auto-generated data file storing student records
└── README.md          # Project documentation
```

---

## 💻 How to Compile and Run

1. **Open Terminal / Command Prompt** in the project directory:
   ```bash
   cd "c:\Users\sivas\OneDrive\Desktop\java intership"
   ```

2. **Compile the program**:
   ```bash
   javac Practise.java
   ```

3. **Run the program**:
   ```bash
   java Practise
   ```

---

## 📝 Data Storage Format

Records are stored in `students.txt` in CSV format:

```csv
<ID>,<Name>,<Age>,<Marks>
```

**Example:**
```csv
101,John Doe,20,89.5
102,Jane Smith,21,94.0
```

---

## 📖 Menu Options

When you run the program, the following interactive menu will be displayed:

```text
--- Student Record Management ---
1. Add Student
2. Read All Students
3. Search Student
4. Update Student
5. Exit
Enter your choice:
```
