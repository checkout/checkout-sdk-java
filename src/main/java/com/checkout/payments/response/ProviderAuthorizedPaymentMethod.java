package com.checkout.payments.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class ProviderAuthorizedPaymentMethod {

    private String type;

    private String description;

    @SerializedName("number_of_installments")
    private Long numberOfInstallments;

    @SerializedName("number_of_days")
    private Long numberOfDays;
}
