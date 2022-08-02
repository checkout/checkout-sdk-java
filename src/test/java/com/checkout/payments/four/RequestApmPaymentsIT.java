package com.checkout.payments.four;

import com.checkout.CheckoutApiException;
import com.checkout.common.Currency;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.source.apm.RequestAlipayPlusSource;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RequestApmPaymentsIT extends AbstractPaymentsTestIT {

    @Test
    void shouldMakeAliPayPayment() throws InterruptedException {
        RequestAlipayPlusSource source = RequestAlipayPlusSource.requestAlipayPlusCNSource();
        source = RequestAlipayPlusSource.requestAlipayPlusGCashSource();
        source = RequestAlipayPlusSource.requestAlipayPlusHKSource();
        source = RequestAlipayPlusSource.requestAlipayPlusDanaSource();
        source = RequestAlipayPlusSource.requestAlipayPlusKakaoPaySource();
        source = RequestAlipayPlusSource.requestAlipayPlusTrueMoneySource();
        source = RequestAlipayPlusSource.requestAlipayPlusTNGSource();
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .reference(UUID.randomUUID().toString())
                .processingChannelId("pc_5jp2az55l3cuths25t5p3xhwru")
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        try {
            paymentsClient.requestPayment(paymentRequest).get();
            fail();
        } catch (Exception exception) {
            assertTrue(exception.getCause() instanceof CheckoutApiException);

        }
    }
}
