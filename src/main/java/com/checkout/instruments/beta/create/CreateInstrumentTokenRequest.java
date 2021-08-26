package com.checkout.instruments.beta.create;

import com.checkout.common.beta.AccountHolder;
import com.checkout.common.beta.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentTokenRequest extends CreateInstrumentRequest {

    private final String token;

    @SerializedName("account_holder")
    private final AccountHolder accountHolder;

    private final CreateCustomerInstrumentRequest customer;

    @Builder
    protected CreateInstrumentTokenRequest(final String token,
                                           final AccountHolder accountHolder,
                                           final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.TOKEN);
        this.token = token;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

}
