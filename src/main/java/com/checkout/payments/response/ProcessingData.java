package com.checkout.payments.response;

import com.checkout.common.CountryCode;
import com.checkout.payments.AccommodationData;
import com.checkout.payments.AirlineData;
import com.checkout.payments.PanProcessedType;
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

    @SerializedName("pan_type_processed")
    private PanProcessedType panTypeProcessed;

    @SerializedName("cko_network_token_available")
    private Boolean ckoNetworkTokenAvailable;

    /**
     * Indicates whether the {@code fallback_source} field was used for the payment.
     * [Optional]
     */
    @SerializedName("fallback_source_used")
    private Boolean fallbackSourceUsed;

    /**
     * A high-level failure category returned by the payment provider when a payment is declined or fails.
     * Not all payment methods return this field.
     * [Optional]
     */
    @SerializedName("failure_code")
    private String failureCode;

    /**
     * The 6-digit partner code returned by the payment provider. Returned when {@code source.type} is {@code blik}.
     * [Optional]
     * Pattern: ^\d{6}$
     * 6 characters
     */
    @SerializedName("partner_code")
    private String partnerCode;

    /**
     * The raw response code returned by the payment provider when a payment is declined or fails.
     * Not all payment methods return this field.
     * [Optional]
     */
    @SerializedName("partner_response_code")
    private String partnerResponseCode;

    /**
     * The scheme on which the payment was authorized. This may differ from the card's scheme used
     * for the payment if the card is co-badged and the payment was authorized on a different network.
     * [Optional] readOnly
     */
    private String scheme;

    /**
     * Partner fraud status. If the status is {@code Pending}, and the merchant captures before it
     * changes to {@code Accepted}, the risk of the transaction is solely on the merchant.
     * [Optional]
     */
    @SerializedName("partner_fraud_status")
    private String partnerFraudStatus;

    /**
     * The Mastercard Merchant Advice Code (MAC), which contains additional information about the
     * transaction. For example, the MAC can inform you if the transaction was performed using a
     * consumer non-reloadable prepaid card or a consumer single-use virtual card. For declined
     * transactions, the MAC also indicates whether the payment can be retried and how long to wait.
     * [Optional]
     */
    @SerializedName("partner_merchant_advice_code")
    private String partnerMerchantAdviceCode;

    /**
     * Contains information about the accommodation booked by the customer.
     * [Optional]
     */
    @SerializedName("accommodation_data")
    private List<AccommodationData> accommodationData;

    /**
     * Contains information about the airline ticket and flights booked by the customer.
     * [Optional]
     */
    @SerializedName("airline_data")
    private List<AirlineData> airlineData;

    /**
     * The scheme transaction link identifier. Returned for Mastercard transactions when the scheme
     * provides a link identifier that ties together related transactions on the network
     * (see Mastercard Transaction Link Identifier documentation).
     * [Optional]
     */
    @SerializedName("scheme_transaction_link_id")
    private String schemeTransactionLinkId;

}
