package com.checkout.payments.response;

import com.checkout.common.CountryCode;
import com.checkout.payments.PreferredSchema;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class ProcessingData {

    @SerializedName("preferred_scheme")
    private PreferredSchema preferredScheme;

    @SerializedName("app_id")
    private String appId;

    @SerializedName("partner_customer_id")
    private String partnerCustomerId;

    @SerializedName("partner_payment_id")
    private String partnerPaymentId;

    @SerializedName("tax_amount")
    private Long taxAmount;

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    private String locale;

    @SerializedName("partner_order_id")
    private String partnerOrderId;

    @SerializedName("fraud_status")
    private String fraudStatus;

    @SerializedName("provider_authorized_payment_method")
    private ProviderAuthorizedPaymentMethod providerAuthorizedPaymentMethod;

    @SerializedName("custom_payment_method_ids")
    private List<String> customPaymentMethodIds;

    @SerializedName("partner_error_codes")
    private List<String> partnerErrorCodes;

    @SerializedName("partner_reason")
    private List<String> partnerReason;
}
