package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDevice;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDeviceClient;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerEmail;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashApp;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.checkout.payments.PaymentType;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class PaymentSetupsTestIT extends SandboxTestFixture {

    PaymentSetupsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void createPaymentSetup_ShouldReturnValidResponse() {
        // Arrange
        final PaymentSetupsRequest paymentSetupsRequest = createValidPaymentSetupsRequest();

        // Act
        final CompletableFuture<PaymentSetupsResponse> future =
                checkoutApi.paymentSetupsClient().createPaymentSetup(paymentSetupsRequest);
        final PaymentSetupsResponse response = future.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(paymentSetupsRequest.getProcessingChannelId(), response.getProcessingChannelId());
        assertEquals(paymentSetupsRequest.getAmount(), response.getAmount());
        assertEquals(paymentSetupsRequest.getCurrency(), response.getCurrency());
        assertEquals(paymentSetupsRequest.getPaymentType(), response.getPaymentType());
        assertEquals(paymentSetupsRequest.getReference(), response.getReference());
        assertEquals(paymentSetupsRequest.getDescription(), response.getDescription());
    }

    @Test
    void updatePaymentSetup_ShouldReturnValidResponse() {
        // Arrange
        final PaymentSetupsRequest paymentSetupsRequest = createValidPaymentSetupsRequest();
        final CompletableFuture<PaymentSetupsResponse> createFuture =
                checkoutApi.paymentSetupsClient().createPaymentSetup(paymentSetupsRequest);
        final PaymentSetupsResponse createResponse = createFuture.join();

        final PaymentSetupsRequest updateRequest = createValidPaymentSetupsRequest();
        updateRequest.setDescription("Updated description");

        // Act
        final CompletableFuture<PaymentSetupsResponse> updateFuture =
                checkoutApi.paymentSetupsClient().updatePaymentSetup(createResponse.getId(), updateRequest);
        final PaymentSetupsResponse response = updateFuture.join();

        // Assert
        assertNotNull(response);
        assertEquals(createResponse.getId(), response.getId());
        assertEquals("Updated description", response.getDescription());
    }

    @Test
    void getPaymentSetup_ShouldReturnValidResponse() {
        // Arrange
        final PaymentSetupsRequest paymentSetupsRequest = createValidPaymentSetupsRequest();
        final CompletableFuture<PaymentSetupsResponse> createFuture =
                checkoutApi.paymentSetupsClient().createPaymentSetup(paymentSetupsRequest);
        final PaymentSetupsResponse createResponse = createFuture.join();

        // Act
        final CompletableFuture<PaymentSetupsResponse> getFuture =
                checkoutApi.paymentSetupsClient().getPaymentSetup(createResponse.getId());
        final PaymentSetupsResponse response = getFuture.join();

        // Assert
        assertNotNull(response);
        assertEquals(createResponse.getId(), response.getId());
        assertEquals(paymentSetupsRequest.getProcessingChannelId(), response.getProcessingChannelId());
        assertEquals(paymentSetupsRequest.getAmount(), response.getAmount());
        assertEquals(paymentSetupsRequest.getCurrency(), response.getCurrency());
        assertEquals(paymentSetupsRequest.getPaymentType(), response.getPaymentType());
        assertEquals(paymentSetupsRequest.getReference(), response.getReference());
        assertEquals(paymentSetupsRequest.getDescription(), response.getDescription());
    }

    @Test
    @Disabled("Integration test - requires valid payment method option")
    void confirmPaymentSetup_ShouldReturnValidResponse() {
        // Arrange
        final PaymentSetupsRequest paymentSetupsRequest = createValidPaymentSetupsRequest();
        final CompletableFuture<PaymentSetupsResponse> createFuture =
                checkoutApi.paymentSetupsClient().createPaymentSetup(paymentSetupsRequest);
        final PaymentSetupsResponse createResponse = createFuture.join();

        // The name of the payment method to process the payment with (for example, tabby, klarna, card)
        final String paymentMethodName = "card";

        // Act
        final CompletableFuture<PaymentSetupsConfirmResponse> confirmFuture =
                checkoutApi.paymentSetupsClient().confirmPaymentSetup(createResponse.getId(), paymentMethodName);
        final PaymentSetupsConfirmResponse response = confirmFuture.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(paymentSetupsRequest.getAmount(), response.getAmount());
        assertEquals(paymentSetupsRequest.getCurrency(), response.getCurrency());
    }

    @Test
    void createPaymentSetupWithDeviceDetails_ShouldEchoDeviceAndReadEveryStatus() {
        // Arrange
        final PaymentSetupsRequest request = createCashAppPaymentSetupsRequest();

        // Act
        final PaymentSetupsResponse created =
                checkoutApi.paymentSetupsClient().createPaymentSetup(request).join();
        final PaymentSetupsResponse fetched =
                checkoutApi.paymentSetupsClient().getPaymentSetup(created.getId()).join();

        // Assert
        final CustomerDevice device = fetched.getCustomer().getDevice();
        assertEquals("en_US", device.getLocale());
        assertEquals("fp_abc123xyz", device.getFingerprint());
        assertEquals("203.0.113.0", device.getIpv4());
        assertEquals(CustomerDeviceClient.WEB, device.getClient());
        assertEquals(OsType.IOS, device.getOs());

        // A status value the SDK does not model is read as null and then dropped on write,
        // so every payment method the API returned must still carry its status here.
        final JsonObject methods = JsonParser.parseString(new GsonSerializer().toJson(fetched.getPaymentMethods()))
                .getAsJsonObject();
        assertFalse(methods.entrySet().isEmpty());
        for (final Map.Entry<String, JsonElement> method : methods.entrySet()) {
            assertTrue(method.getValue().getAsJsonObject().has("status"), method.getKey() + " lost its status");
        }
    }

    @Test
    void createPaymentSetupWithCustomerIdentifiers_ShouldEchoThem() {
        // Arrange
        final PaymentSetupsRequest request = createValidPaymentSetupsRequest();
        request.getCustomer().setId("cus_123456789");
        request.getCustomer().setCountry(CountryCode.GB);
        request.getCustomer().setTaxNumber("GB123456789");

        // Act
        final PaymentSetupsResponse created =
                checkoutApi.paymentSetupsClient().createPaymentSetup(request).join();
        final PaymentSetupsResponse fetched =
                checkoutApi.paymentSetupsClient().getPaymentSetup(created.getId()).join();

        // Assert
        assertEquals("cus_123456789", fetched.getCustomer().getId());
        assertEquals(CountryCode.GB, fetched.getCustomer().getCountry());
        assertEquals("GB123456789", fetched.getCustomer().getTaxNumber());
    }

    @Test
    void createPaymentSetupWithCashApp_ShouldReturnCashAppDetails() {
        // Arrange
        final PaymentSetupsRequest request = createCashAppPaymentSetupsRequest();

        // Act
        final PaymentSetupsResponse created =
                checkoutApi.paymentSetupsClient().createPaymentSetup(request).join();
        assumeTrue(created.getAvailablePaymentMethods() != null
                        && created.getAvailablePaymentMethods().contains("cashapp"),
                "Cash App Pay is not enabled on the sandbox processing channel");
        final PaymentSetupsResponse fetched =
                checkoutApi.paymentSetupsClient().getPaymentSetup(created.getId()).join();

        // Assert
        final CashApp cashApp = fetched.getPaymentMethods().getCashapp();
        assertNotNull(cashApp);
        assertNotNull(cashApp.getStatus());
        assertEquals(PaymentMethodInitialization.ENABLED, cashApp.getInitialization());
        assertEquals(Boolean.TRUE, cashApp.getCustomerProfileSharing());
    }

    private PaymentSetupsRequest createCashAppPaymentSetupsRequest() {
        final CashApp cashApp = new CashApp();
        cashApp.setInitialization(PaymentMethodInitialization.ENABLED);
        cashApp.setCustomerProfileSharing(true);

        final PaymentSetupsRequest request = createValidPaymentSetupsRequest();
        request.setCurrency(Currency.USD);
        request.setPaymentMethods(PaymentMethods.builder().cashapp(cashApp).build());
        request.getCustomer().setDevice(CustomerDevice.builder()
                .locale("en_US")
                .fingerprint("fp_abc123xyz")
                .ipv4("203.0.113.0")
                .client(CustomerDeviceClient.WEB)
                .os(OsType.IOS)
                .build());
        return request;
    }

    private PaymentSetupsRequest createValidPaymentSetupsRequest() {
        final Klarna klarna = new Klarna();
        klarna.setInitialization(PaymentMethodInitialization.DISABLED);

        return PaymentSetupsRequest.builder()
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .amount(1000L)
                .currency(Currency.GBP)
                .paymentType(PaymentType.REGULAR)
                .reference("TEST-REF-" + randomString(6))
                .description("Integration test payment setup")
                .settings(Settings.builder()
                        .successUrl("https://example.com/success")
                        .failureUrl("https://example.com/failure")
                        .build())
                .customer(Customer.builder()
                        .name("John Smith")
                        .email(CustomerEmail.builder()
                                .address("john.smith+" + randomString(6) + "@example.com")
                                .verified(true)
                                .build())
                        .phone(Phone.builder()
                                .countryCode("+44")
                                .number("207 946 0000")
                                .build())
                        .device(CustomerDevice.builder()
                                .locale("en_GB")
                                .build())
                        .build())
                .paymentMethods(PaymentMethods.builder()
                        .klarna(klarna)
                        .build())
                .build();
    }
    
    private String randomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }
}
