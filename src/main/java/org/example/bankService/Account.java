package org.example.bankService;

public record Account(
        Long id,
        Long taxId,
        Double balance) {
}
