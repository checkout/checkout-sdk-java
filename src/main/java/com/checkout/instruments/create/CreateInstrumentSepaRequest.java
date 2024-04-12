package com.checkout.instruments.create;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.InstrumentType;
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
public final class CreateInstrumentSepaRequest extends CreateInstrumentRequest {

    @SerializedName("instrument_data")
    private InstrumentData instrumentData;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private CreateInstrumentSepaRequest(final InstrumentData instrumentData,
                                        final AccountHolder accountHolder) {
        super(InstrumentType.SEPA);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
    }

    public CreateInstrumentSepaRequest() {
        super(InstrumentType.SEPA);
    }
}
