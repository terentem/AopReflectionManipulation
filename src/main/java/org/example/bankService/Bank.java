package org.example.bankService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {

    private static final AtomicLong idCounter = new AtomicLong(1);

    private static List<Account> accounts = new ArrayList<>();


    public Account createAccount(Long taxId) {
        Long id = idCounter.getAndIncrement();
        System.out.println("id=" + id);
        Account newAccount=new Account(id, taxId, 0.00);
        accounts.add(newAccount);
        System.out.println("account created: "+accounts.getLast());
        return newAccount;
    }

    private static AtomicLong getIdCounter() {
        return idCounter;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
