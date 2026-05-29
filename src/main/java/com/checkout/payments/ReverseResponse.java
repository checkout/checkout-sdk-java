package com.checkout.payments;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ReverseResponse extends Resource {

    @SerializedName("action_id")
    private String actionId;

    private String reference;

    /**
     * The payment action performed during the reversal.
     * Determined by the payment's state at the time of processing:
     * "Refund" if the payment has been fully or partially captured;
     * "Void" if the payment is in an Authorized state.
     * [Optional]
     */
    @SerializedName("action_type")
    private String actionType;

}
