package com.checkout.payments;

import com.checkout.CardSourceHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.CardResponseSource;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldHandleCorrectlyResourceNotFound() {
        assertNotFound(paymentsClient.getPayment("fake"));
    }

    @Test
    void shouldHandleTimeout() {
        assertThrows(TimeoutException.class, () -> paymentsClient.getPayment("fake").get(5, TimeUnit.MILLISECONDS));
    }

    @Test
    void shouldGetPayment() {

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final RequestCardSource source = RequestCardSource.builder()
                .name(CardSourceHelper.Visa.NAME)
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final ShippingDetails shippingDetails = ShippingDetails.builder()
                .address(billingAddress)
                .phone(phone)
                .build();

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .capture(true)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.GBP)
                .paymentIp("1.2.3.4")
                .description("description")
                .shipping(shippingDetails)
                .build();

        paymentRequest.getMetadata().put("test", "1234");

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));

        assertNotNull(paymentReturned.getId());
        assertEquals(PaymentType.REGULAR, paymentReturned.getPaymentType());
        assertNotNull(paymentReturned.getRequestedOn());
        assertNotNull(paymentReturned.getReference());
        assertNotNull(paymentReturned.getSchemeId());
        assertNotNull(paymentReturned.getEci());
        //assertEquals(PaymentStatus.CAPTURED, paymentReturned.getStatus());
        assertEquals(10, paymentReturned.getAmount());
        assertTrue(paymentReturned.getApproved());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertEquals(Currency.GBP, paymentReturned.getCurrency());
        // source
        assertTrue(paymentReturned.getSource() instanceof CardResponseSource);
        final CardResponseSource responseCardSource = (CardResponseSource) paymentReturned.getSource();
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertNotNull(responseCardSource.getId());
        assertEquals("S", responseCardSource.getAvsCheck());
        assertEquals("Y", responseCardSource.getCvvCheck());
        assertNotNull(responseCardSource.getBin());
        //assertEquals(CardCategory.CONSUMER, responseCardSource.getCardCategory());
        //assertEquals(CardType.CREDIT, responseCardSource.getCardType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, responseCardSource.getExpiryYear());
        assertNotNull(responseCardSource.getLast4());
        //assertNotNull(responseCardSource.getScheme());
        assertNotNull(responseCardSource.getName());
        assertNotNull(responseCardSource.getFastFunds());
        assertNotNull(responseCardSource.getFingerprint());
        //assertNotNull(responseCardSource.getIssuer());
        //assertEquals(CountryCode.US, responseCardSource.getIssuerCountry());
        assertTrue(responseCardSource.getPayouts());
        //assertNotNull(responseCardSource.getProductId());
        //assertNotNull(responseCardSource.getProductType());
        // customer
        assertNotNull(paymentReturned.getCustomer());
        assertNotNull(paymentReturned.getCustomer().getId());
        assertNotNull(paymentReturned.getCustomer().getName());
        //risk
        assertFalse(paymentReturned.getRisk().getFlagged());
        // links
        assertEquals(4, paymentResponse.getLinks().size());
        assertNotNull(paymentResponse.getLink(SELF));
        assertNotNull(paymentResponse.getLink("actions"));
        assertNotNull(paymentResponse.getLink("capture"));
        assertNotNull(paymentResponse.getLink("void"));
        // metadata
        assertEquals(1, paymentReturned.getMetadata().size());
        assertEquals("1234", paymentReturned.getMetadata().get("test"));
        // ip
        assertEquals("1.2.3.4", paymentReturned.getPaymentIp());
        // description
        assertEquals("description", paymentReturned.getDescription());
        // shipping
        assertEquals(shippingDetails, paymentReturned.getShipping());
    }

}
