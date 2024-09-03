package com.checkout.payments;

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

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("tax_amount")
    private Long taxAmount;

    @SerializedName("discount_amount")
    private Long discountAmount;

    @SerializedName("duty_amount")
    private Long dutyAmount;

    @SerializedName("shipping_amount")
    private Long shippingAmount;

    @SerializedName("shipping_tax_amount")
    private Long shippingTaxAmount;

    private boolean aft;

    @SerializedName("preferred_scheme")
    private PreferredSchema preferredScheme;

    @SerializedName("merchant_initiated_reason")
    private MerchantInitiatedReason merchantInitiatedReason;

    @SerializedName("campaign_id")
    private Long campaignId;

    @SerializedName("product_type")
    private ProductType productType;

    @SerializedName("open_id")
    private String openId;

    @SerializedName("original_order_amount")
    private Long originalOrderAmount;

    @SerializedName("receipt_id")
    private String receiptId;

    @SerializedName("terminal_type")
    private TerminalType terminalType;

    @SerializedName("os_type")
    private OsType osType;

    @SerializedName("invoice_id")
    private String invoiceId;

    @SerializedName("brand_name")
    private String brandName;

    private String locale;

    @SerializedName("shipping_preference")
    private ShippingPreference shippingPreference;

    @SerializedName("user_action")
    private UserAction userAction;

    @SerializedName("set_transaction_context")
    private List<Map<String, String>> setTransactionContext;

    @SerializedName("airline_data")
    private List<AirlineData> airlineData;

    @SerializedName("otp_value")
    private String otpValue;

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    @SerializedName("custom_payment_method_ids")
    private List<String> customPaymentMethodIds;

    @SerializedName("merchant_callback_url")
    private String merchantCallbackUrl;

    @SerializedName("line_of_business")
    private String lineOfBusiness;

    @SerializedName("shipping_delay")
    private Long shippingDelay;

    @SerializedName("shipping_info")
    private List<ShippingInfo> shippingInfo;

    @SerializedName("pan_preference")
    private PanProcessedType panPreference;

    //Previous
    private DLocalProcessingSettings dlocal;

    @SerializedName("senderInformation")
    private SenderInformation senderInformation;

}