package org.example.bankService.context;

import org.example.bankService.model.Bank;
import org.example.bankService.service.AccountService;
import org.example.bankService.web.controller.AccountController;

public class AccountContext {

    Bank bank = new Bank();
    AccountService accountService = new AccountService(bank);
    AccountController accountcontroller = new AccountController(accountService);

    public AccountController getAccountcontroller() {
        return accountcontroller;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
