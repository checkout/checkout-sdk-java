package com.checkout.issuing.cards.requests.update;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuingCardMetadata {

    @SerializedName("udf1")
    private String udf1;

    @SerializedName("udf2")
    private String udf2;

    @SerializedName("udf3")
    private String udf3;

    @SerializedName("udf4")
    private String udf4;

    @SerializedName("udf5")
    private String udf5;
}