package com.checkout.instruments;

import com.checkout.common.CustomerResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InstrumentCustomerResponse extends CustomerResponse {

    @SerializedName("default")
    private boolean isDefault;

}
