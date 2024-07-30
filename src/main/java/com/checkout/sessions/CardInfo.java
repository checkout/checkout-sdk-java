package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class CardInfo {

    @SerializedName("instrument_id")
    private String instrumentId;

    @SerializedName("fingerprint")
    private String fingerprint;

    private SessionsCardMetadataResponse metadata;

}
