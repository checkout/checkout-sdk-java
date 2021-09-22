package com.checkout.instruments.four.update;

import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.InstrumentType;
import com.checkout.common.four.UpdateCustomerRequest;
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
public final class UpdateInstrumentCardRequest extends UpdateInstrumentRequest {

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    // TODO Implement specific UpdateCardAccountHolder domain when it's available
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    private UpdateCustomerRequest customer;

    @Builder
    private UpdateInstrumentCardRequest(final Integer expiryMonth,
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

    public UpdateInstrumentCardRequest(final InstrumentType type) {
        super(InstrumentType.CARD);
    }

}
