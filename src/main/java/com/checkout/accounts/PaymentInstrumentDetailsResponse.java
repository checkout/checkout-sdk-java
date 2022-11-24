package com.checkout.accounts;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentInstrumentDetailsResponse extends Resource {

    private String id;

    private InstrumentStatus status;

    @SerializedName("instrument_id")
    private String instrumentId;

    private String label;

    private InstrumentType type;

    private Currency currency;

    private CountryCode country;

    @SerializedName("default")
    private Boolean defaultDestination;

    private InstrumentDocument document;
}
