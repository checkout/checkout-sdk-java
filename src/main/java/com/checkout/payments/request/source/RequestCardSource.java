package com.checkout.payments.request.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestCardSource extends AbstractRequestSource {

    /**
     * The card number (without separators). Write-only.
     * [Optional]
     */
    private String number;

    /**
     * The expiry month of the card.
     * [Optional]
     */
    private Integer expiryMonth;

    /**
     * The expiry year of the card.
     * [Optional]
     */
    private Integer expiryYear;

    /**
     * The cardholder's name. Write-only.
     * [Optional]
     */
    private String name;

    /**
     * The card verification value/code. 3 digits, except for American Express (4 digits). Write-only.
     * [Optional]
     */
    private String cvv;

    /**
     * Set to true for payments that use stored card details. Write-only.
     * [Optional]
     */
    private Boolean stored;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments. Write-only.
     * [Optional]
     */
    private Boolean storeForFutureUse;

    /**
     * The payment source owner's billing address.
     * [Optional]
     */
    private Address billingAddress;

    /**
     * The payment source owner's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The card account holder's details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    @Builder
    private RequestCardSource(final String number,
                              final Integer expiryMonth,
                              final Integer expiryYear,
                              final String name,
                              final String cvv,
                              final Boolean stored,
                              final Boolean storeForFutureUse,
                              final Address billingAddress,
                              final Phone phone,
                              final AccountHolder accountHolder) {
        super(PaymentSourceType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.cvv = cvv;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.accountHolder = accountHolder;
    }

    public RequestCardSource() {
        super(PaymentSourceType.CARD);
    }

}
