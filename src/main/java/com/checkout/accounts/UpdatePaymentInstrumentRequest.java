package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePaymentInstrumentRequest {

    private String label;

    @SerializedName("default")
    private Boolean defaultDestination;

    private Headers headers;

}
