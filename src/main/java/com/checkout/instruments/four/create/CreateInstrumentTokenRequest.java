package com.checkout.instruments.four.create;

import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.InstrumentType;
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
public final class CreateInstrumentTokenRequest extends CreateInstrumentRequest {

    private String token;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    private CreateCustomerInstrumentRequest customer;

    @Builder
    private CreateInstrumentTokenRequest(final String token,
                                         final AccountHolder accountHolder,
                                         final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.TOKEN);
        this.token = token;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public CreateInstrumentTokenRequest() {
        super(InstrumentType.TOKEN);
    }

}
