package com.checkout.beta;

import com.checkout.customers.beta.CustomersClient;
import com.checkout.payments.beta.PaymentsClient;
import com.checkout.tokens.beta.TokensClient;

public interface CheckoutApi {

    TokensClient tokensClient();

    PaymentsClient paymentsClient();

    CustomersClient customersClient();

}