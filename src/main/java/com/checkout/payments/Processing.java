package com.checkout.payments;

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
public class Processing {

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

    private Boolean aft;

    @SerializedName("preferred_scheme")
    private PreferredSchema preferredScheme;

    @SerializedName("merchant_initiated_reason")
    private MerchantInitiatedReason merchantInitiatedReason;

    @SerializedName("product_type")
    private ProductType productType;

    @SerializedName("open_id")
    private String openId;

    @SerializedName("original_order_amount")
    private Long originalOrderAmount;

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

    private DLocal dlocal;

    //Deprecated fields

    @SerializedName("receipt_id")
    private String receiptId;

    private String mid;

    @SerializedName("senderInformation")
    private SenderInformation senderInformation;

    @SerializedName("skipExpiry")
    private Boolean skipExpiry;

}