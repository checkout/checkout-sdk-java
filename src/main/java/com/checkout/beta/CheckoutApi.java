package com.checkout.beta;

import com.checkout.customers.beta.CustomersClient;
import com.checkout.disputes.beta.DisputesClient;
import com.checkout.instruments.beta.InstrumentsClient;
import com.checkout.payments.beta.PaymentsClient;
import com.checkout.tokens.beta.TokensClient;

public interface CheckoutApi {

    TokensClient tokensClient();

    PaymentsClient paymentsClient();

    CustomersClient customersClient();

    DisputesClient disputesClient();

    InstrumentsClient instrumentsClient();

}
