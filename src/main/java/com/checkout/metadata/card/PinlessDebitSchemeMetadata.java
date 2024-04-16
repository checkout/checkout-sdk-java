package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PinlessDebitSchemeMetadata {

    @SerializedName("network_id")
    private String networkId;

    @SerializedName("network_description")
    private String networkDescription;

    @SerializedName("bill_pay_indicator")
    private Boolean billPayIndicator;

    @SerializedName("ecommerce_indicator")
    private Boolean ecommerceIndicator;

    @SerializedName("interchange_fee_indicator")
    private String InterchangeFeeIndicator;

    @SerializedName("money_transfer_indicator")
    private Boolean moneyTransferIndicator;

    @SerializedName("token_indicator")
    private Boolean tokenIndicator;

}
