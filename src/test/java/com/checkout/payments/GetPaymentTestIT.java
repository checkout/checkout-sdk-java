package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.common.ThreeDSEnrollmentStatus;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetPaymentTestIT extends SandboxTestFixture {

    GetPaymentTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void resourceNotFoundHandledCorrectly() {
        assertNotFound(defaultApi.paymentsClient().getAsync("not-found"));
    }

    @Test
    void shouldHandleTimeout() {
        assertThrows(TimeoutException.class, () ->
                defaultApi.paymentsClient().getAsync("not-found").get(5, TimeUnit.MILLISECONDS));
    }

    @Test
    void shouldGetPayment() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setPaymentType(PaymentType.RECURRING);

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));

        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPayment().getId()));

        assertNotNull(paymentDetails);
        assertEquals(paymentResponse.getPayment().getId(), paymentDetails.getId());
        assertNotNull(paymentDetails.getCustomer());
        assertEquals(paymentResponse.getPayment().getCustomer().getId(), paymentDetails.getCustomer().getId());
        assertEquals(paymentRequest.getCustomer().getEmail(), paymentDetails.getCustomer().getEmail());
        assertEquals(paymentResponse.getPayment().getAmount(), paymentDetails.getAmount().intValue());
        assertEquals(paymentResponse.getPayment().getCurrency(), paymentDetails.getCurrency());
        assertEquals(paymentRequest.getPaymentType(), paymentDetails.getPaymentType());
        assertNotNull(paymentDetails.getBillingDescriptor());
        assertFalse(StringUtils.isBlank(paymentDetails.getReference()));
        assertNotNull(paymentDetails.getRisk());
        assertTrue(paymentDetails.getRequestedOn().isAfter(paymentResponse.getPayment().getProcessedOn().minus(1, ChronoUnit.MINUTES)));
        assertNull(paymentDetails.getThreeDS());
        assertNotNull(paymentDetails.getLinks());
        assertFalse(paymentDetails.getLinks().isEmpty());
        assertEquals(PaymentStatus.AUTHORIZED, paymentDetails.getStatus());
        assertNotNull(paymentDetails.getSource());
        assertTrue(paymentDetails.isApproved());
        assertNotNull(paymentDetails.getActions());
    }

    @Test
    void shouldGet3dsPayment() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.builder().enabled(true).build());

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        assertTrue(paymentResponse.isPending());

        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPending().getId()));

        assertNotNull(paymentDetails);
        assertEquals(paymentResponse.getPending().getId(), paymentDetails.getId());
        assertNotNull(paymentDetails.getCustomer());
        assertEquals(paymentResponse.getPending().getCustomer().getId(), paymentDetails.getCustomer().getId());
        assertEquals(paymentRequest.getCustomer().getEmail(), paymentDetails.getCustomer().getEmail());
        assertEquals(paymentRequest.getAmount(), paymentDetails.getAmount());
        assertEquals(paymentRequest.getCurrency(), paymentDetails.getCurrency());
        assertFalse(StringUtils.isBlank(paymentDetails.getReference()));
        assertEquals(PaymentType.REGULAR, paymentDetails.getPaymentType());
        assertNotNull(paymentDetails.getRisk());
        assertTrue(paymentDetails.getRequestedOn().isAfter(Instant.MIN));
        assertNotNull(paymentDetails.getThreeDS());
        assertFalse(paymentDetails.getThreeDS().isDowngraded());
        assertEquals(ThreeDSEnrollmentStatus.YES, paymentDetails.getThreeDS().getEnrolled());
        assertTrue(paymentDetails.requiresRedirect());
        assertNotNull(paymentDetails.getRedirectLink());
        assertNotNull(paymentDetails.getLinks());
        assertFalse(paymentDetails.getLinks().isEmpty());
        assertEquals(PaymentStatus.PENDING, paymentDetails.getStatus());
        assertNotNull(paymentDetails.getSource());
        assertFalse(paymentDetails.isApproved());
        assertNotNull(paymentDetails.getActions());
    }

    @Test
    void shouldGetPaymentMetadata() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setMetadata(new HashMap<>());
        paymentRequest.getMetadata().put("test", "1234");

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPayment().getId()));

        assertNotNull(paymentDetails.getMetadata());
        assertFalse(paymentDetails.getMetadata().isEmpty());
        assertEquals(1, paymentDetails.getMetadata().size());
        assertEquals("1234", paymentDetails.getMetadata().get("test"));
        assertNotNull(paymentDetails.getActions());
    }

    @Test
    void shouldGetPaymentIp() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setPaymentIp("10.1.2.3");

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPayment().getId()));

        assertEquals(paymentRequest.getPaymentIp(), paymentDetails.getPaymentIp());
        assertNotNull(paymentDetails.getActions());
        assertNotNull(paymentDetails.getMetadata());
    }

    @Test
    void shouldGetRecipient() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setRecipient(new PaymentRecipient(LocalDate.of(1985, 5, 15), "4242424242", "W1T", null, "Wensle", null));

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPayment().getId()));

        assertNotNull(paymentDetails.getRecipient());
        assertEquals(paymentRequest.getRecipient().getAccountNumber(), paymentDetails.getRecipient().getAccountNumber());
        assertEquals(paymentRequest.getRecipient().getDateOfBirth(), paymentDetails.getRecipient().getDateOfBirth());
        assertEquals(paymentRequest.getRecipient().getLastName(), paymentDetails.getRecipient().getLastName());
        assertEquals(paymentRequest.getRecipient().getZip(), paymentDetails.getRecipient().getZip());
        assertNotNull(paymentDetails.getActions());
        assertNotNull(paymentDetails.getMetadata());
    }

    @Test
    void shouldGetPaymentShipping() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        final Address address = new Address();
        address.setAddressLine1("221B Baker Street");
        address.setCity("London");
        address.setCountry(CountryCode.GB);
        address.setState("n/a");
        address.setZip("NW1 6XE");
        final Phone phone = new Phone();
        phone.setCountryCode("44");
        phone.setNumber("124312431243");
        final ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setAddress(address);
        shippingDetails.setPhone(phone);
        paymentRequest.setShipping(shippingDetails);

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPayment().getId()));

        assertNotNull(paymentDetails.getShipping());
        assertNotNull(paymentDetails.getShipping().getAddress());
        assertEquals(paymentRequest.getShipping().getAddress().getAddressLine1(), paymentDetails.getShipping().getAddress().getAddressLine1());
        assertEquals(paymentRequest.getShipping().getAddress().getAddressLine2(), paymentDetails.getShipping().getAddress().getAddressLine2());
        assertEquals(paymentRequest.getShipping().getAddress().getCity(), paymentDetails.getShipping().getAddress().getCity());
        assertEquals(paymentRequest.getShipping().getAddress().getCountry(), paymentDetails.getShipping().getAddress().getCountry());
        assertEquals(paymentRequest.getShipping().getAddress().getState(), paymentDetails.getShipping().getAddress().getState());
        assertEquals(paymentRequest.getShipping().getAddress().getZip(), paymentDetails.getShipping().getAddress().getZip());
        assertNotNull(paymentDetails.getShipping().getPhone());
        assertEquals(paymentRequest.getShipping().getPhone().getCountryCode(), paymentDetails.getShipping().getPhone().getCountryCode());
        assertEquals(paymentRequest.getShipping().getPhone().getNumber(), paymentDetails.getShipping().getPhone().getNumber());
        assertNotNull(paymentDetails.getActions());
        assertNotNull(paymentDetails.getMetadata());
    }

    @Test
    void shouldGetPaymentDescription() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setDescription("Too descriptive");

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));

        final GetPaymentResponse paymentDetails = blocking(defaultApi.paymentsClient().getAsync(paymentResponse.getPayment().getId()));

        assertEquals(paymentRequest.getDescription(), paymentDetails.getDescription());
        assertNotNull(paymentDetails.getActions());
        assertNotNull(paymentDetails.getMetadata());
    }

    @Test
    void shouldGetPaymentActions() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));

        final List<PaymentAction> actionsResponse = blocking(defaultApi.paymentsClient().getActionsAsync(paymentResponse.getPayment().getId()));

        assertNotNull(actionsResponse);
        assertEquals(1, actionsResponse.size());

        final PaymentProcessed payment = paymentResponse.getPayment();
        final PaymentAction paymentAction = actionsResponse.get(0);
        assertNotNull(paymentAction);
        assertEquals(payment.getActionId(), paymentAction.getId());
        assertNotNull(paymentAction.getProcessing());
        assertNotNull(paymentAction.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentAction.getProcessing().getRetrievalReferenceNumber());
        assertFalse(paymentAction.getProcessedOn().isBefore(payment.getProcessedOn()));
        assertTrue(paymentAction.isApproved());
        assertEquals(payment.isApproved(), paymentAction.isApproved());
        assertEquals(payment.getResponseCode(), paymentAction.getResponseCode());
        assertEquals(payment.getResponseSummary(), paymentAction.getResponseSummary());
        assertEquals(payment.getReference(), paymentAction.getReference());
        assertEquals(payment.getAuthCode(), paymentAction.getAuthCode());
        assertEquals(ActionType.AUTHORIZATION, paymentAction.getType());
        assertNotNull(paymentAction.getLinks());
    }

    @Test
    void shouldGetMultiplePaymentActions() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        final CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference(UUID.randomUUID().toString());

        final CaptureResponse captureResponse = blocking(defaultApi.paymentsClient().captureAsync(paymentResponse.getPayment().getId(), captureRequest));

        final List<PaymentAction> actionsResponse = blocking(defaultApi.paymentsClient().getActionsAsync(paymentResponse.getPayment().getId()));

        assertNotNull(actionsResponse);

        final PaymentAction authorizationPaymentAction = actionsResponse.stream().filter(a -> ActionType.AUTHORIZATION.equals(a.getType())).findFirst().get();
        assertNotNull(authorizationPaymentAction);
        assertEquals(paymentResponse.getPayment().getActionId(), authorizationPaymentAction.getId());

        final PaymentAction capturePaymentAction = actionsResponse.stream().filter(a -> ActionType.CAPTURE.equals(a.getType())).findFirst().get();
        assertNotNull(capturePaymentAction);
        assertEquals(captureResponse.getActionId(), capturePaymentAction.getId());
        assertEquals(captureResponse.getReference(), capturePaymentAction.getReference());
        assertNotNull(capturePaymentAction.getLinks());
    }

    @Test
    void shouldGetActionMetadata() {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setMetadata(new HashMap<>());
        paymentRequest.getMetadata().put("test", "1234");

        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));

        final List<PaymentAction> actionsResponse = blocking(defaultApi.paymentsClient().getActionsAsync(paymentResponse.getPayment().getId()));

        assertNotNull(actionsResponse);

        final PaymentAction paymentAction = actionsResponse.get(0);
        assertNotNull(paymentAction);
        assertNotNull(paymentAction.getMetadata());
        assertFalse(paymentAction.getMetadata().isEmpty());
        assertEquals(1, paymentAction.getMetadata().size());
        assertEquals("1234", paymentAction.getMetadata().get("test"));
    }
}
