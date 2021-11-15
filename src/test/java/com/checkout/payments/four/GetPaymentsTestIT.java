package com.checkout.payments.four;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.payments.ActionType;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.response.GetPaymentResponse;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void shouldGetCardPayment() {

        final PaymentResponse payment = makeCardPayment(false);

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());

    }

    @Test
    void shouldGetCardPaymentWithMetadata() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, false);
        request.getMetadata().put("test", "1234");

        final PaymentResponse payment = blocking(paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());
        assertEquals("1234", paymentReturned.getMetadata().get("test"));

    }

    @Test
    void shouldGetCardPaymentWithIpAndDescription() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = Payments.card(source).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .paymentIp("12.12.67.89")
                .description("description")
                .build();

        final PaymentResponse payment = blocking(paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.CARD_VERIFIED, paymentReturned.getStatus());
        assertEquals("12.12.67.89", paymentReturned.getPaymentIp());
        assertEquals("description", paymentReturned.getDescription());

    }

    @Test
    void shouldGetCardPaymentWithRecipient() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRecipient recipient = PaymentRecipient.builder()
                .accountNumber("1234567")
                .country(CountryCode.ES)
                .dateOfBirth("1985-05-15")
                .firstName("IT")
                .lastName("TESTING")
                .zip("12345")
                .build();

        final PaymentRequest request = Payments.card(source).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .recipient(recipient)
                .build();

        final PaymentResponse payment = blocking(paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getRecipient());
        assertEquals("1234567", paymentReturned.getRecipient().getAccountNumber());
        assertEquals("12345", paymentReturned.getRecipient().getZip());
        assertEquals("IT", paymentReturned.getRecipient().getFirstName());
        assertEquals("TESTING", paymentReturned.getRecipient().getLastName());
        assertEquals("1985-05-15", paymentReturned.getRecipient().getDateOfBirth());

    }

    @Test
    void shouldGetCardPaymentWithShipping() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final Address address = Address.builder()
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .city("City")
                .country(CountryCode.GB)
                .build();

        final Phone phone = Phone.builder().number("675676541").countryCode("+34").build();

        final ShippingDetails recipient = ShippingDetails.builder()
                .address(address)
                .phone(phone)
                .build();

        final PaymentRequest request = Payments.card(source).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .shipping(recipient)
                .build();

        final PaymentResponse payment = blocking(paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getShipping());

        final ShippingDetails shippingDetails = paymentReturned.getShipping();
        assertEquals("City", shippingDetails.getAddress().getCity());
        assertEquals(CountryCode.GB, shippingDetails.getAddress().getCountry());
        assertEquals(phone, shippingDetails.getPhone());

    }

    @Test
    void shouldGetCardPayment_3ds() {

        final PaymentResponse payment = makeCardPayment(true);

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.PENDING, paymentReturned.getStatus());

    }

    @Test
    void shouldGetCardTokenPayment() {

        final PaymentResponse payment = makeTokenPayment();

        final GetPaymentResponse paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());

    }

    @Test
    void shouldGetCardPaymentAction() {

        final PaymentResponse payment = makeCardPayment(false);

        final List<PaymentAction> paymentActions = blocking(paymentsClient.getPaymentActions(payment.getId()));

        assertNotNull(paymentActions);
        assertEquals(1, paymentActions.size());

        final PaymentAction paymentAction = paymentActions.get(0);
        assertNotNull(paymentAction.getId());
        assertEquals(ActionType.AUTHORIZATION, paymentAction.getType());
        assertNotNull(paymentAction.getProcessedOn());
        assertEquals(Long.valueOf(10), paymentAction.getAmount());
        assertTrue(paymentAction.getApproved());
        assertNotNull(paymentAction.getAuthCode());
        assertNotNull(paymentAction.getResponseCode());
        assertEquals("Approved", paymentAction.getResponseSummary());
        assertEquals(AuthorizationType.FINAL, paymentAction.getAuthorizationType());
        assertNotNull(paymentAction.getReference());
        assertNotNull(paymentAction.getProcessing());
        assertNotNull(paymentAction.getProcessing().getRetrievalReferenceNumber());
        assertNotNull(paymentAction.getProcessing().getAcquirerTransactionId());

    }

    @Test
    void shouldGetCardMultiplePaymentActions() {

        // payment

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, false);
        request.getMetadata().put("test", "1234");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final PaymentResponse payment = blocking(paymentsClient.requestPayment(request));

        // capture

        final CaptureResponse captureResponse = capturePayment(payment.getId(), captureRequest);

        nap();

        // capture

        final List<PaymentAction> paymentActions = blocking(paymentsClient.getPaymentActions(payment.getId()));

        assertNotNull(paymentActions);
        assertEquals(2, paymentActions.size());

        final PaymentAction authorizationPaymentAction = paymentActions.stream().filter(a -> ActionType.AUTHORIZATION.equals(a.getType())).findFirst().get();
        assertNotNull(authorizationPaymentAction);
        assertEquals(payment.getActionId(), authorizationPaymentAction.getId());
        assertEquals("1234", authorizationPaymentAction.getMetadata().get("test"));

        final PaymentAction capturePaymentAction = paymentActions.stream().filter(a -> ActionType.CAPTURE.equals(a.getType())).findFirst().get();
        assertNotNull(capturePaymentAction);
        assertEquals(captureResponse.getActionId(), capturePaymentAction.getId());
        assertEquals(captureResponse.getReference(), capturePaymentAction.getReference());
        assertNotNull(capturePaymentAction.getLinks());

    }

}
