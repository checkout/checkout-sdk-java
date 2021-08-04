package com.checkout;

import com.checkout.events.EventsClient;
import com.checkout.events.EventsClientImpl;
import com.checkout.instruments.InstrumentsClient;
import com.checkout.instruments.InstrumentsClientImpl;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.PaymentsClientImpl;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.hosted.HostedPaymentsClientImpl;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.payments.links.PaymentLinksClientImpl;
import com.checkout.sources.SourcesClient;
import com.checkout.sources.SourcesClientImpl;
import com.checkout.tokens.TokensClient;
import com.checkout.tokens.TokensClientImpl;
import com.checkout.webhooks.WebhooksClient;
import com.checkout.webhooks.WebhooksClientImpl;

public class CheckoutApiImpl implements CheckoutApi {

    private final PaymentsClient paymentsClient;
    private final SourcesClient sourcesClient;
    private final TokensClient tokensClient;
    private final WebhooksClient webhooksClient;
    private final EventsClient eventsClient;
    private final InstrumentsClient instrumentsClient;
    private final PaymentLinksClient paymentLinksClient;
    private final HostedPaymentsClient hostedPaymentsClient;

    public CheckoutApiImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        paymentsClient = new PaymentsClientImpl(apiClient, configuration);
        sourcesClient = new SourcesClientImpl(apiClient, configuration);
        tokensClient = new TokensClientImpl(apiClient, configuration);
        webhooksClient = new WebhooksClientImpl(apiClient, configuration);
        eventsClient = new EventsClientImpl(apiClient, configuration);
        instrumentsClient = new InstrumentsClientImpl(apiClient, configuration);
        paymentLinksClient = new PaymentLinksClientImpl(apiClient, configuration);
        hostedPaymentsClient = new HostedPaymentsClientImpl(apiClient, configuration);
    }

    public static CheckoutApi create(String secretKey, boolean useSandbox, String publicKey) {
        CheckoutConfiguration configuration = new CheckoutConfiguration(secretKey, useSandbox);
        configuration.setPublicKey(publicKey);

        ApiClient apiClient = new ApiClientImpl(configuration);
        return new CheckoutApiImpl(apiClient, configuration);
    }

    public static CheckoutApi create(String secretKey, String uri, String publicKey) {
        CheckoutConfiguration configuration = new CheckoutConfiguration(secretKey, uri);
        configuration.setPublicKey(publicKey);

        ApiClient apiClient = new ApiClientImpl(configuration);
        return new CheckoutApiImpl(apiClient, configuration);
    }

    @Override
    public PaymentsClient paymentsClient() {
        return paymentsClient;
    }

    @Override
    public SourcesClient sourcesClient() {
        return sourcesClient;
    }

    @Override
    public TokensClient tokensClient() {
        return tokensClient;
    }

    @Override
    public WebhooksClient webhooksClient() {
        return webhooksClient;
    }

    @Override
    public EventsClient eventsClient() {
        return eventsClient;
    }

    @Override
    public InstrumentsClient instrumentsClient() {
        return instrumentsClient;
    }

    @Override
    public PaymentLinksClient paymentLinksClient() { return paymentLinksClient; }

    @Override
    public HostedPaymentsClient hostedPaymentsClient() { return hostedPaymentsClient; }
}