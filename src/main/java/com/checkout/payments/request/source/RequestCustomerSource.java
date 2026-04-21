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
public final class RequestCustomerSource extends AbstractRequestSource {

    /**
     * The unique identifier of an existing customer.
     * [Optional]
     * Pattern: ^(cus)_(\w{26})$
     */
    private String id;

    /**
     * The customer's billing address.
     * [Optional]
     */
    @SerializedName("billing_address")
    private Address billingAddress;

    /**
     * The customer's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The account holder information for the customer.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    /**
     * Whether to update the stored customer information with the data provided.
     * [Optional]
     */
    @SerializedName("allow_update")
    private Boolean allowUpdate;

    @Builder
    private RequestCustomerSource(final String id,
                                  final Address billingAddress,
                                  final Phone phone,
                                  final AccountHolder accountHolder,
                                  final Boolean allowUpdate) {
        super(PaymentSourceType.CUSTOMER);
        this.id = id;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.accountHolder = accountHolder;
        this.allowUpdate = allowUpdate;
    }

    public RequestCustomerSource() {
        super(PaymentSourceType.CUSTOMER);
    }

}
