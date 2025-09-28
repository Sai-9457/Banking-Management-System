import java.sql.Timestamp;

public class TransactionRecord {
    private long id;
    private long accNo;
    private String type; // DEPOSIT or WITHDRAW
    private long amount;
    private Timestamp timestamp;

    public TransactionRecord(long id, long accNo, String type, long amount, Timestamp timestamp) {
        this.id = id;
        this.accNo = accNo;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    public long getId() { return id; }
    public long getAccNo() { return accNo; }
    public String getType() { return type; }
    public long getAmount() { return amount; }
    public Timestamp getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", accNo=" + accNo +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
