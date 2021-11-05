package com.checkout.payments;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.payments.apm.BalotoSource;
import com.checkout.payments.apm.BoletoSource;
import com.checkout.payments.apm.FawrySource;
import com.checkout.payments.apm.GiropaySource;
import com.checkout.payments.apm.IdealSource;
import com.checkout.payments.apm.KlarnaSource;
import com.checkout.payments.apm.OxxoSource;
import com.checkout.payments.apm.PagoFacilSource;
import com.checkout.payments.apm.RapiPagoSource;
import com.checkout.payments.apm.SepaSource;
import com.checkout.payments.apm.SofortSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest<T extends RequestSource> {

    private T source;

    private PaymentDestination destination;

    private Long amount;

    @NonNull
    private Currency currency;

    @SerializedName("payment_type")
    private String paymentType;

    @SerializedName("merchant_initiated")
    private Boolean merchantInitiated;

    private String reference;

    private String description;

    private Boolean capture;

    private CustomerRequest customer;

    @SerializedName("capture_on")
    private Instant captureOn;

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

    private String fundTransferType;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("authorization_type")
    private AuthorizationType authorizationType;

    private Purpose purpose;

    private PaymentRequest(final T source, final Currency currency, final Long amount, final boolean capture) {
        validateParams("source", source, "currency", currency, "amount", amount, "capture", capture);
        this.source = source;
        this.amount = amount;
        this.currency = currency;
        this.capture = capture;
    }

    private PaymentRequest(final T source, final Currency currency, final Long amount, final String reference) {
        validateParams("source", source, "currency", currency, "amount", amount, "reference", reference);
        this.source = source;
        this.amount = amount;
        this.currency = currency;
        this.reference = reference;
    }

    public static <T extends RequestSource> PaymentRequest<T> fromSource(final T source, final Currency currency, final Long amount) {
        return new PaymentRequest<>(source, currency, amount, true);
    }

    public static <T extends RequestSource> PaymentRequest<T> fromDestination(final T destination, final Currency currency, final Long amount) {
        return new PaymentRequest<>(destination, currency, amount, false);
    }

    public static PaymentRequest<BalotoSource> baloto(final BalotoSource balotoSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(balotoSource, currency, amount, true);
    }

    public static PaymentRequest<BoletoSource> boleto(final BoletoSource boletoSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(boletoSource, currency, amount, true);
    }

    public static PaymentRequest<FawrySource> fawry(final FawrySource fawrySource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(fawrySource, currency, amount, true);
    }

    public static PaymentRequest<GiropaySource> giropay(final GiropaySource giropaySource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(giropaySource, currency, amount, true);
    }

    public static PaymentRequest<IdealSource> ideal(final IdealSource idealSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(idealSource, currency, amount, true);
    }

    public static PaymentRequest<OxxoSource> oxxo(final OxxoSource oxxoSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(oxxoSource, currency, amount, true);
    }

    public static PaymentRequest<PagoFacilSource> pagoFacil(final PagoFacilSource pagoFacilSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(pagoFacilSource, currency, amount, true);
    }

    public static PaymentRequest<RapiPagoSource> rapiPago(final RapiPagoSource rapiPagoSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(rapiPagoSource, currency, amount, true);
    }

    public static PaymentRequest<KlarnaSource> klarna(final KlarnaSource klarnaSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(klarnaSource, currency, amount, false);
    }

    public static PaymentRequest<SepaSource> sepa(final SepaSource sepaSource, final Currency currency, final Long amount, final String reference) {
        return new PaymentRequest<>(sepaSource, currency, amount, reference);
    }

    public static PaymentRequest<SofortSource> sofort(final Currency currency, final Long amount) {
        return new PaymentRequest<>(new SofortSource(), currency, amount, true);
    }

}
