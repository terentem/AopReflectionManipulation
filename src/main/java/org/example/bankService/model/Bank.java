package org.example.bankService.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {

    private static final AtomicLong idCounter = new AtomicLong(1);

    private static List<Account> accounts = new ArrayList<>();


    public Account createAccount(Long taxId, boolean isAsset) {
        Long id = idCounter.getAndIncrement();
        System.out.println("id=" + id);
        Account newAccount = new Account(id, taxId, isAsset,0.00);
        accounts.add(newAccount);
        System.out.println("account created: " + accounts.getLast());
        return newAccount;
    }

    private static AtomicLong getIdCounter() {
        return idCounter;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Long getAccountByTaxId(Long taxId) {
        Long accountNumber = null;
        for (Account account : accounts) {
            if (account.taxId().equals(taxId)) {
                accountNumber = account.id();
                break;
            }
        }
        return accountNumber;
    }
    }
