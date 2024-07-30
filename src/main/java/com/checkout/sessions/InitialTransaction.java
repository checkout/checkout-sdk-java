package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InitialTransaction {
    
    @SerializedName("acs_transaction_id")
    private String acsTransactionId;

    @SerializedName("authentication_method")
    private String authenticationMethod;

    @SerializedName("authentication_timestamp")
    private String authenticationTimestamp;

    @SerializedName("authentication_data")
    private String authenticationData;

    @SerializedName("initial_session_id")
    private String initialSessionId;
    
}
