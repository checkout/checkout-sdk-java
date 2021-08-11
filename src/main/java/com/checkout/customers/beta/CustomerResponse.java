package com.checkout.customers.beta;

import com.checkout.common.beta.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public final class CustomerResponse {

    private String id;

    private String email;

    @SerializedName("default")
    private String defaultId;

    private String name;

    private Phone phone;

    private Map<String, Object> metadata;

}
