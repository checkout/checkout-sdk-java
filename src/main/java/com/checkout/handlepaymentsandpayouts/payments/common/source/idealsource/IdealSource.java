package com.checkout.handlepaymentsandpayouts.payments.common.source.idealsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ideal source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdealSource extends AbstractSource {

    /**
     * description
     * [Required]
     * &lt;= 27
     */
    private String description;

    /**
     * BIC (8 or 11-digits) BIC of the bank where the Consumer account is held.  If governing law prevents Issuers
     * outside the Netherlands from disclosing this information, field may be omitted.
     * [Required]
     * &lt;= 11
     */
    private String bic;

    /**
     * The IBAN of the Consumer Bank account used for payment.  If governing law prevents Issuers outside the
     * Netherlands  from disclosing this information, field may be omitted.
     * [Optional]
     * &lt;= 34
     */
    private String iban;

    /**
     * Name of the Consumer according to the name of the account used for payment.  In the exceptional case that the
     * consumerName cannot be retrieved by the Issuer,  this is filled with 'N/A'.  If governing law prevents Issuers
     * outside the Netherlands from disclosing this information, field may be omitted.
     * [Optional]
     */
    @SerializedName("account_holder")
    private String accountHolder;

    /**
     * Initializes a new instance of the IdealSource class.
     */
    @Builder
    private IdealSource(
            final String description,
            final String bic,
            final String iban,
            final String accountHolder
    ) {
        super(SourceType.IDEAL);
        this.description = description;
        this.bic = bic;
        this.iban = iban;
        this.accountHolder = accountHolder;
    }

    public IdealSource() {
        super(SourceType.IDEAL);
    }

}
