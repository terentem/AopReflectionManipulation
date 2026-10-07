package org.example.bankService.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jdk.jfr.BooleanFlag;

public record RequestAccountDto(
        @Positive
        @NotNull
        Long taxId,
        @BooleanFlag
        boolean isAsset) {
}
