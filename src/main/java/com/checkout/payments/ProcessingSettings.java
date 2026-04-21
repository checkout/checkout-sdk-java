package com.checkout.payments;

import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.ShippingInfo;
import com.checkout.payments.previous.request.SenderInformation;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("order_id")
    private String orderId;

    /**
     * The total amount of sales tax on the total purchase amount.
     * [Optional]
     */
    @SerializedName("tax_amount")
    private Long taxAmount;

    /**
     * The surcharge amount applied to the transaction in minor currency units by the merchant.
     * [Optional]
     */
    @SerializedName("surcharge_amount")
    private Long surchargeAmount;

    /**
     * The discount amount applied to the transaction by the merchant.
     * [Optional]
     */
    @SerializedName("discount_amount")
    private Long discountAmount;

    /**
     * The total charges for any import or export duty included in the transaction.
     * [Optional]
     */
    @SerializedName("duty_amount")
    private Long dutyAmount;

    /**
     * The total freight or shipping and handling charges for the transaction.
     * [Optional]
     */
    @SerializedName("shipping_amount")
    private Long shippingAmount;

    /**
     * The tax amount on the freight or shipping and handling charges for the transaction.
     * [Optional]
     */
    @SerializedName("shipping_tax_amount")
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
    @SerializedName("preferred_scheme")
    private PreferredSchema preferredScheme;

    /**
     * Indicates the reason for a merchant-initiated payment request.
     * [Optional]
     * Enum: "Delayed_charge" "Resubmission" "No_show" "Reauthorization"
     */
    @SerializedName("merchant_initiated_reason")
    private MerchantInitiatedReason merchantInitiatedReason;

    /**
     * Unique number of the campaign this payment will run in. Required for Afterpay campaign invoices.
     * [Optional]
     */
    @SerializedName("campaign_id")
    private Long campaignId;

    /**
     * The product type of the payment. Required for wechatpay and sequra; optional for tamara.
     * [Optional]
     */
    @SerializedName("product_type")
    private ProductType productType;

    /**
     * The OpenID obtained from WeChat Web Authorization API. Required for Official Account or Mini Program payments.
     * [Optional]
     */
    @SerializedName("open_id")
    private String openId;

    /**
     * The original order amount when a payment is split. Indicates the full order price.
     * [Optional]
     */
    @SerializedName("original_order_amount")
    private Long originalOrderAmount;

    /**
     * Merchant receipt ID.
     * [Optional]
     * max 32 characters
     */
    @SerializedName("receipt_id")
    private String receiptId;

    /**
     * The client-side terminal type: APP, WAP (mobile browser), or WEB (PC browser).
     * [Optional]
     * Enum: "APP" "WAP" "WEB"
     */
    @SerializedName("terminal_type")
    private TerminalType terminalType;

    /**
     * The operating system type. Required when terminal_type is not WEB.
     * [Optional]
     * Enum: "ANDROID" "IOS"
     */
    @SerializedName("os_type")
    private OsType osType;

    /**
     * Invoice ID number.
     * [Optional]
     * max 127 characters
     */
    @SerializedName("invoice_id")
    private String invoiceId;

    /**
     * The label that overrides the business name in the PayPal account on PayPal pages.
     * [Optional]
     * max 127 characters
     */
    @SerializedName("brand_name")
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
    @SerializedName("shipping_preference")
    private ShippingPreference shippingPreference;

    /**
     * Property required by PayPal to determine the appropriate payment flow.
     * [Optional]
     * Enum: "pay_now" "continue"
     */
    @SerializedName("user_action")
    private UserAction userAction;

    /**
     * An array of key-value pairs with merchant-specific transaction context data for PayPal.
     * [Optional]
     */
    @SerializedName("set_transaction_context")
    private List<Map<String, String>> setTransactionContext;

    /**
     * Airline ticket and flight information for the payment.
     * [Optional]
     */
    @SerializedName("airline_data")
    private List<AirlineData> airlineData;

    /**
     * The accommodation data for hotel or lodging payments.
     * [Optional]
     */
    @SerializedName("accommodation_data")
    private List<AccommodationData> accommodationData;

    /**
     * A one-time password value for OTP-based payment flows.
     * [Optional]
     */
    @SerializedName("otp_value")
    private String otpValue;

    /**
     * The two-letter ISO country code of the purchase country.
     * [Optional]
     * max 2 characters
     */
    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    /**
     * Custom payment method IDs (Klarna promo codes) defining which payment options to show.
     * [Optional]
     */
    @SerializedName("custom_payment_method_ids")
    private List<String> customPaymentMethodIds;

    /**
     * A URL to notify the customer when the order has been created (used by some APMs).
     * [Optional]
     */
    @SerializedName("merchant_callback_url")
    private String merchantCallbackUrl;

    /**
     * The line of business that the payment is associated with.
     * [Optional]
     */
    @SerializedName("line_of_business")
    private String lineOfBusiness;

    /**
     * The number of days by which the shipping is delayed.
     * [Optional]
     */
    @SerializedName("shipping_delay")
    private Long shippingDelay;

    /**
     * Shipping information for the transaction.
     * [Optional]
     */
    @SerializedName("shipping_info")
    private List<ShippingInfo> shippingInfo;

    /**
     * Indicates whether to provision a network token for the payment.
     * [Optional]
     */
    @SerializedName("provision_network_token")
    private Boolean provisionNetworkToken;

    /**
     * Specifies the preferred type of Primary Account Number (PAN): fpan or dpan.
     * [Optional]
     * Enum: "fpan" "dpan"
     */
    @SerializedName("pan_preference")
    private PanProcessedType panPreference;

    /**
     * The ACH service type to use for the payment when source.type is ach.
     * [Optional]
     * Enum: "same_day" "standard"
     */
    @SerializedName("service_type")
    private AchServiceType serviceType;

    /**
     * The foreign retailer amount the merchant applied to the transaction, in minor currency units.
     * [Optional]
     */
    @SerializedName("foreign_retailer_amount")
    private Long foreignRetailerAmount;

    /**
     * A reconciliation identifier for the payment.
     * [Optional]
     */
    @SerializedName("reconciliation_id")
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
    @SerializedName("hub_model_origination_country")
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
    @SerializedName("senderInformation")
    private SenderInformation senderInformation;

    /**
     * Specifies whether to process the payment as credit or debit when a combo card is used.
     * [Optional]
     * Enum: "credit" "debit"
     */
    @SerializedName("card_type")
    private CardType cardType;

    /**
     * The unique identifier for a Visa-registered ramp provider. Must be alphanumeric.
     * [Optional]
     * Pattern: ^[a-zA-Z0-9]{1,15}$
     * max 15 characters
     */
    @SerializedName("affiliate_id")
    private String affiliateId;

    /**
     * The affiliate URL. Required if you are a Visa-registered ramp provider operating with affiliates.
     * [Optional]
     */
    @SerializedName("affiliate_url")
    private String affiliateUrl;

}
