package com.checkout.reconciliation.previous;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessingFeesBreakdown {

    @SerializedName("interchange_fees")
    private Integer interchangeFees;

    @SerializedName("scheme_and_other_network_fees")
    private Integer schemeAndOtherNetworkFees;

    @SerializedName("premium_and_apm_fees")
    private Integer premiumAndApmFees;

    @SerializedName("chargeback_fees")
    private Integer chargebackFees;

    @SerializedName("payout_to_card_fees")
    private Integer payoutToCardFees;

    @SerializedName("payment_gateway_fees")
    private Integer paymentGatewayFees;
}
