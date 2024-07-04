package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TaxVerification {

    private TaxVerificationType type;

    private String front;

}
