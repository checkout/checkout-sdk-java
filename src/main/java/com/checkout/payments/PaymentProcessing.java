package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class PaymentProcessing {

    @SerializedName("retrieval_reference_number")
    private String retrievalReferenceNumber;

    @SerializedName("acquirer_transaction_id")
    private String acquirerTransactionId;

    @SerializedName("acquirer_name")
    private String acquirerName;

    @SerializedName("acquirer_country_code")
    private CountryCode acquirerCountryCode;

    @SerializedName("recommendation_code")
    private String recommendationCode;
    
    private String scheme;

    @SerializedName("partner_merchant_advice_code") 
    private String partnerMerchantAdviceCode;
    
    @SerializedName("partner_response_code")
    private String partnerResponseCode;

    @SerializedName("partner_order_id")
    private String partnerOrderId;

    @SerializedName("partner_session_id")
    private String partnerSessionId;

    @SerializedName("partner_client_token")
    private String partnerClientToken;

    @SerializedName("partner_payment_id")
    private String partnerPaymentId;

    @SerializedName("pan_type_processed")
    private PanProcessedType panTypeProcessed;

    @SerializedName("continuation_payload")
    private String continuationPayload;

    private String pun;

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
    
    @SerializedName("surcharge_amount")
    private Long surchargeAmount;

    @SerializedName("cko_network_token_available")
    private Boolean ckoNetworkTokenAvailable;

    @SerializedName("provision_network_token")
    private Boolean provisionNetworkToken;

    @SerializedName("merchant_category_code")
    private String merchantCategoryCode;

    private Boolean aft;

}
