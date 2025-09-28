import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TransactionHistoryGUI {
    public TransactionHistoryGUI(Account account) {
        AccountDAO dao = new AccountDAO();
        List<TransactionRecord> txns = dao.getTransactions(account.getAccNo());

        String[] cols = {"ID", "Type", "Amount", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (TransactionRecord t : txns) {
            model.addRow(new Object[]{t.getId(), t.getType(), t.getAmount(), t.getTimestamp()});
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JFrame frame = new JFrame("Transaction History");
        frame.setSize(500, 300);
        frame.add(scroll);
        frame.setVisible(true);
    }
}
