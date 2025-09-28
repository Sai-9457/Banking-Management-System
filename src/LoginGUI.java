import javax.swing.*;
import java.awt.event.*;

public class LoginGUI {
    private JFrame frame;
    private JTextField accField;
    private JPasswordField pinField;
    private AccountDAO dao = new AccountDAO();

    public LoginGUI() {
        frame = new JFrame("Bank Login");
        frame.setSize(300, 250);
        frame.setLayout(null);

        JLabel accLabel = new JLabel("Account No:");
        accLabel.setBounds(20, 20, 100, 30);
        accField = new JTextField();
        accField.setBounds(120, 20, 130, 30);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(20, 70, 100, 30);
        pinField = new JPasswordField();
        pinField.setBounds(120, 70, 130, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 130, 90, 30);

        JButton createBtn = new JButton("Create Account");
        createBtn.setBounds(150, 130, 130, 30);

        loginBtn.addActionListener(e -> login());
        createBtn.addActionListener(e -> createAccount());

        frame.add(accLabel); frame.add(accField);
        frame.add(pinLabel); frame.add(pinField);
        frame.add(loginBtn); frame.add(createBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void login() {
        try {
            long acc = Long.parseLong(accField.getText());
            int pin = Integer.parseInt(new String(pinField.getPassword()));

            Account a = dao.getAccount(acc);
            if (a != null && a.getPin() == pin) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                new MainMenuGUI(a);
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong Account or PIN!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Enter valid numbers!");
        }
    }

    private void createAccount() {
        try {
            long acc = Long.parseLong(accField.getText());
            int pin = Integer.parseInt(new String(pinField.getPassword()));
            String name = JOptionPane.showInputDialog(frame, "Enter Name:");
            if (dao.getAccount(acc) != null) {
                JOptionPane.showMessageDialog(frame, "Account already exists!");
                return;
            }
            boolean ok = dao.createAccount(acc, name, pin);
            JOptionPane.showMessageDialog(frame, ok ? "Account Created!" : "Failed!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Enter valid numbers!");
        }
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
