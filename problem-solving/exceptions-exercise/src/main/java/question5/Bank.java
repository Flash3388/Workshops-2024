package question5;

import java.util.ArrayList;

public class Bank {

    private final ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public Account getAccount(int index) {
        return accounts.get(index);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(int index) {
        accounts.remove(index);
    }
}
