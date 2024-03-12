public class BankAccount {
    private final Integer accountNumber;
    private double balance;

    public BankAccount(Integer accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }
}