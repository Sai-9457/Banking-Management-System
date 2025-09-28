# Banking Management System (Java Swing + MySQL)

## Project Overview
This is a **desktop-based Banking Management System** built using **Java Swing** for the graphical user interface (GUI) and **MySQL** for backend database storage.  
It simulates real-world banking operations, allowing users to **create accounts, deposit and withdraw money, check balance, and view transaction history**.

---

## Tech Stack
- **Java** (Core Java, JDBC)  
- **Swing** (GUI)  
- **MySQL** (Database)  

---

## Features
1. **Account Creation** – Create a new bank account with a unique account number, name, and PIN.  
2. **Login** – Secure login with account number and PIN.  
3. **Deposit** – Add money to the account with automatic transaction logging.  
4. **Withdraw** – Withdraw money with balance check and transaction logging.  
5. **Balance Check** – View current account balance.  
6. **Transaction History** – View all deposits and withdrawals with timestamps in a table format.  
7. **Swing GUI** – User-friendly interface for all operations.  

---

## Database Structure

**Accounts Table**
```sql
CREATE TABLE accounts (
    acc_no BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance BIGINT DEFAULT 0,
    pin INT NOT NULL
);
