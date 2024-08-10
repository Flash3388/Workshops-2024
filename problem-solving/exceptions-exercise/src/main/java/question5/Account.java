package question5;

public class Account {
    private double balance;

    public Account() {
        this.balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (balance < amount) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }
}
