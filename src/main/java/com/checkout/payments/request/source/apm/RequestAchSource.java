package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestAchSource extends AbstractRequestSource {

    /**
     * The type of Direct Debit account.
     * [Required]
     * Enum: "savings" "checking" "cash"
     */
    private AccountType accountType;

    /**
     * The source country as an ISO 3166-1 alpha-2 code.
     * [Required]
     * min 2 characters, max 2 characters
     */
    private CountryCode country;

    /**
     * The account number of the Direct Debit account.
     * [Required]
     * min 4 characters, max 17 characters
     */
    private String accountNumber;

    /**
     * The bank code (ABA routing number) of the Direct Debit account.
     * [Required]
     * min 8 characters, max 9 characters
     */
    private String bankCode;

    /**
     * The account holder's personal information.
     * Supports date_of_birth and identification (SSN) fields.
     * [Required]
     */
    private AccountHolder accountHolder;

    @Builder
    private RequestAchSource(
            final AccountType accountType,
            final CountryCode country,
            final String accountNumber,
            final String bankCode,
            final AccountHolder accountHolder) {
        super(PaymentSourceType.ACH);
        this.accountType = accountType;
        this.country = country;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.accountHolder = accountHolder;
    }

    public RequestAchSource() {
        super(PaymentSourceType.ACH);
    }
}
