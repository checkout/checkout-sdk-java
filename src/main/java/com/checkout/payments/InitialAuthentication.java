package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitialAuthentication {

    @SerializedName("acs_transaction_id")
    private String acsTransactionId;

    @SerializedName("authentication_method")
    private ThreeDSAuthenticationMethod authenticationMethod;
    
    @SerializedName("authentication_timestamp")
    private String authenticationTimestamp;

    @SerializedName("authentication_data")
    private String authenticationData;

    @SerializedName("initial_session_id")
    private String initialSessionId;
}
