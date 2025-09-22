package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.UnreferencedRefundRequest;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.carddestination.CardDestination;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.billingaddress.BillingAddress;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.individualaccountholder.IndividualAccountHolder;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.source.Source;
import com.checkout.payments.AbstractPaymentsTestIT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class RequestUnreferencedRefundPaymentsTestIT extends AbstractPaymentsTestIT {

    @Disabled("use on demand")
    @Test
    void shouldRequestPaymentWithUnreferencedRefund_CardDestination() throws Exception {
        // Arrange
        UnreferencedRefundRequest request = createUnreferencedRefundRequest();

        // Act
        RequestAPaymentOrPayoutResponse response = paymentsClient.requestPayment(request).get();

        // Assert
        assertNotNull(response);

        if (response.getCreated() != null) {
            assertNotNull(response.getCreated().getId());
            assertNotNull(response.getCreated().getStatus());
            assertEquals(Integer.valueOf(1000), response.getCreated().getAmount());
            assertEquals(Currency.USD, response.getCreated().getCurrency());
            assertEquals("REF-FQSGRB", response.getCreated().getReference());
            assertNotNull(response.getCreated().getSource());
            assertNotNull(response.getCreated().getSource().getType());
        } else if (response.getAccepted() != null) {
            assertNotNull(response.getAccepted().getId());
            assertNotNull(response.getAccepted().getStatus());
            assertEquals("REF-FQSGRB", response.getAccepted().getReference());
        } else {
            fail("Neither Created nor Accepted response was returned");
        }
    }

    @Disabled("use on demand")
    @Test
    void shouldRequestPaymentWithUnreferencedRefund_WithIdempotencyKey() throws Exception {

        // Arrange
        UnreferencedRefundRequest request = createUnreferencedRefundRequest();
        String idempotencyKey = java.util.UUID.randomUUID().toString();

        // Act
        RequestAPaymentOrPayoutResponse response1 = paymentsClient.requestPayment(request, idempotencyKey).get();
        RequestAPaymentOrPayoutResponse response2 = paymentsClient.requestPayment(request, idempotencyKey).get();

        // Assert
        assertNotNull(response1);
        assertNotNull(response2);

        if (response1.getCreated() != null && response2.getCreated() != null) {
            assertEquals(response1.getCreated().getId(), response2.getCreated().getId());
        } else if (response1.getAccepted() != null && response2.getAccepted() != null) {
            assertEquals(response1.getAccepted().getId(), response2.getAccepted().getId());
        }
    }

    private UnreferencedRefundRequest createUnreferencedRefundRequest() {
        UnreferencedRefundRequest request = new UnreferencedRefundRequest();
        request.setAmount(1000);
        request.setCurrency("USD");
        request.setPaymentType("UnreferencedRefund");
        request.setReference("REF-FQSGRB");

        Source source = new com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.source.Source();
        source.setType("currency_account");
        source.setId("ca_7hdklm89ybnfqqmk8pxrcvfh5t");
        request.setSource(source);

        CardDestination destination = new CardDestination();
        destination.setNumber("4111111111111111");
        destination.setExpiryMonth(12);
        destination.setExpiryYear(2042);

        IndividualAccountHolder accountHolder = new IndividualAccountHolder();
        accountHolder.setFirstName("John");
        accountHolder.setLastName("Doe");
        accountHolder.setDateOfBirth("2000-01-01");
        accountHolder.setCountryOfBirth("US");

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddressLine1("123 Test St");
        billingAddress.setCity("New York");
        billingAddress.setState("NY");
        billingAddress.setZip("10014");
        billingAddress.setCountry(CountryCode.US);
        accountHolder.setBillingAddress(billingAddress);

        destination.setAccountHolder(accountHolder);
        request.setDestination(destination);

        request.setProcessingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"));

        return request;
    }
}
