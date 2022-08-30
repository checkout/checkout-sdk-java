package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestKlarnaSource extends AbstractRequestSource {

    @SerializedName("authorization_token")
    private String authorizationToken;

    private String locale;

    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    @SerializedName("auto_capture")
    private Boolean autoCapture;

    @SerializedName("billing_address")
    private Map<String, String> billingAddress;

    @SerializedName("shipping_address")
    private Map<String, String> shippingAddress;

    @SerializedName("tax_amount")
    private Integer taxAmount;

    private List<Map<String, String>> products;

    private Map<String, String> customer;

    @SerializedName("merchant_reference1")
    private String merchantReference1;

    @SerializedName("merchant_reference2")
    private String merchantReference2;

    @SerializedName("merchant_data")
    private String merchantData;

    private Map<String, String> attachment;

    @SerializedName("custom_payment_method_ids")
    private List<Map<String, String>> customPaymentMethodIds;

    @Builder
    private RequestKlarnaSource(final String authorizationToken,
                                final String locale,
                                final CountryCode purchaseCountry,
                                final Boolean autoCapture,
                                final Map<String, String> billingAddress,
                                final Map<String, String> shippingAddress,
                                final Integer taxAmount,
                                final List<Map<String, String>> products,
                                final Map<String, String> customer,
                                final String merchantReference1,
                                final String merchantReference2,
                                final String merchantData,
                                final Map<String, String> attachment,
                                final List<Map<String, String>> customPaymentMethodIds) {
        super(PaymentSourceType.KLARNA);
        this.authorizationToken = authorizationToken;
        this.locale = locale;
        this.purchaseCountry = purchaseCountry;
        this.autoCapture = autoCapture;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.taxAmount = taxAmount;
        this.products = products;
        this.customer = customer;
        this.merchantReference1 = merchantReference1;
        this.merchantReference2 = merchantReference2;
        this.merchantData = merchantData;
        this.attachment = attachment;
        this.customPaymentMethodIds = customPaymentMethodIds;
    }

    public RequestKlarnaSource() {
        super(PaymentSourceType.KLARNA);
    }
}
