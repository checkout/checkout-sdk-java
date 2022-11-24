package com.checkout.accounts;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInstrumentRequest {

    private String label;

    private InstrumentType type;

    private Currency currency;

    private CountryCode country;

    @SerializedName("default")
    private Boolean defaultDestination;

    private InstrumentDocument document;

    @SerializedName("instrument_details")
    private InstrumentDetails instrumentDetails;

}
