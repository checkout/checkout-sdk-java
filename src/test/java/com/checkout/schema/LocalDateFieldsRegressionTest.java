package com.checkout.schema;

import com.checkout.GsonSerializer;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.MerchantAccount;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.OrderSubMerchant;
import com.checkout.instruments.create.InstrumentData;
import com.checkout.payments.ProductRequest;
import com.checkout.payments.ProductResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression tests for {@code format: date} (yyyy-MM-dd) fields that were previously
 * typed as {@link java.time.Instant}.
 *
 * <p>An {@code Instant} serializes as an ISO-8601 datetime (e.g. {@code 2025-01-01T00:00:00Z}),
 * which the API rejects with a 422. These tests verify that each corrected field serializes
 * as a plain date string ({@code yyyy-MM-dd}).
 *
 * <p>Classes covered:
 * <ul>
 *   <li>{@link ProductRequest} – {@code service_ends_on}</li>
 *   <li>{@link ProductResponse} – {@code service_ends_on}</li>
 *   <li>{@link InstrumentData} – {@code date_of_signature}</li>
 *   <li>{@link MerchantAccount} – {@code registration_date}, {@code last_modified},
 *       {@code first_transaction_date}, {@code last_transaction_date}</li>
 *   <li>{@link OrderSubMerchant} – {@code registration_date}</li>
 * </ul>
 */
class LocalDateFieldsRegressionTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ── ProductRequest ────────────────────────────────────────────────────────

    @Test
    void productRequest_serviceEndsOn_shouldSerializeAsDateOnly() {
        ProductRequest request = ProductRequest.builder()
                .name("Premium Support")
                .serviceEndsOn(LocalDate.of(2025, 1, 1))
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"service_ends_on\":\"2025-01-01\""),
                "Expected yyyy-MM-dd but got: " + json);
    }

    @Test
    void productRequest_serviceEndsOn_shouldRoundTrip() {
        ProductRequest original = ProductRequest.builder()
                .name("Subscription Box")
                .quantity(2L)
                .unitPrice(5000L)
                .serviceEndsOn(LocalDate.of(2026, 6, 30))
                .build();

        String json = serializer.toJson(original);
        ProductRequest deserialized = serializer.fromJson(json, ProductRequest.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2026, 6, 30), deserialized.getServiceEndsOn());
        assertEquals("Subscription Box", deserialized.getName());
    }

    @Test
    void productRequest_serviceEndsOn_shouldDeserializeFromSwaggerExample() {
        String json = "{\"name\":\"Item\",\"quantity\":1,\"unit_price\":1000,\"service_ends_on\":\"2025-01-01\"}";

        ProductRequest request = serializer.fromJson(json, ProductRequest.class);

        assertNotNull(request);
        assertEquals(LocalDate.of(2025, 1, 1), request.getServiceEndsOn());
    }

    // ── ProductResponse ───────────────────────────────────────────────────────

    @Test
    void productResponse_serviceEndsOn_shouldSerializeAsDateOnly() {
        ProductResponse response = ProductResponse.builder()
                .name("Annual License")
                .serviceEndsOn(LocalDate.of(2025, 12, 31))
                .build();

        String json = serializer.toJson(response);

        assertTrue(json.contains("\"service_ends_on\":\"2025-12-31\""),
                "Expected yyyy-MM-dd but got: " + json);
    }

    @Test
    void productResponse_serviceEndsOn_shouldRoundTrip() {
        ProductResponse original = ProductResponse.builder()
                .name("Service")
                .quantity(1L)
                .serviceEndsOn(LocalDate.of(2027, 3, 15))
                .build();

        String json = serializer.toJson(original);
        ProductResponse deserialized = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2027, 3, 15), deserialized.getServiceEndsOn());
    }

    @Test
    void productResponse_serviceEndsOn_shouldDeserializeFromSwaggerExample() {
        String json = "{\"name\":\"Service\",\"quantity\":1,\"service_ends_on\":\"2025-01-01\"}";

        ProductResponse response = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(response);
        assertEquals(LocalDate.of(2025, 1, 1), response.getServiceEndsOn());
    }

    // ── InstrumentData ────────────────────────────────────────────────────────

    @Test
    void instrumentData_dateOfSignature_shouldSerializeAsDateOnly() {
        InstrumentData data = InstrumentData.builder()
                .mandateId("MANDATE123")
                .dateOfSignature(LocalDate.of(2021, 1, 1))
                .build();

        String json = serializer.toJson(data);

        assertTrue(json.contains("\"date_of_signature\":\"2021-01-01\""),
                "Expected yyyy-MM-dd but got: " + json);
    }

    @Test
    void instrumentData_dateOfSignature_shouldRoundTrip() {
        InstrumentData original = InstrumentData.builder()
                .mandateId("MND-987")
                .dateOfSignature(LocalDate.of(2020, 6, 15))
                .build();

        String json = serializer.toJson(original);
        InstrumentData deserialized = serializer.fromJson(json, InstrumentData.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2020, 6, 15), deserialized.getDateOfSignature());
        assertEquals("MND-987", deserialized.getMandateId());
    }

    @Test
    void instrumentData_dateOfSignature_shouldDeserializeFromSwaggerExample() {
        String json = "{\"mandate_id\":\"abc123\",\"date_of_signature\":\"2021-01-01\"}";

        InstrumentData data = serializer.fromJson(json, InstrumentData.class);

        assertNotNull(data);
        assertEquals(LocalDate.of(2021, 1, 1), data.getDateOfSignature());
    }

    // ── MerchantAccount ───────────────────────────────────────────────────────

    @Test
    void merchantAccount_allDateFields_shouldSerializeAsDateOnly() {
        MerchantAccount account = MerchantAccount.builder()
                .id("ACC-001")
                .registrationDate(LocalDate.of(2023, 5, 1))
                .lastModified(LocalDate.of(2023, 5, 1))
                .firstTransactionDate(LocalDate.of(2023, 9, 15))
                .lastTransactionDate(LocalDate.of(2025, 3, 28))
                .returningCustomer(true)
                .totalOrderCount(42)
                .lastPaymentAmount(10000L)
                .build();

        String json = serializer.toJson(account);

        assertTrue(json.contains("\"registration_date\":\"2023-05-01\""), "registration_date: " + json);
        assertTrue(json.contains("\"last_modified\":\"2023-05-01\""), "last_modified: " + json);
        assertTrue(json.contains("\"first_transaction_date\":\"2023-09-15\""), "first_transaction_date: " + json);
        assertTrue(json.contains("\"last_transaction_date\":\"2025-03-28\""), "last_transaction_date: " + json);
    }

    @Test
    void merchantAccount_allDateFields_shouldRoundTrip() {
        MerchantAccount original = MerchantAccount.builder()
                .id("ACC-999")
                .registrationDate(LocalDate.of(2022, 1, 10))
                .lastModified(LocalDate.of(2023, 7, 20))
                .firstTransactionDate(LocalDate.of(2022, 2, 5))
                .lastTransactionDate(LocalDate.of(2025, 1, 1))
                .returningCustomer(false)
                .totalOrderCount(5)
                .lastPaymentAmount(50000L)
                .build();

        String json = serializer.toJson(original);
        MerchantAccount deserialized = serializer.fromJson(json, MerchantAccount.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2022, 1, 10), deserialized.getRegistrationDate());
        assertEquals(LocalDate.of(2023, 7, 20), deserialized.getLastModified());
        assertEquals(LocalDate.of(2022, 2, 5), deserialized.getFirstTransactionDate());
        assertEquals(LocalDate.of(2025, 1, 1), deserialized.getLastTransactionDate());
        assertEquals(false, deserialized.getReturningCustomer());
        assertEquals(5, deserialized.getTotalOrderCount());
        assertEquals(50000L, deserialized.getLastPaymentAmount());
    }

    @Test
    void merchantAccount_allDateFields_shouldDeserializeFromSwaggerExample() {
        String json = "{" +
                "\"id\":\"ACC-123\"," +
                "\"registration_date\":\"2023-05-01\"," +
                "\"last_modified\":\"2023-05-01\"," +
                "\"first_transaction_date\":\"2023-09-15\"," +
                "\"last_transaction_date\":\"2025-03-28\"," +
                "\"returning_customer\":true," +
                "\"total_order_count\":10," +
                "\"last_payment_amount\":20000" +
                "}";

        MerchantAccount account = serializer.fromJson(json, MerchantAccount.class);

        assertNotNull(account);
        assertEquals("ACC-123", account.getId());
        assertEquals(LocalDate.of(2023, 5, 1), account.getRegistrationDate());
        assertEquals(LocalDate.of(2023, 5, 1), account.getLastModified());
        assertEquals(LocalDate.of(2023, 9, 15), account.getFirstTransactionDate());
        assertEquals(LocalDate.of(2025, 3, 28), account.getLastTransactionDate());
        assertEquals(true, account.getReturningCustomer());
    }

    // ── OrderSubMerchant ──────────────────────────────────────────────────────

    @Test
    void orderSubMerchant_registrationDate_shouldSerializeAsDateOnly() {
        OrderSubMerchant subMerchant = OrderSubMerchant.builder()
                .id("SUB-001")
                .registrationDate(LocalDate.of(2023, 1, 15))
                .build();

        String json = serializer.toJson(subMerchant);

        assertTrue(json.contains("\"registration_date\":\"2023-01-15\""),
                "Expected yyyy-MM-dd but got: " + json);
    }

    @Test
    void orderSubMerchant_registrationDate_shouldRoundTrip() {
        OrderSubMerchant original = OrderSubMerchant.builder()
                .id("SUB-999")
                .productCategory("Electronics")
                .numberOfTrades(100)
                .registrationDate(LocalDate.of(2021, 8, 22))
                .build();

        String json = serializer.toJson(original);
        OrderSubMerchant deserialized = serializer.fromJson(json, OrderSubMerchant.class);

        assertNotNull(deserialized);
        assertEquals(LocalDate.of(2021, 8, 22), deserialized.getRegistrationDate());
        assertEquals("SUB-999", deserialized.getId());
        assertEquals("Electronics", deserialized.getProductCategory());
    }

    @Test
    void orderSubMerchant_registrationDate_shouldDeserializeFromSwaggerExample() {
        String json = "{\"id\":\"SUB-123\",\"registration_date\":\"2023-01-15\",\"number_of_trades\":50}";

        OrderSubMerchant subMerchant = serializer.fromJson(json, OrderSubMerchant.class);

        assertNotNull(subMerchant);
        assertEquals(LocalDate.of(2023, 1, 15), subMerchant.getRegistrationDate());
        assertEquals(50, subMerchant.getNumberOfTrades());
    }
}
