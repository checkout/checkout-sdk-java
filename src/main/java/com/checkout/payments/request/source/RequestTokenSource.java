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
public final class RequestTokenSource extends AbstractRequestSource {

    /**
     * The Checkout.com token value.
     * [Optional]
     */
    private String token;

    /**
     * The payment source owner's billing address.
     * [Optional]
     */
    @SerializedName("billing_address")
    private Address billingAddress;

    /**
     * The payment source owner's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * Set to true for payments that use stored card details. Write-only.
     * [Optional]
     */
    private Boolean stored;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments. Write-only.
     * [Optional]
     */
    @SerializedName("store_for_future_use")
    private Boolean storeForFutureUse;

    /**
     * The card account holder's details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestTokenSource(final String token,
                               final Address billingAddress,
                               final Phone phone,
                               final Boolean stored,
                               final Boolean storeForFutureUse,
                               final AccountHolder accountHolder) {
        super(PaymentSourceType.TOKEN);
        this.token = token;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
        this.accountHolder = accountHolder;
    }

    public RequestTokenSource() {
        super(PaymentSourceType.TOKEN);
    }

}
