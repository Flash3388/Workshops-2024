package question5;

public class Question5 {

    // this program has some parts of bank application.
    // it contains a basic account and bank to store a bunch of
    // accounts.
    // the main does a bunch of stuff with these class, as
    // a way to test them. but there are problems.

    public static void main(String[] args) {
        Bank bank = new Bank();

        Account account = new Account();
        account.deposit(1000);
        bank.addAccount(account);

        Account account2 = new Account();
        account2.deposit(400);
        bank.addAccount(account2);
    }
}
