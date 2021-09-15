package com.checkout.instruments.four.update;

import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.InstrumentType;
import com.checkout.common.four.UpdateCustomerRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentCardRequest extends UpdateInstrumentRequest {

    @SerializedName("expiry_month")
    private final Integer expiryMonth;

    @SerializedName("expiry_year")
    private final Integer expiryYear;

    private final String name;

    // TODO Implement specific UpdateCardAccountHolder domain when it's available
    @SerializedName("account_holder")
    private final AccountHolder accountHolder;

    private final UpdateCustomerRequest customer;

    @Builder
    protected UpdateInstrumentCardRequest(final Integer expiryMonth,
                                          final Integer expiryYear,
                                          final String name,
                                          final AccountHolder accountHolder,
                                          final UpdateCustomerRequest customer) {
        super(InstrumentType.CARD);
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

}
