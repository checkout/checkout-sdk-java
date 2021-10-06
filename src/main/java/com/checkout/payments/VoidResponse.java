package com.checkout.payments;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class VoidResponse extends Resource {

    @SerializedName("action_id")
    private String actionId;

    private String reference;

}