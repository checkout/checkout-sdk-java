package com.checkout.payments.response;

import com.checkout.common.CountryCode;
import com.checkout.payments.AccommodationData;
import com.checkout.payments.AirlineData;
import com.checkout.payments.PanProcessedType;
import com.checkout.payments.PreferredSchema;
import lombok.Data;

import java.util.List;

@Data
public final class ProcessingData {

    private PreferredSchema preferredScheme;

    private String appId;

    private String partnerCustomerId;

    private String partnerPaymentId;

    private Long taxAmount;

    private CountryCode purchaseCountry;

    private String locale;

    private String retrievalReferenceNumber;

    private String partnerOrderId;

    private String partnerStatus;

    private String partnerTransactionId;

    private List<String> partnerErrorCodes;

    private String partnerErrorMessage;

    private String partnerAuthorizationCode;

    private String partnerAuthorizationResponseCode;

    private String fraudStatus;

    private ProviderAuthorizedPaymentMethod providerAuthorizedPaymentMethod;

    private List<String> customPaymentMethodIds;

    private Boolean aft;

    private String merchantCategoryCode;

    private String schemeMerchantId;

    private PanProcessedType panTypeProcessed;

    private Boolean ckoNetworkTokenAvailable;

    /**
     * Indicates whether the {@code fallback_source} field was used for the payment.
     * [Optional]
     */
    private Boolean fallbackSourceUsed;

    /**
     * A high-level failure category returned by the payment provider when a payment is declined or fails.
     * Not all payment methods return this field.
     * [Optional]
     */
    private String failureCode;

    /**
     * The 6-digit partner code returned by the payment provider. Returned when {@code source.type} is {@code blik}.
     * [Optional]
     * Pattern: ^\d{6}$
     * 6 characters
     */
    private String partnerCode;

    /**
     * The raw response code returned by the payment provider when a payment is declined or fails.
     * Not all payment methods return this field.
     * [Optional]
     */
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
    private String partnerFraudStatus;

    /**
     * The Mastercard Merchant Advice Code (MAC), which contains additional information about the
     * transaction. For example, the MAC can inform you if the transaction was performed using a
     * consumer non-reloadable prepaid card or a consumer single-use virtual card. For declined
     * transactions, the MAC also indicates whether the payment can be retried and how long to wait.
     * [Optional]
     */
    private String partnerMerchantAdviceCode;

    /**
     * Contains information about the accommodation booked by the customer.
     * [Optional]
     */
    private List<AccommodationData> accommodationData;

    /**
     * Contains information about the airline ticket and flights booked by the customer.
     * [Optional]
     */
    private List<AirlineData> airlineData;

    /**
     * The scheme transaction link identifier. Returned for Mastercard transactions when the scheme
     * provides a link identifier that ties together related transactions on the network
     * (see Mastercard Transaction Link Identifier documentation).
     * [Optional]
     */
    private String schemeTransactionLinkId;

}
