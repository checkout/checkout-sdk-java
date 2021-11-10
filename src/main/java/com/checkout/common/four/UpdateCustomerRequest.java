package com.checkout.common.four;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class UpdateCustomerRequest {

    private String id;

    @SerializedName("default")
    private boolean defaultCustomer;

}
