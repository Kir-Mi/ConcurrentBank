import java.util.HashMap;
import java.util.Map;

public class ConcurrentBank {
    private final Map<Integer, BankAccount> accounts;
    private Integer accountNumber = 0;

    public ConcurrentBank() {
        this.accounts = new HashMap<>();
    }

    public synchronized BankAccount createAccount(double initialBalance) {
        ++accountNumber;
        accounts.put(accountNumber, new BankAccount(accountNumber, initialBalance));
        return accounts.get(accountNumber);
    }

    public synchronized void transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (!accounts.containsValue(fromAccount) || !accounts.containsValue(toAccount)) {
            throw new IllegalArgumentException("One of the accounts does not exist");
        }

        synchronized (fromAccount) {
            synchronized (toAccount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            }
        }
        System.out.println(String.format("Account %d = %.2f", fromAccount.getAccountNumber(), fromAccount.getBalance()));
        System.out.println(String.format("Account %d = %.2f", toAccount.getAccountNumber(), toAccount.getBalance()));
    }

    public synchronized double getTotalBalance() {
        double totalBalance = 0;
        for (BankAccount account : accounts.values()) {
            synchronized (account) {
                totalBalance += account.getBalance();
            }
        }
        return totalBalance;
    }
}
