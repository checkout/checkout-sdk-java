package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class BankVerification {

    private BankVerificationType type;

    private String front;

}
