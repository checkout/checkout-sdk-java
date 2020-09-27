package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CustomerResponse {
    private String id;
    private String email;
    private String name;
    @SerializedName("default")
    private boolean isDefault;
}
