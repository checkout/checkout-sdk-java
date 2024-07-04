package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class FinancialVerification {

    private FinancialVerificationType type;

    private String front;

}
