package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;

public class CompellingEvidence {

    @SerializedName("merchandise_or_service")
    private String merchandiseOrService;

    @SerializedName("merchandise_or_service_desc")
    private String merchandiseOrServiceDesc;

    @SerializedName("merchandise_or_service_provided_date")
    private Instant merchandiseOrServiceProvidedDate;

    @SerializedName("shipping_delivery_status")
    private ShippingDeliveryStatusType shippingDeliveryStatus;

    @SerializedName("tracking_information")
    private TrackingInformationType trackingInformation;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("shipping_address")
    private ShippingAddress shippingAddress;

    @SerializedName("historical_transactions")
    private List<HistoricalTransactions> historicalTransactions;

}
