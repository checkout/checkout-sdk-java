package com.checkout;

import com.checkout.payments.PaymentsClient;
import com.checkout.sources.SourcesClient;
import com.checkout.tokens.TokensClient;
import com.checkout.webhooks.WebhooksClient;

public interface CheckoutApi {
    PaymentsClient paymentsClient();

    SourcesClient sourcesClient();

    TokensClient tokensClient();

    WebhooksClient webhooksClient();
}