package com.checkout.instruments;

import com.checkout.common.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateInstrumentRequest {

    private InstrumentType type;

    private String token;

    @SerializedName("account_holder")
    private InstrumentAccountHolder accountHolder;

    private InstrumentCustomerRequest customer;

}
