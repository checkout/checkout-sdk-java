package com.checkout.reports;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AccountResponse {

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("entity_id")
    private String entityId;
}
