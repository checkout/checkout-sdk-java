package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDevice;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerEmail;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.KlarnaAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.checkout.payments.PaymentType;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        // This would require extracting a payment method option ID from the create response
        final String paymentMethodOptionId = "opt_test_12345"; // This should come from the payment setup response

        // Act
        final CompletableFuture<PaymentSetupsConfirmResponse> confirmFuture =
                checkoutApi.paymentSetupsClient().confirmPaymentSetup(createResponse.getId(), paymentMethodOptionId);
        final PaymentSetupsConfirmResponse response = confirmFuture.join();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getActionId());
        assertEquals(paymentSetupsRequest.getAmount(), response.getAmount());
        assertEquals(paymentSetupsRequest.getCurrency(), response.getCurrency());
        assertNotNull(response.getProcessedOn());
    }

    private PaymentSetupsRequest createValidPaymentSetupsRequest() {
        final Klarna klarna = new Klarna();
        klarna.setInitialization(PaymentMethodInitialization.DISABLED);
        
        final KlarnaAccountHolder accountHolder = KlarnaAccountHolder.builder()
                .billingAddress(Address.builder()
                        .addressLine1("123 High Street")
                        .city("London")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .build();
        
        klarna.setAccountHolder(accountHolder);

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
