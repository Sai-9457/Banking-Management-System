import javax.swing.*;

public class DepositGUI {
    public DepositGUI(Account account) {
        String amtStr = JOptionPane.showInputDialog("Enter amount to deposit:");
        try {
            long amt = Long.parseLong(amtStr);
            AccountDAO dao = new AccountDAO();
            if (dao.deposit(account.getAccNo(), amt)) {
                JOptionPane.showMessageDialog(null, "Deposit successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Deposit failed!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter valid number!");
        }
    }
}
