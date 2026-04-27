package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.common.PaymentMethodType;
import com.checkout.handlepaymentsandpayouts.flow.entities.CardConfiguration;
import com.checkout.handlepaymentsandpayouts.flow.entities.Customer;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethodConfiguration;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionSubmitRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCompleteRequest;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSubmissionResponse;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.LocaleType;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProductRequest;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.StorePaymentDetailsType;
import com.checkout.payments.ThreeDSRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FlowTestIT extends SandboxTestFixture {

    FlowTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldCreatePaymentSession() {
        // Arrange
        final PaymentSessionCreateRequest request = createPaymentSessionCreateRequest();

        // Act
        final CompletableFuture<PaymentSessionResponse> future =
                checkoutApi.flowClient().requestPaymentSession(request);
        final PaymentSessionResponse response = future.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getPaymentSessionSecret());
    }

    @Test
    @Disabled("This test requires a valid merchant configuration for Flow")
    void shouldSubmitPaymentSession() {
        // Arrange
        final PaymentSessionCreateRequest createRequest = createPaymentSessionCreateRequest();
        final CompletableFuture<PaymentSessionResponse> createFuture =
                checkoutApi.flowClient().requestPaymentSession(createRequest);
        final PaymentSessionResponse createResponse = createFuture.join();

        final PaymentSessionSubmitRequest submitRequest = createPaymentSessionSubmitRequest();

        // Act
        final CompletableFuture<PaymentSubmissionResponse> submitFuture =
                checkoutApi.flowClient().submitPaymentSession(createResponse.getId(), submitRequest);
        final PaymentSubmissionResponse response = submitFuture.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getType());
    }

    @Test
    @Disabled("This test requires a valid merchant configuration for Flow")
    void shouldCompletePaymentSession() {
        // Arrange
        final PaymentSessionCompleteRequest request = createPaymentSessionCompleteRequest();

        // Act
        final CompletableFuture<PaymentSubmissionResponse> future =
                checkoutApi.flowClient().requestPaymentSessionWithPayment(request);
        final PaymentSubmissionResponse response = future.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getType());
    }

    // Synchronous methods
    @Test
    void shouldCreatePaymentSessionSync() {
        // Arrange
        final PaymentSessionCreateRequest request = createPaymentSessionCreateRequest();

        // Act
        final PaymentSessionResponse response = checkoutApi.flowClient().requestPaymentSessionSync(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getPaymentSessionSecret());
    }

    @Test
    @Disabled("This test requires a valid merchant configuration for Flow")
    void shouldSubmitPaymentSessionSync() {
        // Arrange
        final PaymentSessionCreateRequest createRequest = createPaymentSessionCreateRequest();
        final PaymentSessionResponse createResponse = checkoutApi.flowClient().requestPaymentSessionSync(createRequest);

        final PaymentSessionSubmitRequest submitRequest = createPaymentSessionSubmitRequest();

        // Act
        final PaymentSubmissionResponse response = checkoutApi.flowClient().submitPaymentSessionSync(createResponse.getId(), submitRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getType());
    }

    @Test
    @Disabled("This test requires a valid merchant configuration for Flow")
    void shouldCompletePaymentSessionSync() {
        // Arrange
        final PaymentSessionCompleteRequest request = createPaymentSessionCompleteRequest();

        // Act
        final PaymentSubmissionResponse response = checkoutApi.flowClient().requestPaymentSessionWithPaymentSync(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getType());
    }

    // Common methods
    private PaymentSessionCreateRequest createPaymentSessionCreateRequest() {
        return PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .paymentType(PaymentType.REGULAR)
                .reference("ORD-123A")
                .description("Payment for gold necklace")
                .displayName("Company Test")
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .billing(createBillingInformation())
                .billingDescriptor(createBillingDescriptor())
                .risk(createRiskRequest())
                .threeDS(createThreeDSRequest())
                .capture(true)
                .locale(LocaleType.EN_GB)
                .enabledPaymentMethods(Collections.singletonList(PaymentMethodType.CARD))
                .paymentMethodConfiguration(createPaymentMethodConfiguration())
                .build();
    }

    private PaymentSessionCreateRequest createPaymentSessionCreateRequestWithBlik() {
        return PaymentSessionCreateRequest.builder()
                .amount(2000L)
                .currency(Currency.PLN)
                .reference("ORD-BLIK-789")
                .description("BLIK payment for mobile transaction")
                .displayName("Company BLIK Test")
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .billing(createBillingInformation())
                .billingDescriptor(createBillingDescriptor())
                .risk(createRiskRequest())
                .threeDS(createThreeDSRequest())
                .capture(true)
                .locale(LocaleType.EN_GB)
                .enabledPaymentMethods(Collections.singletonList(PaymentMethodType.BLIK))
                .paymentMethodConfiguration(createPaymentMethodConfiguration())
                .build();
    }

    @Test
    void shouldCreatePaymentSessionWithBlikInDisabledMethods() {
        // Test BLIK in disabled payment methods list
        final PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.EUR)
                .paymentType(PaymentType.REGULAR)
                .reference("ORD-NO-BLIK-456")
                .description("Payment without BLIK")
                .displayName("No BLIK Test")
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .billing(createBillingInformation())
                .billingDescriptor(createBillingDescriptor())
                .risk(createRiskRequest())
                .threeDS(createThreeDSRequest())
                .capture(true)
                .locale(LocaleType.EN_GB)
                .enabledPaymentMethods(Collections.singletonList(PaymentMethodType.CARD))
                .disabledPaymentMethods(Collections.singletonList(PaymentMethodType.BLIK))
                .paymentMethodConfiguration(createPaymentMethodConfiguration())
                .build();

        final CompletableFuture<PaymentSessionResponse> future =
                checkoutApi.flowClient().requestPaymentSession(request);
        final PaymentSessionResponse response = future.join();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getPaymentSessionSecret());
    }

    private PaymentSessionSubmitRequest createPaymentSessionSubmitRequest() {
        return PaymentSessionSubmitRequest.builder()
                .sessionData("encrypted_session_data")
                .amount(1000L)
                .reference("ORD-123A")
                .items(Collections.singletonList(createProductRequest()))
                .threeDS(createThreeDSRequest())
                .paymentType(PaymentType.REGULAR)
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .billingDescriptor(createBillingDescriptor())
                .paymentMethodConfiguration(createPaymentMethodConfiguration())
                .capture(true)
                .ipAddress("192.168.1.1")
                .build();
    }

    private PaymentSessionCompleteRequest createPaymentSessionCompleteRequest() {
        return PaymentSessionCompleteRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .paymentType(PaymentType.REGULAR)
                .reference("ORD-5023-4E89")
                .description("Payment for gold necklace")
                .displayName("Company Test")
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .billing(createBillingInformation())
                .billingDescriptor(createBillingDescriptor())
                .risk(createRiskRequest())
                .threeDS(createThreeDSRequest())
                .capture(true)
                .locale(LocaleType.EN_GB)
                .sessionData("encrypted_session_data")
                .build();
    }

    private BillingInformation createBillingInformation() {
        return BillingInformation.builder()
                .address(Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(Phone.builder()
                        .countryCode("+1")
                        .number("415 555 2671")
                        .build())
                .build();
    }

    private BillingDescriptor createBillingDescriptor() {
        return BillingDescriptor.builder()
                .name("Company Test")
                .city("London")
                .reference(UUID.randomUUID().toString())
                .build();
    }

    private RiskRequest createRiskRequest() {
        return RiskRequest.builder()
                .enabled(false)
                .build();
    }

    private ThreeDSRequest createThreeDSRequest() {
        return ThreeDSRequest.builder()
                .enabled(false)
                .build();
    }

    private PaymentMethodConfiguration createPaymentMethodConfiguration() {
        final CardConfiguration cardConfiguration = new CardConfiguration();
        cardConfiguration.setStorePaymentDetails(StorePaymentDetailsType.ENABLED);
        
        return PaymentMethodConfiguration.builder()
                .card(cardConfiguration)
                .build();
    }

    private ProductRequest createProductRequest() {
        return ProductRequest.builder()
                .reference("string")
                .name("Gold Necklace")
                .quantity(1L)
                .unitPrice(1000L)
                .build();
    }

    private Map<String, Object> createMetadata() {
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("coupon_code", "NY2018");
        return metadata;
    }

    private String randomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    @Test
    void paymentSessionCreateRequest_shouldHaveAllRequiredPropertiesForJsonCompatibility() {
        // This test validates that PaymentSessionCreateRequest has the necessary properties
        // to be compatible with the comprehensive JSON structure from the API documentation
        final PaymentSessionCreateRequest request = new PaymentSessionCreateRequest();

        // Validate that all major property groups from the JSON are available:
        
        // Basic properties from PaymentSessionBase
        request.setAmount(1000L);
        request.setReference("ORD-123A");
        request.setPaymentType(PaymentType.REGULAR);
        request.setItems(Collections.singletonList(createProductRequest()));
        request.setThreeDS(createThreeDSRequest());

        // Properties from PaymentSessionInfo
        request.setCurrency(Currency.USD);
        request.setDescription("Payment for gold necklace");
        request.setBillingDescriptor(createBillingDescriptor());
        request.setBilling(createBillingInformation());
        request.setSuccessUrl("https://example.com/success");
        request.setFailureUrl("https://example.com/failure");
        request.setMetadata(createMetadata());
        request.setLocale(LocaleType.AR);
        request.setDisplayName("Test Payment");
        request.setProcessingChannelId("pc_123");
        request.setCapture(true);
        request.setRisk(createRiskRequest());

        // Properties specific to PaymentSessionCreateRequest
        request.setEnabledPaymentMethods(Collections.singletonList(PaymentMethodType.CARD));
        request.setDisabledPaymentMethods(Collections.singletonList(PaymentMethodType.EPS));
        request.setPaymentMethodConfiguration(createPaymentMethodConfiguration());
        request.setIpAddress("127.0.0.1");

        // Assertions to ensure the object is properly constructed
        assertNotNull(request);
        assertEquals(1000L, request.getAmount());
        assertEquals(Currency.USD, request.getCurrency());
        assertEquals("ORD-123A", request.getReference());
        assertEquals("Payment for gold necklace", request.getDescription());
        assertNotNull(request.getBilling());
        assertNotNull(request.getPaymentMethodConfiguration());
        assertEquals("127.0.0.1", request.getIpAddress());
    }

    @Test
    void paymentSessionSubmitRequest_shouldHaveAllRequiredPropertiesForJsonCompatibility() {
        final PaymentSessionSubmitRequest request = new PaymentSessionSubmitRequest();

        // PaymentSessionSubmitRequest specific properties
        request.setSessionData("string");
        request.setSuccessUrl("https://example.com/payments/success");
        request.setFailureUrl("https://example.com/payments/failure");
        request.setBillingDescriptor(createBillingDescriptor());
        request.setPaymentMethodConfiguration(createPaymentMethodConfiguration());
        request.setCapture(true);
        request.setIpAddress("90.197.169.245");

        // Basic properties from PaymentSessionBase (inherited)
        request.setAmount(1000L);
        request.setReference("ORD-123A");
        request.setItems(Collections.singletonList(createProductRequest()));
        request.setThreeDS(createThreeDSRequest());
        request.setPaymentType(PaymentType.REGULAR);

        // Assertions
        assertNotNull(request);
        assertEquals("string", request.getSessionData());
        assertEquals("https://example.com/payments/success", request.getSuccessUrl());
        assertEquals("https://example.com/payments/failure", request.getFailureUrl());
        assertNotNull(request.getBillingDescriptor());
        assertNotNull(request.getPaymentMethodConfiguration());
        assertEquals(true, request.getCapture());
        assertEquals("90.197.169.245", request.getIpAddress());
        assertEquals(1000L, request.getAmount());
        assertEquals("ORD-123A", request.getReference());
        assertNotNull(request.getItems());
        assertEquals(1, request.getItems().size());
        assertEquals("Gold Necklace", request.getItems().get(0).getName());
        assertNotNull(request.getThreeDS());
        assertEquals(false, request.getThreeDS().getEnabled());
        assertEquals(PaymentType.REGULAR, request.getPaymentType());
    }

    @Test
    void paymentSessionCompleteRequest_shouldHaveAllRequiredPropertiesForJsonCompatibility() {
        // This test validates that PaymentSessionCompleteRequest has the necessary properties
        // to be compatible with the complete JSON structure from the API documentation
        final PaymentSessionCompleteRequest request = new PaymentSessionCompleteRequest();

        // PaymentSessionCompleteRequest specific property
        request.setSessionData("string");

        // Properties from PaymentSessionBase (inherited through PaymentSessionInfo)
        request.setAmount(1000L);
        request.setReference("ORD-123A");
        request.setItems(Collections.singletonList(createProductRequest()));
        request.setThreeDS(createThreeDSRequest());
        request.setPaymentType(PaymentType.REGULAR);

        // Properties from PaymentSessionInfo (inherited)
        request.setCurrency(Currency.USD);
        request.setBilling(createBillingInformation());
        request.setBillingDescriptor(createBillingDescriptor());
        request.setDescription("Payment for gold necklace");
        request.setSuccessUrl("https://example.com/payments/success");
        request.setFailureUrl("https://example.com/payments/failure");
        request.setMetadata(createMetadata());
        request.setLocale(LocaleType.AR);
        request.setDisplayName("string");
        request.setProcessingChannelId("string");
        request.setRisk(createRiskRequest());
        request.setCapture(true);

        // Assertions to verify all properties from the JSON can be assigned
        assertNotNull(request);
        assertEquals("string", request.getSessionData());
        assertEquals(1000L, request.getAmount());
        assertEquals(Currency.USD, request.getCurrency());
        assertEquals(PaymentType.REGULAR, request.getPaymentType());
        assertNotNull(request.getBilling());
        assertNotNull(request.getBillingDescriptor());
        assertEquals("ORD-123A", request.getReference());
        assertEquals("Payment for gold necklace", request.getDescription());
        assertEquals("string", request.getProcessingChannelId());
        assertEquals(1, request.getItems().size());
        assertNotNull(request.getRisk());
        assertEquals("string", request.getDisplayName());
        assertEquals("https://example.com/payments/success", request.getSuccessUrl());
        assertEquals("https://example.com/payments/failure", request.getFailureUrl());
        assertEquals(1, request.getMetadata().size());
        assertEquals(LocaleType.AR, request.getLocale());
        assertNotNull(request.getThreeDS());
        assertEquals(true, request.getCapture());
    }

    @Test
    @Disabled("This test requires merchant configuration that supports customer.summary fields")
    void shouldCreatePaymentSessionWithCustomerSummary() {
        // Arrange
        final Customer.CustomerSummary summary = Customer.CustomerSummary.builder()
                .registrationDate(LocalDate.of(2023, 1, 15))
                .firstTransactionDate(LocalDate.of(2023, 2, 20))
                .lastPaymentDate(LocalDate.of(2024, 3, 10))
                .totalOrderCount(5)
                .lastPaymentAmount(100.50)
                .isPremiumCustomer(true)
                .isReturningCustomer(true)
                .lifetimeValue(500.75)
                .build();

        final Customer customer = Customer.builder()
                .email("customer@example.com")
                .name("Test Customer")
                .id("cust_" + randomString(10))
                .phone(Phone.builder()
                        .countryCode("+1")
                        .number("415 555 2671")
                        .build())
                .taxNumber("VAT123456")
                .summary(summary)
                .build();

        final PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .paymentType(PaymentType.REGULAR)
                .reference("ORD-" + randomString(6))
                .description("Payment with customer summary")
                .displayName("Company Test")
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .billing(createBillingInformation())
                .billingDescriptor(createBillingDescriptor())
                .risk(createRiskRequest())
                .threeDS(createThreeDSRequest())
                .capture(true)
                .locale(LocaleType.EN_GB)
                .customer(customer)
                .enabledPaymentMethods(Collections.singletonList(PaymentMethodType.CARD))
                .paymentMethodConfiguration(createPaymentMethodConfiguration())
                .build();

        // Act
        final CompletableFuture<PaymentSessionResponse> future =
                checkoutApi.flowClient().requestPaymentSession(request);
        final PaymentSessionResponse response = future.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getPaymentSessionSecret());
    }
}