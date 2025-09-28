public class Account {
    private long accNo;
    private String name;
    private int pin;
    private long balance;

    public Account(long accNo, String name, int pin, long balance) {
        this.accNo = accNo;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public long getAccNo() { return accNo; }
    public String getName() { return name; }
    public int getPin() { return pin; }
    public long getBalance() { return balance; }

    @Override
    public String toString() {
        return "Account{" +
                "accNo=" + accNo +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
