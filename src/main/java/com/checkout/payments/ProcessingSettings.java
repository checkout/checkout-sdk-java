package com.checkout.payments;

import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.ShippingInfo;
import com.checkout.payments.previous.request.SenderInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProcessingSettings {

    /**
     * The cardholder-provided purchase order or invoice number.
     * [Optional]
     * max 15 characters
     */
    private String orderId;

    /**
     * The total amount of sales tax on the total purchase amount.
     * [Optional]
     */
    private Long taxAmount;

    /**
     * The surcharge amount applied to the transaction in minor currency units by the merchant.
     * [Optional]
     */
    private Long surchargeAmount;

    /**
     * The discount amount applied to the transaction by the merchant.
     * [Optional]
     */
    private Long discountAmount;

    /**
     * The total charges for any import or export duty included in the transaction.
     * [Optional]
     */
    private Long dutyAmount;

    /**
     * The total freight or shipping and handling charges for the transaction.
     * [Optional]
     */
    private Long shippingAmount;

    /**
     * The tax amount on the freight or shipping and handling charges for the transaction.
     * [Optional]
     */
    private Long shippingTaxAmount;

    /**
     * Indicates if the payment is an Account Funding Transaction (AFT).
     * [Optional]
     */
    private boolean aft;

    /**
     * The preferred scheme for co-badged card payment processing.
     * [Optional]
     * Enum: "mastercard" "visa" "cartes_bancaires"
     */
    private PreferredSchema preferredScheme;

    /**
     * Indicates the reason for a merchant-initiated payment request.
     * [Optional]
     * Enum: "Delayed_charge" "Resubmission" "No_show" "Reauthorization"
     */
    private MerchantInitiatedReason merchantInitiatedReason;

    /**
     * Unique number of the campaign this payment will run in. Required for Afterpay campaign invoices.
     * [Optional]
     */
    private Long campaignId;

    /**
     * The product type of the payment. Required for wechatpay and sequra; optional for tamara.
     * [Optional]
     */
    private ProductType productType;

    /**
     * The OpenID obtained from WeChat Web Authorization API. Required for Official Account or Mini Program payments.
     * [Optional]
     */
    private String openId;

    /**
     * The original order amount when a payment is split. Indicates the full order price.
     * [Optional]
     */
    private Long originalOrderAmount;

    /**
     * Merchant receipt ID.
     * [Optional]
     * max 32 characters
     */
    private String receiptId;

    /**
     * The client-side terminal type: APP, WAP (mobile browser), or WEB (PC browser).
     * [Optional]
     * Enum: "APP" "WAP" "WEB"
     */
    private TerminalType terminalType;

    /**
     * The operating system type. Required when terminal_type is not WEB.
     * [Optional]
     * Enum: "ANDROID" "IOS"
     */
    private OsType osType;

    /**
     * Invoice ID number.
     * [Optional]
     * max 127 characters
     */
    private String invoiceId;

    /**
     * The label that overrides the business name in the PayPal account on PayPal pages.
     * [Optional]
     * max 127 characters
     */
    private String brandName;

    /**
     * The language and region of the customer in ISO 639-2 format (e.g. en-US).
     * [Optional]
     * Pattern: ^[a-z]{2}(?:-[A-Z][a-z]{3})?(?:-(?:[A-Z]{2}))?$
     * min 2 characters, max 10 characters
     */
    private String locale;

    /**
     * Shipping preference for PayPal payments.
     * [Optional]
     * Enum: "no_shipping" "set_provided_address" "get_from_file"
     */
    private ShippingPreference shippingPreference;

    /**
     * Property required by PayPal to determine the appropriate payment flow.
     * [Optional]
     * Enum: "pay_now" "continue"
     */
    private UserAction userAction;

    /**
     * An array of key-value pairs with merchant-specific transaction context data for PayPal.
     * [Optional]
     */
    private List<Map<String, String>> setTransactionContext;

    /**
     * Airline ticket and flight information for the payment.
     * [Optional]
     */
    private List<AirlineData> airlineData;

    /**
     * The accommodation data for hotel or lodging payments.
     * [Optional]
     */
    private List<AccommodationData> accommodationData;

    /**
     * A one-time password value for OTP-based payment flows.
     * [Optional]
     */
    private String otpValue;

    /**
     * The two-letter ISO country code of the purchase country.
     * [Optional]
     * max 2 characters
     */
    private CountryCode purchaseCountry;

    /**
     * Custom payment method IDs (Klarna promo codes) defining which payment options to show.
     * [Optional]
     */
    private List<String> customPaymentMethodIds;

    /**
     * A URL to notify the customer when the order has been created (used by some APMs).
     * [Optional]
     */
    private String merchantCallbackUrl;

    /**
     * The line of business that the payment is associated with.
     * [Optional]
     */
    private String lineOfBusiness;

    /**
     * The number of days by which the shipping is delayed.
     * [Optional]
     */
    private Long shippingDelay;

    /**
     * Shipping information for the transaction.
     * [Optional]
     */
    private List<ShippingInfo> shippingInfo;

    /**
     * Indicates whether to provision a network token for the payment.
     * [Optional]
     */
    private Boolean provisionNetworkToken;

    /**
     * Specifies the preferred type of Primary Account Number (PAN): fpan or dpan.
     * [Optional]
     * Enum: "fpan" "dpan"
     */
    private PanProcessedType panPreference;

    /**
     * The ACH service type to use for the payment when source.type is ach.
     * [Optional]
     * Enum: "same_day" "standard"
     */
    private AchServiceType serviceType;

    /**
     * The foreign retailer amount the merchant applied to the transaction, in minor currency units.
     * [Optional]
     */
    private Long foreignRetailerAmount;

    /**
     * A reconciliation identifier for the payment.
     * [Optional]
     */
    private String reconciliationId;

    /**
     * The aggregator information for this payment.
     * [Optional]
     */
    private Aggregator aggregator;

    /**
     * The origination country for hub model payments.
     * [Optional]
     */
    private CountryCode hubModelOriginationCountry;

    /**
     * DLocal-specific processing settings. Used for Previous API only.
     * [Optional]
     */
    private DLocalProcessingSettings dlocal;

    /**
     * Sender information. Used for Previous API only.
     * [Optional]
     */
    private SenderInformation senderInformation;

    /**
     * Specifies whether to process the payment as credit or debit when a combo card is used.
     * [Optional]
     * Enum: "credit" "debit"
     */
    private CardType cardType;

    /**
     * The unique identifier for a Visa-registered ramp provider. Must be alphanumeric.
     * [Optional]
     * Pattern: ^[a-zA-Z0-9]{1,15}$
     * max 15 characters
     */
    private String affiliateId;

    /**
     * The affiliate URL. Required if you are a Visa-registered ramp provider operating with affiliates.
     * [Optional]
     */
    private String affiliateUrl;

}
