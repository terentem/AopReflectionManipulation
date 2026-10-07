package org.example.bankService.model;

public record Account(
        Long id,
        Long taxId,
        boolean isAsset,
        Double balance) {
}
