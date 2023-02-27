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

    @SerializedName("retrieval_reference_number")
    private String retrievalReferenceNumber;

    @SerializedName("partner_order_id")
    private String partnerOrderId;

    @SerializedName("partner_status")
    private String partnerStatus;

    @SerializedName("partner_transaction_id")
    private String partnerTransactionId;

    @SerializedName("partner_error_codes")
    private List<String> partnerErrorCodes;

    @SerializedName("partner_error_message")
    private String partnerErrorMessage;

    @SerializedName("partner_authorization_code")
    private String partnerAuthorizationCode;

    @SerializedName("partner_authorization_response_code")
    private String partnerAuthorizationResponseCode;

    @SerializedName("fraud_status")
    private String fraudStatus;

    @SerializedName("provider_authorized_payment_method")
    private ProviderAuthorizedPaymentMethod providerAuthorizedPaymentMethod;

    @SerializedName("custom_payment_method_ids")
    private List<String> customPaymentMethodIds;

    private Boolean aft;

    @SerializedName("merchant_category_code")
    private String merchantCategoryCode;

    @SerializedName("scheme_merchant_id")
    private String schemeMerchantId;

}
