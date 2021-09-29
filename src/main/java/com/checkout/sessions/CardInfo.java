package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public final class CardInfo {

    @SerializedName("instrument_id")
    private String instrumentId;

    @SerializedName("fingerprint")
    private String fingerprint;

    private Map<String, String> metadata;

}
