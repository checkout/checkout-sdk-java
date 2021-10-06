package com.checkout.apm.klarna;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KlarnaPaymentsTestIT extends SandboxTestFixture {

    KlarnaPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldCreateAndGetKlarnaSession() {

        final CreditSessionRequest creditSessionRequest = CreditSessionRequest.builder()
                .purchaseCountry(CountryCode.GB)
                .currency(Currency.GBP)
                .locale("en-GB")
                .amount(1000L)
                .taxAmount(1L)
                .products(Collections.singletonList(
                        KlarnaProduct.builder()
                                .name("Brown leather belt")
                                .quantity(1L)
                                .unitPrice(1000L)
                                .taxRate(0L)
                                .totalAmount(1000L)
                                .totalTaxAmount(0L)
                                .build()
                )).build();

        final CreditSessionResponse creditSessionResponse = blocking(defaultApi.klarnaClient().createCreditSession(creditSessionRequest));

        assertNotNull(creditSessionResponse);
        assertNotNull(creditSessionResponse.getSessionId());
        assertNotNull(creditSessionResponse.getClientToken());
        assertNotNull(creditSessionResponse.getPaymentMethodCategories());
        assertTrue(creditSessionResponse.getPaymentMethodCategories().size() > 0);
        for (final PaymentMethod paymentMethodCategory : creditSessionResponse.getPaymentMethodCategories()) {
            assertNotNull(paymentMethodCategory.getIdentifier());
            assertNotNull(paymentMethodCategory.getName());
            assertNotNull(paymentMethodCategory.getAssetUrls());
            assertNotNull(paymentMethodCategory.getAssetUrls().getDescriptive());
            assertNotNull(paymentMethodCategory.getAssetUrls().getStandard());
        }

        final CreditSession creditSession = blocking(defaultApi.klarnaClient().getCreditSession(creditSessionResponse.getSessionId()));

        assertNotNull(creditSession);
        assertNotNull(creditSession.getClientToken());
        assertNotNull(creditSession.getPurchaseCountry());
        assertNotNull(creditSession.getCurrency());
        assertNotNull(creditSession.getLocale());
        assertNotNull(creditSession.getAmount());
        assertNotNull(creditSession.getTaxAmount());
        assertNotNull(creditSession.getProducts());
        assertTrue(creditSession.getProducts().size() > 0);
        for (final KlarnaProduct klarnaProduct : creditSession.getProducts()) {
            assertNotNull(klarnaProduct.getName());
            assertNotNull(klarnaProduct.getQuantity());
            assertNotNull(klarnaProduct.getUnitPrice());
            assertNotNull(klarnaProduct.getTaxRate());
            assertNotNull(klarnaProduct.getTotalAmount());
            assertNotNull(klarnaProduct.getTotalTaxAmount());
        }

    }

}

