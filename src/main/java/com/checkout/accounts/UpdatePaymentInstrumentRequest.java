package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class UpdatePaymentInstrumentRequest extends Headers{

    private String label;

    @SerializedName("default")
    private Boolean defaultDestination;
}
