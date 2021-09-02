package com.checkout.payments;

import com.checkout.common.beta.Currency;
import com.checkout.payments.apm.BalotoSource;
import com.checkout.payments.apm.BoletoSource;
import com.checkout.payments.apm.FawrySource;
import com.checkout.payments.apm.GiropaySource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@AllArgsConstructor
public class PaymentRequest<T extends RequestSource> {
    private final T source;
    private final T destination;
    private final Long amount;
    @NonNull
    private final String currency;
    private String paymentType;
    private Boolean merchantInitiated;
    private String reference;
    private String description;
    private Boolean capture;
    private CustomerRequest customer;
    private Instant captureOn;
    private BillingDescriptor billingDescriptor;
    private ShippingDetails shipping;
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;
    private String previousPaymentId;
    private RiskRequest risk;
    private String successUrl;
    private String failureUrl;
    private String paymentIp;
    private PaymentRecipient recipient;
    private Processing processing;
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();
    private String fundTransferType;
    private String processingChannelId;
    private AuthorizationType authorizationType;
    private MarketplaceData marketplace;

    /**
     * @deprecated Please use {@link PaymentRequest} constructor with {@link Currency} parameter enum type.
     */
    @Deprecated
    private PaymentRequest(final T sourceOrDestination, final String currency, final Long amount, final boolean isSource) {
        validateParams("sourceOrDestination", sourceOrDestination, "currency", currency, "amount", amount);
        this.source = isSource ? sourceOrDestination : null;
        this.destination = isSource ? null : sourceOrDestination;
        this.amount = amount;
        this.currency = currency;
        this.metadata = new HashMap<>();
    }

    private PaymentRequest(final T sourceOrDestination, final Currency currency, final Long amount, final boolean isSource) {
        validateParams("sourceOrDestination", sourceOrDestination, "currency", currency, "amount", amount);
        this.source = isSource ? sourceOrDestination : null;
        this.destination = isSource ? null : sourceOrDestination;
        this.amount = amount;
        this.currency = currency.name();
        this.metadata = new HashMap<>();
    }


    public static <T extends RequestSource> PaymentRequest<T> fromSource(final T source, final String currency, final Long amount) {
        return new PaymentRequest<>(source, currency, amount, true);
    }

    public static <T extends RequestSource> PaymentRequest<T> fromDestination(final T destination, final String currency, final Long amount) {
        return new PaymentRequest<>(destination, currency, amount, false);
    }

    public static PaymentRequest<BalotoSource> baloto(final BalotoSource balotoSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(balotoSource, currency, amount, true);
    }

    public static PaymentRequest<BoletoSource> boleto(final BoletoSource balotoSource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(balotoSource, currency, amount, true);
    }

    public static PaymentRequest<FawrySource> fawry(final FawrySource fawrySource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(fawrySource, currency, amount, true);
    }

    public static PaymentRequest<GiropaySource> giropay(final GiropaySource giropaySource, final Currency currency, final Long amount) {
        return new PaymentRequest<>(giropaySource, currency, amount, true);
    }

}
