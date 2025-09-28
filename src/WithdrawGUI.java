import javax.swing.*;

public class WithdrawGUI {
    public WithdrawGUI(Account account) {
        String amtStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
        try {
            long amt = Long.parseLong(amtStr);
            AccountDAO dao = new AccountDAO();
            if (dao.withdraw(account.getAccNo(), amt)) {
                JOptionPane.showMessageDialog(null, "Withdraw successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Withdraw failed or insufficient balance!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter valid number!");
        }
    }
}
