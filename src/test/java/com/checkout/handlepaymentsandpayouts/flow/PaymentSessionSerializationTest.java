package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.common.PaymentMethodType;
import com.checkout.handlepaymentsandpayouts.flow.entities.Customer;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.payments.LocaleType;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to validate GSON serialization/deserialization of PaymentSession requests,
 * particularly focusing on LocalDate fields in Customer.CustomerSummary.
 */
class PaymentSessionSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializePaymentSessionCreateRequestWithCustomerSummary() {
        Customer.CustomerSummary summary = Customer.CustomerSummary.builder()
                .registrationDate(LocalDate.of(2023, 1, 15))
                .firstTransactionDate(LocalDate.of(2023, 2, 20))
                .lastPaymentDate(LocalDate.of(2024, 3, 10))
                .totalOrderCount(5)
                .lastPaymentAmount(100.50)
                .isPremiumCustomer(true)
                .isReturningCustomer(true)
                .lifetimeValue(500.75)
                .build();

        Customer customer = Customer.builder()
                .email("test@example.com")
                .name("Test Customer")
                .id("cust_123")
                .taxNumber("VAT123456")
                .summary(summary)
                .build();

        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .reference("test-ref-123")
                .paymentType(PaymentType.REGULAR)
                .description("Test payment with customer summary")
                .customer(customer)
                .locale(LocaleType.EN_GB)
                .enabledPaymentMethods(Collections.singletonList(PaymentMethodType.CARD))
                .capture(true)
                .build();

        assertDoesNotThrow(() -> {
            String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("2023-01-15"), "Should contain registration_date");
            assertTrue(json.contains("2023-02-20"), "Should contain first_transaction_date");
            assertTrue(json.contains("2024-03-10"), "Should contain last_payment_date");
            assertTrue(json.contains("test@example.com"), "Should contain customer email");
            assertTrue(json.contains("Test Customer"), "Should contain customer name");
        }, "Should serialize PaymentSessionCreateRequest with Customer.CustomerSummary without GSON reflection errors");
    }

    @Test
    void shouldDeserializePaymentSessionCreateRequestWithCustomerSummary() {
        String json = "{"
                + "\"amount\":1000,"
                + "\"currency\":\"USD\","
                + "\"reference\":\"test-ref-123\","
                + "\"payment_type\":\"Regular\","
                + "\"description\":\"Test payment\","
                + "\"customer\":{"
                + "  \"email\":\"test@example.com\","
                + "  \"name\":\"Test Customer\","
                + "  \"id\":\"cust_123\","
                + "  \"summary\":{"
                + "    \"registration_date\":\"2023-01-15\","
                + "    \"first_transaction_date\":\"2023-02-20\","
                + "    \"last_payment_date\":\"2024-03-10\","
                + "    \"total_order_count\":5,"
                + "    \"last_payment_amount\":100.50,"
                + "    \"is_premium_customer\":true,"
                + "    \"is_returning_customer\":true,"
                + "    \"lifetime_value\":500.75"
                + "  }"
                + "},"
                + "\"locale\":\"en-GB\","
                + "\"capture\":true"
                + "}";

        assertDoesNotThrow(() -> {
            PaymentSessionCreateRequest request = serializer.fromJson(json, PaymentSessionCreateRequest.class);
            assertNotNull(request);
            assertNotNull(request.getCustomer());
            assertNotNull(request.getCustomer().getSummary());
            assertEquals(LocalDate.of(2023, 1, 15), request.getCustomer().getSummary().getRegistrationDate());
            assertEquals(LocalDate.of(2023, 2, 20), request.getCustomer().getSummary().getFirstTransactionDate());
            assertEquals(LocalDate.of(2024, 3, 10), request.getCustomer().getSummary().getLastPaymentDate());
            assertEquals(5, request.getCustomer().getSummary().getTotalOrderCount());
            assertEquals(100.50, request.getCustomer().getSummary().getLastPaymentAmount());
            assertTrue(request.getCustomer().getSummary().getIsPremiumCustomer());
            assertTrue(request.getCustomer().getSummary().getIsReturningCustomer());
            assertEquals(500.75, request.getCustomer().getSummary().getLifetimeValue());
        }, "Should deserialize PaymentSessionCreateRequest with Customer.CustomerSummary without GSON reflection errors");
    }

    @Test
    void shouldSerializePaymentSessionCreateRequestWithBlikPaymentMethod() {
        Customer customer = Customer.builder()
                .email("blik@example.com")
                .name("BLIK Customer")
                .id("cust_blik_123")
                .build();

        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(2000L)
                .currency(Currency.EUR)
                .reference("blik-ref-456")
                .paymentType(PaymentType.REGULAR)
                .description("Test BLIK payment")
                .customer(customer)
                .locale(LocaleType.EN_GB)
                .enabledPaymentMethods(Collections.singletonList(PaymentMethodType.BLIK))
                .capture(true)
                .build();

        assertDoesNotThrow(() -> {
            String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("\"blik\""), "Should contain serialized BLIK payment method");
            assertTrue(json.contains("blik@example.com"), "Should contain customer email");
            assertTrue(json.contains("BLIK Customer"), "Should contain customer name");
        }, "Should serialize PaymentSessionCreateRequest with BLIK payment method without errors");
    }

    @Test
    void shouldDeserializePaymentSessionCreateRequestWithBlikPaymentMethod() {
        String json = "{"
                + "\"amount\":2000,"
                + "\"currency\":\"EUR\","
                + "\"reference\":\"blik-ref-456\","
                + "\"payment_type\":\"Regular\","
                + "\"description\":\"Test BLIK payment\","
                + "\"customer\":{"
                + "  \"email\":\"blik@example.com\","
                + "  \"name\":\"BLIK Customer\","
                + "  \"id\":\"cust_blik_123\""
                + "},"
                + "\"locale\":\"en-GB\","
                + "\"enabled_payment_methods\":[\"blik\"],"
                + "\"capture\":true"
                + "}";

        assertDoesNotThrow(() -> {
            PaymentSessionCreateRequest request = serializer.fromJson(json, PaymentSessionCreateRequest.class);
            assertNotNull(request);
            assertNotNull(request.getEnabledPaymentMethods());
            assertEquals(1, request.getEnabledPaymentMethods().size());
            assertEquals(PaymentMethodType.BLIK, request.getEnabledPaymentMethods().get(0));
            assertEquals("blik@example.com", request.getCustomer().getEmail());
            assertEquals("BLIK Customer", request.getCustomer().getName());
        }, "Should deserialize PaymentSessionCreateRequest with BLIK payment method without errors");
    }

    @Test
    void shouldRoundTripSerializePaymentSessionCreateRequestWithCustomer() {
        Customer.CustomerSummary summary = Customer.CustomerSummary.builder()
                .registrationDate(LocalDate.of(2022, 6, 1))
                .firstTransactionDate(LocalDate.of(2022, 7, 15))
                .lastPaymentDate(LocalDate.of(2024, 1, 20))
                .totalOrderCount(10)
                .lastPaymentAmount(250.00)
                .isPremiumCustomer(false)
                .isReturningCustomer(true)
                .lifetimeValue(1250.00)
                .build();

        Customer customer = Customer.builder()
                .email("roundtrip@example.com")
                .name("Round Trip Customer")
                .summary(summary)
                .build();

        PaymentSessionCreateRequest originalRequest = PaymentSessionCreateRequest.builder()
                .amount(5000L)
                .currency(Currency.EUR)
                .reference("roundtrip-ref")
                .customer(customer)
                .build();

        String json = serializer.toJson(originalRequest);
        PaymentSessionCreateRequest deserializedRequest = serializer.fromJson(json, PaymentSessionCreateRequest.class);

        assertNotNull(deserializedRequest);
        assertNotNull(deserializedRequest.getCustomer());
        assertNotNull(deserializedRequest.getCustomer().getSummary());
        assertEquals(originalRequest.getCustomer().getSummary().getRegistrationDate(),
                deserializedRequest.getCustomer().getSummary().getRegistrationDate());
        assertEquals(originalRequest.getCustomer().getSummary().getFirstTransactionDate(),
                deserializedRequest.getCustomer().getSummary().getFirstTransactionDate());
        assertEquals(originalRequest.getCustomer().getSummary().getLastPaymentDate(),
                deserializedRequest.getCustomer().getSummary().getLastPaymentDate());
    }

    @Test
    void shouldSerializeCustomerWithOnlyRequiredFields() {
        Customer customer = Customer.builder()
                .email("minimal@example.com")
                .name("Minimal Customer")
                .build();

        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("minimal-ref")
                .customer(customer)
                .build();

        assertDoesNotThrow(() -> {
            String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("minimal@example.com"));
        });
    }

    @Test
    void shouldSerializeCustomerWithNullSummary() {
        Customer customer = Customer.builder()
                .email("nosummary@example.com")
                .name("No Summary Customer")
                .summary(null)
                .build();

        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .reference("nosummary-ref")
                .customer(customer)
                .build();

        assertDoesNotThrow(() -> {
            String json = serializer.toJson(request);
            assertNotNull(json);
        });
    }

    @Test
    void shouldSerializeCustomerSummaryWithPartialDates() {
        Customer.CustomerSummary summary = Customer.CustomerSummary.builder()
                .registrationDate(LocalDate.of(2023, 1, 15))
                .totalOrderCount(3)
                .build();

        Customer customer = Customer.builder()
                .email("partial@example.com")
                .summary(summary)
                .build();

        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .reference("partial-ref")
                .customer(customer)
                .build();

        assertDoesNotThrow(() -> {
            String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("2023-01-15"));
        });
    }

    @Test
    void shouldHandleLocalDateInCompactFormat() {
        String json = "{"
                + "\"amount\":1000,"
                + "\"currency\":\"USD\","
                + "\"reference\":\"compact-ref\","
                + "\"customer\":{"
                + "  \"email\":\"test@example.com\","
                + "  \"summary\":{"
                + "    \"registration_date\":\"20230115\""
                + "  }"
                + "}"
                + "}";

        assertDoesNotThrow(() -> {
            PaymentSessionCreateRequest request = serializer.fromJson(json, PaymentSessionCreateRequest.class);
            assertNotNull(request);
            assertNotNull(request.getCustomer());
            assertNotNull(request.getCustomer().getSummary());
            assertEquals(LocalDate.of(2023, 1, 15), request.getCustomer().getSummary().getRegistrationDate());
        }, "Should deserialize compact date format (yyyyMMdd)");
    }
}
