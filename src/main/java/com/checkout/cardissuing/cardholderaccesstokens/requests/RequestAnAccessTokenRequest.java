package com.checkout.cardissuing.cardholderaccesstokens.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RequestAnAccessTokenRequest {

    @SerializedName("grant_type")
    private String grantType;

    /**
     * Access key ID
     */
    @SerializedName("client_id")
    private String clientId;

    /**
     * Access key secret
     */
    @SerializedName("client_secret")
    private String clientSecret;

    /**
     * The cardholder's unique identifier
     */
    @SerializedName("cardholder_id")
    private String cardholderId;

    /**
     * Specifies if the request is for a single-use token. Single-use tokens are required for sensitive endpoints
     */
    @SerializedName("single_use")
    private Boolean singleUse;

    public List<NameValuePair> toFormParams() {
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", grantType));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("cardholder_id", cardholderId));
        if (singleUse != null) {
            params.add(new BasicNameValuePair("single_use", singleUse.toString()));
        }
        return params;
    }

}
