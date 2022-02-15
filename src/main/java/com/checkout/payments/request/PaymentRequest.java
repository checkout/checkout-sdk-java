package com.checkout.payments.request;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.checkout.payments.request.source.apm.RequestBalotoSource;
import com.checkout.payments.request.source.apm.RequestBoletoSource;
import com.checkout.payments.request.source.apm.RequestFawrySource;
import com.checkout.payments.request.source.apm.RequestGiropaySource;
import com.checkout.payments.request.source.apm.RequestIdealSource;
import com.checkout.payments.request.source.apm.RequestKlarnaSource;
import com.checkout.payments.request.source.apm.RequestOxxoSource;
import com.checkout.payments.request.source.apm.RequestPagoFacilSource;
import com.checkout.payments.request.source.apm.RequestRapiPagoSource;
import com.checkout.payments.request.source.apm.RequestSepaSource;
import com.checkout.payments.request.source.apm.RequestSofortSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentRequest {

    private AbstractRequestSource source;

    private Long amount;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("merchant_initiated")
    private Boolean merchantInitiated;

    private String reference;

    private String description;

    private Boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    private CustomerRequest customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    @SerializedName("previous_payment_id")
    private String previousPaymentId;

    private RiskRequest risk;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    @SerializedName("payment_ip")
    private String paymentIp;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    private PaymentRequest(final AbstractRequestSource source, final Currency currency, final Long amount, final boolean capture) {
        validateParams("source", source, "currency", currency, "amount", amount, "capture", capture);
        this.source = source;
        this.amount = amount;
        this.currency = currency;
        this.capture = capture;
    }

    private PaymentRequest(final AbstractRequestSource source, final Currency currency, final Long amount, final String reference) {
        validateParams("source", source, "currency", currency, "amount", amount, "reference", reference);
        this.source = source;
        this.amount = amount;
        this.currency = currency;
        this.reference = reference;
    }

    public static PaymentRequest baloto(final RequestBalotoSource balotoSource, final Currency currency, final Long amount) {
        return new PaymentRequest(balotoSource, currency, amount, true);
    }

    public static PaymentRequest boleto(final RequestBoletoSource boletoSource, final Currency currency, final Long amount) {
        return new PaymentRequest(boletoSource, currency, amount, true);
    }

    public static PaymentRequest fawry(final RequestFawrySource fawrySource, final Currency currency, final Long amount) {
        return new PaymentRequest(fawrySource, currency, amount, true);
    }

    public static PaymentRequest giropay(final RequestGiropaySource giropaySource, final Currency currency, final Long amount) {
        return new PaymentRequest(giropaySource, currency, amount, true);
    }

    public static PaymentRequest ideal(final RequestIdealSource idealSource, final Currency currency, final Long amount) {
        return new PaymentRequest(idealSource, currency, amount, true);
    }

    public static PaymentRequest oxxo(final RequestOxxoSource oxxoSource, final Currency currency, final Long amount) {
        return new PaymentRequest(oxxoSource, currency, amount, true);
    }

    public static PaymentRequest pagoFacil(final RequestPagoFacilSource pagoFacilSource, final Currency currency, final Long amount) {
        return new PaymentRequest(pagoFacilSource, currency, amount, true);
    }

    public static PaymentRequest rapiPago(final RequestRapiPagoSource rapiPagoSource, final Currency currency, final Long amount) {
        return new PaymentRequest(rapiPagoSource, currency, amount, true);
    }

    public static PaymentRequest klarna(final RequestKlarnaSource klarnaSource, final Currency currency, final Long amount) {
        return new PaymentRequest(klarnaSource, currency, amount, false);
    }

    public static PaymentRequest sepa(final RequestSepaSource sepaSource, final Currency currency, final Long amount, final String reference) {
        return new PaymentRequest(sepaSource, currency, amount, reference);
    }

    public static PaymentRequest sofort(final Currency currency, final Long amount) {
        return new PaymentRequest(new RequestSofortSource(), currency, amount, true);
    }

}
