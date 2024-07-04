package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CompanyVerification {

    private CompanyVerificationType type;

    private String front;
}
