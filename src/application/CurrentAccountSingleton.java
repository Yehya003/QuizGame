package application;

import application.model.Account;

public class CurrentAccountSingleton {

    private static CurrentAccountSingleton ourInstance;
    private Account account = null;

    private CurrentAccountSingleton() {
    }

    public static CurrentAccountSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new CurrentAccountSingleton();
        }
        return ourInstance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
