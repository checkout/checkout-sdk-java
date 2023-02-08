package com.checkout.payments.request.source.apm;

import com.checkout.common.*;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSepaSource extends AbstractRequestSource {

    private CountryCode country;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_code")
    private String bankCode;

    private Currency currency;

    @SerializedName("mandate_id")
    private String mandateId;

    @SerializedName("date_of_signature")
    private String dateOfSignature;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestSepaSource(
            final CountryCode country,
            final String accountNumber,
            final String bankCode,
            final Currency currency,
            final String mandateId,
            final String dateOfSignature,
            final AccountHolder accountHolder
    ) {
        super(PaymentSourceType.SEPAV4);
        this.country = country;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.currency = currency;
        this.mandateId = mandateId;
        this.dateOfSignature = dateOfSignature;
        this.accountHolder = accountHolder;
    }

    public RequestSepaSource() {
        super(PaymentSourceType.SEPAV4);
    }

}
