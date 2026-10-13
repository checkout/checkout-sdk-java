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
     * The card number (without separators).
     * [Required]
     */
    private String number;

    /**
     * The expiry month of the card.
     * [Required]
     * min 1, max 12
     */
    private Integer expiryMonth;

    /**
     * 4-digit expiry year of the card.
     * [Required]
     */
    private Integer expiryYear;

    /**
     * The name of the cardholder.
     * [Optional]
     */
    private String name;

    /**
     * The card verification value/code. 3 digits, except for American Express (4 digits).
     * [Optional]
     * min 3 characters, max 4 characters
     */
    private String cvv;

    /**
     * Indicates whether this card is being submitted from your own stored card-on-file system, rather than
     * being entered by the customer at the time of payment. Set to true when you provide card details that
     * you already have on file for the customer.
     * [Optional]
     */
    private Boolean stored;

    /**
     * This must be set to true if you intend to reuse the payment credentials in subsequent payments.
     * Setting the field as false will mean that a payment instrument will not be included in the payment
     * response.
     * [Optional]
     */
    private Boolean storeForFutureUse;

    /**
     * The billing address of the cardholder.
     * [Optional]
     */
    private Address billingAddress;

    /**
     * The phone number of the cardholder.
     * [Optional]
     */
    private Phone phone;

    /**
     * Information about the account holder of the card.
     * [Optional]
     */
    private AccountHolder accountHolder;

    /**
     * Specifies whether to use the Real-Time Account Updater to update the card information.
     * [Optional]
     */
    private Boolean allowUpdate;

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
                              final AccountHolder accountHolder,
                              final Boolean allowUpdate) {
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
        this.allowUpdate = allowUpdate;
    }

    public RequestCardSource() {
        super(PaymentSourceType.CARD);
    }

}
