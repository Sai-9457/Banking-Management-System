import javax.swing.*;

public class BalanceGUI {
    public BalanceGUI(Account account) {
        AccountDAO dao = new AccountDAO();
        Account updated = dao.getAccount(account.getAccNo());
        JOptionPane.showMessageDialog(null, "Balance: " + updated.getBalance());
    }
}
