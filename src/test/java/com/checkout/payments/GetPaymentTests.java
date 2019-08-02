package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class GetPaymentTests extends ApiTestFixture {

    @Test
    public void can_get_non_3ds_payment() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setPaymentType(PaymentType.RECURRING);

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(paymentDetails);
        Assert.assertEquals(paymentResponse.getPayment().getId(), paymentDetails.getId());
        Assert.assertNotNull(paymentDetails.getCustomer());
        Assert.assertEquals(paymentResponse.getPayment().getCustomer().getId(), paymentDetails.getCustomer().getId());
        Assert.assertEquals(paymentRequest.getCustomer().getEmail(), paymentDetails.getCustomer().getEmail());
        Assert.assertEquals(paymentResponse.getPayment().getAmount(), paymentDetails.getAmount().intValue());
        Assert.assertEquals(paymentResponse.getPayment().getCurrency(), paymentDetails.getCurrency());
        Assert.assertEquals(paymentRequest.getPaymentType(), paymentDetails.getPaymentType());
        Assert.assertNotNull(paymentDetails.getBillingDescriptor());
        Assert.assertFalse(CheckoutUtils.isNullOrWhitespace(paymentDetails.getReference()));
        Assert.assertNotNull(paymentDetails.getRisk());
        Assert.assertTrue(paymentDetails.getRequestedOn().isAfter(paymentResponse.getPayment().getProcessedOn().minus(1, ChronoUnit.MINUTES)));
        Assert.assertNull(paymentDetails.getThreeDS());
        Assert.assertNotNull(paymentDetails.getLinks());
        Assert.assertFalse(paymentDetails.getLinks().isEmpty());
        Assert.assertEquals(PaymentStatus.AUTHORIZED, paymentDetails.getStatus());
        Assert.assertNotNull(paymentDetails.getSource());
        Assert.assertTrue(paymentDetails.isApproved());
    }

    @Test
    public void can_get_3ds_payment_before_auth() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.from(true));

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(paymentResponse.isPending());

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPending().getId()).get();

        Assert.assertNotNull(paymentDetails);
        Assert.assertEquals(paymentResponse.getPending().getId(), paymentDetails.getId());
        Assert.assertNotNull(paymentDetails.getCustomer());
        Assert.assertEquals(paymentResponse.getPending().getCustomer().getId(), paymentDetails.getCustomer().getId());
        Assert.assertEquals(paymentRequest.getCustomer().getEmail(), paymentDetails.getCustomer().getEmail());
        Assert.assertEquals(paymentRequest.getAmount(), paymentDetails.getAmount());
        Assert.assertEquals(paymentRequest.getCurrency(), paymentDetails.getCurrency());
        Assert.assertFalse(CheckoutUtils.isNullOrWhitespace(paymentDetails.getReference()));
        Assert.assertEquals(PaymentType.REGULAR, paymentDetails.getPaymentType());
        Assert.assertNotNull(paymentDetails.getRisk());
        Assert.assertTrue(paymentDetails.getRequestedOn().isAfter(Instant.MIN));
        Assert.assertNotNull(paymentDetails.getThreeDS());
        Assert.assertFalse(paymentDetails.getThreeDS().isDowngraded());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentDetails.getThreeDS().getEnrolled()));
        Assert.assertTrue(paymentDetails.requiresRedirect());
        Assert.assertNotNull(paymentDetails.getRedirectLink());
        Assert.assertNotNull(paymentDetails.getLinks());
        Assert.assertFalse(paymentDetails.getLinks().isEmpty());
        Assert.assertEquals(PaymentStatus.PENDING, paymentDetails.getStatus());
        Assert.assertNotNull(paymentDetails.getSource());
        Assert.assertFalse(paymentDetails.isApproved());
    }

    @Test
    public void can_get_payment_metadata() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.getMetadata().put("test", "1234");

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(paymentDetails.getMetadata());
        Assert.assertFalse(paymentDetails.getMetadata().isEmpty());
        Assert.assertEquals(1, paymentDetails.getMetadata().size());
        Assert.assertEquals("1234", paymentDetails.getMetadata().get("test"));
    }

    @Test
    public void can_get_payment_ip() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setPaymentIp("10.1.2.3");

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertEquals(paymentRequest.getPaymentIp(), paymentDetails.getPaymentIp());
    }

    @Test
    public void can_get_payment_recipient() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setRecipient(new PaymentRecipient(LocalDate.of(1985, 5, 15), "4242424242", "W1T", null, "Wensle"));

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(paymentDetails.getRecipient());
        Assert.assertEquals(paymentRequest.getRecipient().getAccountNumber(), paymentDetails.getRecipient().getAccountNumber());
        Assert.assertEquals(paymentRequest.getRecipient().getDateOfBirth(), paymentDetails.getRecipient().getDateOfBirth());
        Assert.assertEquals(paymentRequest.getRecipient().getLastName(), paymentDetails.getRecipient().getLastName());
        Assert.assertEquals(paymentRequest.getRecipient().getZip(), paymentDetails.getRecipient().getZip());
    }

    @Test
    public void can_get_payment_shipping() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        Address address = new Address();
        address.setAddressLine1("221B Baker Street");
        address.setCity("London");
        address.setCountry("UK");
        address.setState("n/a");
        address.setZip("NW1 6XE");
        Phone phone = new Phone();
        phone.setCountryCode("44");
        phone.setNumber("124312431243");
        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setAddress(address);
        shippingDetails.setPhone(phone);
        paymentRequest.setShipping(shippingDetails);

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(paymentDetails.getShipping());
        Assert.assertNotNull(paymentDetails.getShipping().getAddress());
        Assert.assertEquals(paymentRequest.getShipping().getAddress().getAddressLine1(), paymentDetails.getShipping().getAddress().getAddressLine1());
        Assert.assertEquals(paymentRequest.getShipping().getAddress().getAddressLine2(), paymentDetails.getShipping().getAddress().getAddressLine2());
        Assert.assertEquals(paymentRequest.getShipping().getAddress().getCity(), paymentDetails.getShipping().getAddress().getCity());
        Assert.assertEquals(paymentRequest.getShipping().getAddress().getCountry(), paymentDetails.getShipping().getAddress().getCountry());
        Assert.assertEquals(paymentRequest.getShipping().getAddress().getState(), paymentDetails.getShipping().getAddress().getState());
        Assert.assertEquals(paymentRequest.getShipping().getAddress().getZip(), paymentDetails.getShipping().getAddress().getZip());
        Assert.assertNotNull(paymentDetails.getShipping().getPhone());
        Assert.assertEquals(paymentRequest.getShipping().getPhone().getCountryCode(), paymentDetails.getShipping().getPhone().getCountryCode());
        Assert.assertEquals(paymentRequest.getShipping().getPhone().getNumber(), paymentDetails.getShipping().getPhone().getNumber());
    }

    @Test
    public void can_get_payment_description() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setDescription("Too descriptive");

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        GetPaymentResponse paymentDetails = getApi().paymentsClient().getAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertEquals(paymentRequest.getDescription(), paymentDetails.getDescription());
    }

    @Test
    public void can_get_payment_action() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        List<PaymentAction> actionsResponse = getApi().paymentsClient().getActionsAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(actionsResponse);
        Assert.assertEquals(1, actionsResponse.size());

        PaymentProcessed payment = paymentResponse.getPayment();
        PaymentAction paymentAction = actionsResponse.get(0);
        Assert.assertNotNull(paymentAction);
        Assert.assertEquals(payment.getActionId(), paymentAction.getId());
        Assert.assertFalse(paymentAction.getProcessedOn().isBefore(payment.getProcessedOn()));
        Assert.assertTrue(paymentAction.isApproved());
        Assert.assertEquals(payment.isApproved(), paymentAction.isApproved());
        Assert.assertEquals(payment.getResponseCode(), paymentAction.getResponseCode());
        Assert.assertEquals(payment.getResponseSummary(), paymentAction.getResponseSummary());
        Assert.assertEquals(payment.getReference(), paymentAction.getReference());
        Assert.assertEquals(payment.getAuthCode(), paymentAction.getAuthCode());
        Assert.assertEquals(ActionType.AUTHORIZATION, paymentAction.getType());
        Assert.assertNotNull(paymentAction.getLinks());
    }

    @Test
    public void can_get_multiple_payment_actions() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference(UUID.randomUUID().toString());

        CaptureResponse captureResponse = getApi().paymentsClient().captureAsync(paymentResponse.getPayment().getId(), captureRequest).get();

        List<PaymentAction> actionsResponse = getApi().paymentsClient().getActionsAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(actionsResponse);

        PaymentAction authorizationPaymentAction = actionsResponse.stream().filter(a -> ActionType.AUTHORIZATION.equals(a.getType())).findFirst().get();
        Assert.assertNotNull(authorizationPaymentAction);
        Assert.assertEquals(paymentResponse.getPayment().getActionId(), authorizationPaymentAction.getId());

        PaymentAction capturePaymentAction = actionsResponse.stream().filter(a -> ActionType.CAPTURE.equals(a.getType())).findFirst().get();
        Assert.assertNotNull(capturePaymentAction);
        Assert.assertEquals(captureResponse.getActionId(), capturePaymentAction.getId());
        Assert.assertEquals(captureResponse.getReference(), capturePaymentAction.getReference());
        Assert.assertNotNull(capturePaymentAction.getLinks());
    }

    @Test
    public void can_get_payment_action_metadata() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.getMetadata().put("test", "1234");

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        List<PaymentAction> actionsResponse = getApi().paymentsClient().getActionsAsync(paymentResponse.getPayment().getId()).get();

        Assert.assertNotNull(actionsResponse);

        PaymentAction paymentAction = actionsResponse.get(0);
        Assert.assertNotNull(paymentAction);
        Assert.assertNotNull(paymentAction.getMetadata());
        Assert.assertFalse(paymentAction.getMetadata().isEmpty());
        Assert.assertEquals(1, paymentAction.getMetadata().size());
        Assert.assertEquals("1234", paymentAction.getMetadata().get("test"));
    }
}