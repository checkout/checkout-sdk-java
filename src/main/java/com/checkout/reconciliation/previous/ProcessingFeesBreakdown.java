package com.checkout.reconciliation.previous;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProcessingFeesBreakdown {

    private Integer interchangeFees;

    private Integer schemeAndOtherNetworkFees;

    private Integer premiumAndApmFees;

    private Integer chargebackFees;

    private Integer payoutToCardFees;

    private Integer paymentGatewayFees;
}
