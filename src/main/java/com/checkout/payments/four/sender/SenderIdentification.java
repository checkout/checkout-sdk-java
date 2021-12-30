package com.checkout.payments.four.sender;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SenderIdentification {

    private SenderIdentificationType type;

    private String number;

    @SerializedName("issuing_country")
    private CountryCode issuingCountry;

}
