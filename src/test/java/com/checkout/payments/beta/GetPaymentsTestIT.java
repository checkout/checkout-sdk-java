package com.checkout.payments.beta;

import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.common.beta.Address;
import com.checkout.common.beta.Currency;
import com.checkout.common.beta.Phone;
import com.checkout.payments.beta.action.PaymentAction;
import com.checkout.payments.beta.action.PaymentActionType;
import com.checkout.payments.beta.capture.CaptureRequest;
import com.checkout.payments.beta.capture.CaptureResponse;
import com.checkout.payments.beta.request.AuthorizationType;
import com.checkout.payments.beta.request.PaymentRecipient;
import com.checkout.payments.beta.request.PaymentRequest;
import com.checkout.payments.beta.request.Payments;
import com.checkout.payments.beta.request.ShippingDetails;
import com.checkout.payments.beta.request.source.RequestCardSource;
import com.checkout.payments.beta.response.PaymentResponse;
import com.checkout.payments.beta.response.PaymentStatus;
import com.checkout.payments.beta.response.source.ResponseCardSource;
import com.checkout.payments.beta.sender.RequestIndividualSender;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.checkout.payments.beta.CardSourceHelper.getCardSourcePayment;
import static com.checkout.payments.beta.CardSourceHelper.getIndividualSender;
import static com.checkout.payments.beta.CardSourceHelper.getRequestCardSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    public void shouldHandleCorrectlyResourceNotFound() throws Exception {
        try {
            paymentsClient.getPayment("fake").get();
            fail();
        } catch (final ExecutionException e) {
            assertTrue(e.getCause() instanceof CheckoutResourceNotFoundException);
        }
    }

    @Test(expected = TimeoutException.class)
    public void shouldHandleTimeout() throws Exception {
        paymentsClient.getPayment("fake").get(5, TimeUnit.MILLISECONDS);
    }

    @Test
    public void shouldGetCardPayment() {

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());

    }

    @Test
    public void shouldGetCardPaymentWithMetadata() {

        final RequestCardSource source = getRequestCardSource();
        final RequestIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, false);
        request.getMetadata().put("test", "1234");

        final PaymentResponse<ResponseCardSource> payment = blocking(paymentsClient.requestPayment(request));

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());
        assertEquals("1234", paymentReturned.getMetadata().get("test"));

    }

    @Test
    public void shouldGetCardPaymentWithIpAndDescription() {

        final RequestCardSource source = getRequestCardSource();
        final RequestIndividualSender sender = getIndividualSender();

        final PaymentRequest request = Payments.card(source).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .paymentIp("12.12.67.89")
                .description("description")
                .build();

        final PaymentResponse<ResponseCardSource> payment = blocking(paymentsClient.requestPayment(request));

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.CARD_VERIFIED, paymentReturned.getStatus());
        assertEquals("12.12.67.89", paymentReturned.getPaymentIp());
        assertEquals("description", paymentReturned.getDescription());

    }

    @Test
    public void shouldGetCardPaymentWithRecipient() {

        final RequestCardSource source = getRequestCardSource();
        final RequestIndividualSender sender = getIndividualSender();

        final PaymentRecipient recipient = PaymentRecipient.builder()
                .accountNumber("1234567")
                .country("ES")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
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

        final PaymentResponse<ResponseCardSource> payment = blocking(paymentsClient.requestPayment(request));

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getRecipient());
        assertEquals("1234567", paymentReturned.getRecipient().getAccountNumber());
        assertEquals("12345", paymentReturned.getRecipient().getZip());
        assertEquals("IT", paymentReturned.getRecipient().getFirstName());
        assertEquals("TESTING", paymentReturned.getRecipient().getLastName());
        assertEquals(LocalDate.of(1985, 5, 15), paymentReturned.getRecipient().getDateOfBirth());

    }

    @Test
    public void shouldGetCardPaymentWithShipping() {

        final RequestCardSource source = getRequestCardSource();
        final RequestIndividualSender sender = getIndividualSender();

        final Address address = Address.builder()
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .city("City")
                .country("UK")
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

        final PaymentResponse<ResponseCardSource> payment = blocking(paymentsClient.requestPayment(request));

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getShipping());

        final ShippingDetails shippingDetails = paymentReturned.getShipping();
        assertEquals("City", shippingDetails.getAddress().getCity());
        assertEquals("UK", shippingDetails.getAddress().getCountry());
        assertEquals(phone, shippingDetails.getPhone());

    }

    @Test
    public void shouldGetCardPayment_3ds() {

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(true);

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.PENDING, paymentReturned.getStatus());

    }

    @Test
    public void shouldGetCardTokenPayment() {

        final PaymentResponse<ResponseCardSource> payment = makeTokenPayment();

        final PaymentResponse<ResponseCardSource> paymentReturned = blocking(paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());

    }

    @Test
    public void shouldGetCardPaymentAction() {

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        final List<PaymentAction> paymentActions = blocking(paymentsClient.getPaymentActions(payment.getId()));

        assertNotNull(paymentActions);
        assertEquals(1, paymentActions.size());

        final PaymentAction paymentAction = paymentActions.get(0);
        assertNotNull(paymentAction.getId());
        assertEquals(PaymentActionType.AUTHORIZATION, paymentAction.getType());
        assertNotNull(paymentAction.getProcessedOn());
        assertEquals(Long.valueOf(10), paymentAction.getAmount());
        assertTrue(paymentAction.isApproved());
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
    public void shouldGetCardMultiplePaymentActions() {

        // payment

        final RequestCardSource source = getRequestCardSource();
        final RequestIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, false);
        request.getMetadata().put("test", "1234");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final PaymentResponse<ResponseCardSource> payment = blocking(paymentsClient.requestPayment(request));

        // capture

        final CaptureResponse captureResponse = capturePayment(payment.getId(), captureRequest);

        waitForIt();

        // capture

        final List<PaymentAction> paymentActions = blocking(paymentsClient.getPaymentActions(payment.getId()));

        assertNotNull(paymentActions);
        assertEquals(2, paymentActions.size());

        final PaymentAction authorizationPaymentAction = paymentActions.stream().filter(a -> PaymentActionType.AUTHORIZATION.equals(a.getType())).findFirst().get();
        assertNotNull(authorizationPaymentAction);
        assertEquals(payment.getActionId(), authorizationPaymentAction.getId());
        assertEquals("1234", authorizationPaymentAction.getMetadata().get("test"));

        final PaymentAction capturePaymentAction = paymentActions.stream().filter(a -> PaymentActionType.CAPTURE.equals(a.getType())).findFirst().get();
        assertNotNull(capturePaymentAction);
        assertEquals(captureResponse.getActionId(), capturePaymentAction.getId());
        assertEquals(captureResponse.getReference(), capturePaymentAction.getReference());
        assertNotNull(capturePaymentAction.getLinks());

    }

}
