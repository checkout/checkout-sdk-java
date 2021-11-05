package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProcessingSettings {

    private Boolean aft;

    private String mid;

    @SerializedName("senderInformation")
    private SenderInformation senderInformation;

    @SerializedName("skipExpiry")
    private Boolean skipExpiry;

    private DLocal dlocal;

}