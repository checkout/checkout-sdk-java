package com.checkout.payments.request.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
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
public final class RequestIdSource extends AbstractRequestSource {

    /**
     * The payment instrument identifier.
     * [Required]
     * Pattern: ^(src)_(\w{26})$
     */
    private String id;

    /**
     * The card verification value (CVV) of the card.
     * [Optional]
     * min 3 characters, max 4 characters
     */
    private String cvv;

    /**
     * The payment method to be used.
     * [Optional]
     */
    @SerializedName("payment_method")
    private String paymentMethod;

    /**
     * Whether this payment instrument was previously stored for future use.
     * [Optional]
     */
    private Boolean stored;

    /**
     * Whether to store the payment instrument for future use.
     * [Optional]
     */
    @SerializedName("store_for_future_use")
    private Boolean storeForFutureUse;

    /**
     * The billing address associated with the payment instrument.
     * [Optional]
     */
    @SerializedName("billing_address")
    private Address billingAddress;

    /**
     * The phone number associated with the payment instrument.
     * [Optional]
     */
    private Phone phone;

    /**
     * The account holder information for the payment instrument.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    /**
     * Whether to update the stored instrument information with the data provided.
     * [Optional]
     */
    @SerializedName("allow_update")
    private Boolean allowUpdate;

    @Builder
    private RequestIdSource(final String id,
                            final String cvv,
                            final String paymentMethod,
                            final Boolean stored,
                            final Boolean storeForFutureUse,
                            final Address billingAddress,
                            final Phone phone,
                            final AccountHolder accountHolder,
                            final Boolean allowUpdate) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
        this.paymentMethod = paymentMethod;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.accountHolder = accountHolder;
        this.allowUpdate = allowUpdate;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}
