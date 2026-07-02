package com.checkout.payments.response;

import lombok.Data;

@Data
public final class ProviderAuthorizedPaymentMethod {

    private String type;

    private String description;

    private Long numberOfInstallments;

    private Long numberOfDays;
}
