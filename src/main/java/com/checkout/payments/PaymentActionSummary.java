package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class PaymentActionSummary {

    private String id;

    private ActionType type;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("response_summary")
    private String responseSummary;

}
