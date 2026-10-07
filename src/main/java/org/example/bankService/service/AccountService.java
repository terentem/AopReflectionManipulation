package org.example.bankService.service;

import org.example.bankService.model.Account;
import org.example.bankService.model.Bank;
import org.example.bankService.web.dto.RequestAccountDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final Bank bank;

    public AccountService(Bank bank) {
        this.bank = bank;
    }

    public List<Account> get() throws SQLException {
        return bank.getAccounts();
    }

    public List<Account> create(RequestAccountDto accountDto) throws SQLException {
        List<Account> tempList=new ArrayList<>();
        Account account=bank.createAccount(accountDto.taxId(), accountDto.isAsset());
        tempList.add(account);
        return tempList;
    }
}
