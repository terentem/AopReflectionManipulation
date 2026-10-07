package org.example.bankService.web.dto;

import org.example.bankService.model.Account;

public record ResponseAccountDto(Long id,
                                 Long taxId,
                                 boolean isAsset,
                                 Double balance) {

    public static ResponseAccountDto toGteDto(Account account) {
        return new ResponseAccountDto(
                account.id(),
                account.taxId(),
                account.isAsset(),
                account.balance()
        );
    }
}
