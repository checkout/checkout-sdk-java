package com.checkout.payments;

import com.checkout.ItemsResponse;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentIndividualSender;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldGetCardPayment() {

        final PaymentResponse payment = makeCardPayment(false);

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());

    }

    @Test
    void shouldGetPaymentWithItemsUsingEnumType() {

        Product product = Product.builder()
                .type(ProductType.DIGITAL)
                .name("Test Product")
                .quantity(1L)
                .unitPrice(1000L)
                .build();

        PaymentRequest request = PaymentRequest.builder()
                .source(getRequestCardSource())
                .reference(UUID.randomUUID().toString())
                .amount(1000L)
                .currency(Currency.EUR)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .items(Collections.singletonList(product))
                .build();

        PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getItems());
        assertEquals(1, paymentReturned.getItems().size());

        Product returnedProduct = paymentReturned.getItems().get(0);
        assertEquals(ProductType.DIGITAL, returnedProduct.getTypeAsEnum());
        assertEquals(ProductType.DIGITAL, returnedProduct.getType());
        assertNull(returnedProduct.getTypeAsString());
        assertEquals("Test Product", returnedProduct.getName());
        assertEquals(1L, returnedProduct.getQuantity());
        assertEquals(1000L, returnedProduct.getUnitPrice());
    }

    @Test
    void shouldGetPaymentWithItemsUsingStringType() {

        Product product = Product.builder()
                .type("CustomType")
                .name("Custom Product")
                .quantity(2L)
                .unitPrice(2000L)
                .build();

        PaymentRequest request = PaymentRequest.builder()
                .source(getRequestCardSource())
                .reference(UUID.randomUUID().toString())
                .amount(2000L)
                .currency(Currency.EUR)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .items(Collections.singletonList(product))
                .build();

        PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getItems());
        assertEquals(1, paymentReturned.getItems().size());

        Product returnedProduct = paymentReturned.getItems().get(0);
        assertEquals("CustomType", returnedProduct.getTypeAsString());
        assertEquals("CustomType", returnedProduct.getType());
        assertNull(returnedProduct.getTypeAsEnum());
        assertEquals("Custom Product", returnedProduct.getName());
        assertEquals(2L, returnedProduct.getQuantity());
        assertEquals(2000L, returnedProduct.getUnitPrice());
    }

    @Test
    void shouldGetPaymentWithMultipleItems() {

        Product enumProduct = Product.builder()
                .type(ProductType.PHYSICAL)
                .name("Physical Product")
                .quantity(1L)
                .unitPrice(1500L)
                .build();

        Product stringProduct = Product.builder()
                .type("CustomType")
                .name("Custom Product")
                .quantity(2L)
                .unitPrice(3000L)
                .build();

        PaymentRequest request = PaymentRequest.builder()
                .source(getRequestCardSource())
                .reference(UUID.randomUUID().toString())
                .amount(4500L)
                .currency(Currency.EUR)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .items(Arrays.asList(enumProduct, stringProduct))
                .build();

        PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertNotNull(paymentReturned.getItems());
        assertEquals(2, paymentReturned.getItems().size());

        Product returnedEnumProduct = paymentReturned.getItems().get(0);
        assertEquals(ProductType.PHYSICAL, returnedEnumProduct.getTypeAsEnum());
        assertEquals(ProductType.PHYSICAL, returnedEnumProduct.getType());
        assertNull(returnedEnumProduct.getTypeAsString());
        assertEquals("Physical Product", returnedEnumProduct.getName());
        assertEquals(1L, returnedEnumProduct.getQuantity());
        assertEquals(1500L, returnedEnumProduct.getUnitPrice());

        Product returnedStringProduct = paymentReturned.getItems().get(1);
        assertEquals("CustomType", returnedStringProduct.getTypeAsString());
        assertEquals("CustomType", returnedStringProduct.getType());
        assertNull(returnedStringProduct.getTypeAsEnum());
        assertEquals("Custom Product", returnedStringProduct.getName());
        assertEquals(2L, returnedStringProduct.getQuantity());
        assertEquals(3000L, returnedStringProduct.getUnitPrice());
    }

    @Test
    void shouldGetCardPaymentWithMetadata() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, false);
        request.getMetadata().put("test", "1234");

        final PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());
        assertEquals("1234", paymentReturned.getMetadata().get("test"));

    }

    @Test
    void shouldGetCardPaymentWithIpAndDescription() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .paymentIp("12.12.67.89")
                .description("description")
                .build();

        final PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

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

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .recipient(recipient)
                .build();

        final PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

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

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .shipping(recipient)
                .build();

        final PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

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

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.PENDING, paymentReturned.getStatus());

    }

    @Test
    void shouldGetCardTokenPayment() {

        final PaymentResponse payment = makeTokenPayment();

        final GetPaymentResponse paymentReturned = blocking(() -> paymentsClient.getPayment(payment.getId()));

        assertNotNull(paymentReturned);
        assertEquals(PaymentStatus.AUTHORIZED, paymentReturned.getStatus());

    }

    @Test
    void shouldGetCardPaymentAction() {

        final PaymentResponse payment = makeCardPayment(false);

        final ItemsResponse<PaymentAction> paymentActions = blocking(() -> paymentsClient.getPaymentActions(payment.getId()));

        assertNotNull(paymentActions);
        assertEquals(1, paymentActions.getItems().size());
        assertNotNull(paymentActions.getResponseHeaders());
        assertNotNull(paymentActions.getBody());
        assertNotNull(paymentActions.getHttpStatusCode());

        final PaymentAction paymentAction = paymentActions.getItems().get(0);
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

        final PaymentResponse payment = blocking(() -> paymentsClient.requestPayment(request));

        // capture
        final CaptureResponse captureResponse = capturePayment(payment.getId(), captureRequest);

        // capture
        final ItemsResponse<PaymentAction> paymentActions = blocking(() -> paymentsClient.getPaymentActions(payment.getId()), new ListHasSize<ItemsResponse<PaymentAction>, PaymentAction>(2));

        assertNotNull(paymentActions);
        assertEquals(2, paymentActions.getItems().size());

        final PaymentAction authorizationPaymentAction = paymentActions.getItems().stream().filter(a -> ActionType.AUTHORIZATION.equals(a.getType())).findFirst().get();
        assertNotNull(authorizationPaymentAction);
        assertEquals(payment.getActionId(), authorizationPaymentAction.getId());
        assertEquals("1234", authorizationPaymentAction.getMetadata().get("test"));

        final PaymentAction capturePaymentAction = paymentActions.getItems().stream().filter(a -> ActionType.CAPTURE.equals(a.getType())).findFirst().get();
        assertNotNull(capturePaymentAction);
        assertEquals(captureResponse.getActionId(), capturePaymentAction.getId());
        assertEquals(captureResponse.getReference(), capturePaymentAction.getReference());
        assertNotNull(capturePaymentAction.getLinks());

    }
}
