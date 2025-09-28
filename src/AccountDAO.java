import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {


    public boolean createAccount(long accNo, String name, int pin) {
        String sql = "INSERT INTO accounts (acc_no, name, pin, balance) VALUES (?, ?, ?, 0)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accNo);
            ps.setString(2, name);
            ps.setInt(3, pin);
            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }


    public Account getAccount(long accNo) {
        String sql = "SELECT acc_no, name, pin, balance FROM accounts WHERE acc_no = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getLong("acc_no"),
                            rs.getString("name"),
                            rs.getInt("pin"),
                            rs.getLong("balance"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching account: " + e.getMessage());
        }
        return null;
    }


    public boolean deposit(long accNo, long amount) {
        String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE acc_no = ?";
        String insertTxn = "INSERT INTO transactions (acc_no, type, amount) VALUES (?, 'DEPOSIT', ?)";
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(updateBalance);
                 PreparedStatement ps2 = conn.prepareStatement(insertTxn)) {

                ps1.setLong(1, amount);
                ps1.setLong(2, accNo);
                int u = ps1.executeUpdate();
                if (u != 1) throw new SQLException("Account not found or update failed");

                ps2.setLong(1, accNo);
                ps2.setLong(2, amount);
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Deposit failed: " + ex.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Database error during deposit: " + e.getMessage());
            return false;
        }
    }


    public boolean withdraw(long accNo, long amount) {
        String checkSql = "SELECT balance FROM accounts WHERE acc_no = ? FOR UPDATE";
        String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE acc_no = ?";
        String insertTxn = "INSERT INTO transactions (acc_no, type, amount) VALUES (?, 'WITHDRAW', ?)";
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setLong(1, accNo);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Account not found");
                    long balance = rs.getLong("balance");
                    if (balance < amount) throw new SQLException("Insufficient balance");
                }
            }

            try (PreparedStatement ps1 = conn.prepareStatement(updateBalance);
                 PreparedStatement ps2 = conn.prepareStatement(insertTxn)) {

                ps1.setLong(1, amount);
                ps1.setLong(2, accNo);
                int u = ps1.executeUpdate();
                if (u != 1) throw new SQLException("Update failed");

                ps2.setLong(1, accNo);
                ps2.setLong(2, amount);
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Withdraw failed: " + ex.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Database error during withdraw: " + e.getMessage());
            return false;
        }
    }


    public List<TransactionRecord> getTransactions(long accNo) {
        List<TransactionRecord> list = new ArrayList<>();
        String sql = "SELECT id, acc_no, type, amount, timestamp FROM transactions WHERE acc_no = ? ORDER BY timestamp DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TransactionRecord(
                            rs.getLong("id"),
                            rs.getLong("acc_no"),
                            rs.getString("type"),
                            rs.getLong("amount"),
                            rs.getTimestamp("timestamp")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }
        return list;
    }


    public List<Account> listAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT acc_no, name, pin, balance FROM accounts ORDER BY acc_no";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Account(rs.getLong("acc_no"),
                        rs.getString("name"),
                        rs.getInt("pin"),
                        rs.getLong("balance")));
            }
        } catch (SQLException e) {
            System.err.println("Error listing accounts: " + e.getMessage());
        }
        return list;
    }
}
