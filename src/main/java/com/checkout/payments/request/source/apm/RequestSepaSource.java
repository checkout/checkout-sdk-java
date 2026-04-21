package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolder;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSepaSource extends AbstractRequestSource {

    /**
     * The account's country, as an ISO 3166-1 alpha-2 code.
     * [Required]
     */
    private CountryCode country;

    /**
     * The account holder's IBAN.
     * [Required]
     */
    @SerializedName("account_number")
    private String accountNumber;

    /**
     * The BIC/SWIFT code of the bank.
     * [Optional]
     */
    @SerializedName("bank_code")
    private String bankCode;

    /**
     * The account holder's account currency.
     * [Required]
     */
    private Currency currency;

    /**
     * The ID of the mandate.
     * [Optional]
     */
    @SerializedName("mandate_id")
    private String mandateId;

    /**
     * The type of mandate.
     * [Optional]
     * Enum: "Core" "B2B"
     */
    @SerializedName("mandate_type")
    private MandateType mandateType;

    /**
     * The date the mandate was signed, in the format yyyy-MM-dd.
     * [Optional]
     */
    @SerializedName("date_of_signature")
    private String dateOfSignature;

    /**
     * The account holder's personal information.
     * [Required]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestSepaSource(
            final CountryCode country,
            final String accountNumber,
            final String bankCode,
            final Currency currency,
            final String mandateId,
            final MandateType mandateType,
            final String dateOfSignature,
            final AccountHolder accountHolder
    ) {
        super(PaymentSourceType.SEPAV4);
        this.country = country;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.currency = currency;
        this.mandateId = mandateId;
        this.mandateType = mandateType;
        this.dateOfSignature = dateOfSignature;
        this.accountHolder = accountHolder;
    }

    public RequestSepaSource() {
        super(PaymentSourceType.SEPAV4);
    }

}
