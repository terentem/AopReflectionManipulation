package org.example.bankService.web.controller;

import jakarta.validation.ValidatorFactory;
import org.example.bankService.model.Account;
import org.example.bankService.service.AccountService;
import org.example.bankService.web.dto.RequestAccountDto;
import org.example.bankService.web.dto.ResponseAccountDto;
import org.example.bankService.web.dtoValidation.Validation;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<ResponseAccountDto> read() throws IOException, SQLException {
        List<Account> result = accountService.get();
        List<ResponseAccountDto> getResponseDto = result.stream().map(ResponseAccountDto::toGteDto).toList();
        //log.info("GET reply  {}", getResponseDto);
        return getResponseDto;
    }

    public List<ResponseAccountDto> create(RequestAccountDto requestAccountDto) throws IOException, SQLException {
        Validation.validateRequestAccountDto(requestAccountDto);
        List<Account> result = accountService.create(requestAccountDto);
        List<ResponseAccountDto> getResponseDto = result.stream().map(ResponseAccountDto::toGteDto).toList();
        //log.info("POST http response= {}", getResponseDto);

        return getResponseDto;

    }
}
