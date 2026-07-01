package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
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

    private String authorizationToken;

    private String locale;

    private CountryCode purchaseCountry;

    private Boolean autoCapture;

    private Map<String, String> billingAddress;

    private Map<String, String> shippingAddress;

    private Integer taxAmount;

    private List<Map<String, String>> products;

    private Map<String, String> customer;

    private String merchantReference1;

    private String merchantReference2;

    private String merchantData;

    private Map<String, String> attachment;

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
