import javax.swing.*;
import java.awt.event.*;

public class MainMenuGUI {
    private JFrame frame;
    private Account account;
    private AccountDAO dao = new AccountDAO();

    public MainMenuGUI(Account account) {
        this.account = account;
        frame = new JFrame("Bank Main Menu - " + account.getName());
        frame.setSize(400, 300);
        frame.setLayout(null);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(50, 50, 120, 40);
        depositBtn.addActionListener(e -> new DepositGUI(account));

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(200, 50, 120, 40);
        withdrawBtn.addActionListener(e -> new WithdrawGUI(account));

        JButton balanceBtn = new JButton("Balance");
        balanceBtn.setBounds(50, 120, 120, 40);
        balanceBtn.addActionListener(e -> new BalanceGUI(account));

        JButton txnBtn = new JButton("Transactions");
        txnBtn.setBounds(200, 120, 120, 40);
        txnBtn.addActionListener(e -> new TransactionHistoryGUI(account));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(125, 200, 120, 40);
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });

        frame.add(depositBtn); frame.add(withdrawBtn);
        frame.add(balanceBtn); frame.add(txnBtn);
        frame.add(logoutBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
