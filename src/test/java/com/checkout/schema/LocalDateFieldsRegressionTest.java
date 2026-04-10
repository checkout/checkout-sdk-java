package com.checkout.schema;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.MerchantAccount;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.OrderSubMerchant;
import com.checkout.instruments.create.InstrumentData;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProductRequest;
import com.checkout.payments.ProductResponse;
import com.checkout.payments.request.ItemSubType;
import com.checkout.payments.request.ItemType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression tests for {@code format: date} (yyyy-MM-dd) fields that were previously
 * typed as {@link java.time.Instant}.
 *
 * <p>An {@code Instant} serializes as an ISO-8601 datetime (e.g. {@code 2025-01-01T00:00:00Z}),
 * which the API rejects with a 422. These tests verify that each corrected field serializes
 * as a plain date string ({@code yyyy-MM-dd}).
 *
 * <p>Every round-trip test sets and asserts <strong>all</strong> properties of the class,
 * not only the corrected field — as required by the review-integrity checklist.
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

    // ── ProductRequest — ALL properties ──────────────────────────────────────

    @Test
    void productRequest_allProperties_roundTrip() {
        ProductRequest original = ProductRequest.builder()
                .type(ItemType.PHYSICAL)
                .subType(ItemSubType.BLOCKCHAIN)
                .name("Hotel Room — Deluxe")
                .quantity(2L)
                .unitPrice(15000L)
                .reference("REF-001")
                .commodityCode("12345678")
                .unitOfMeasure("night")
                .totalAmount(30000L)
                .taxRate(2000L)
                .taxAmount(6000L)
                .discountAmount(1000L)
                .wxpayGoodsId("WX-GOODS-001")
                .imageUrl("https://example.com/room.jpg")
                .url("https://example.com/product/room")
                .sku("SKU-DELUXE-001")
                .serviceEndsOn(LocalDate.of(2025, 6, 30))
                .purchaseCountry(CountryCode.GB)
                .foreignRetailerAmount(500L)
                .build();

        String json = serializer.toJson(original);
        ProductRequest deserialized = serializer.fromJson(json, ProductRequest.class);

        assertNotNull(deserialized);
        assertEquals(ItemType.PHYSICAL, deserialized.getType());
        assertEquals(ItemSubType.BLOCKCHAIN, deserialized.getSubType());
        assertEquals("Hotel Room — Deluxe", deserialized.getName());
        assertEquals(2L, deserialized.getQuantity());
        assertEquals(15000L, deserialized.getUnitPrice());
        assertEquals("REF-001", deserialized.getReference());
        assertEquals("12345678", deserialized.getCommodityCode());
        assertEquals("night", deserialized.getUnitOfMeasure());
        assertEquals(30000L, deserialized.getTotalAmount());
        assertEquals(2000L, deserialized.getTaxRate());
        assertEquals(6000L, deserialized.getTaxAmount());
        assertEquals(1000L, deserialized.getDiscountAmount());
        assertEquals("WX-GOODS-001", deserialized.getWxpayGoodsId());
        assertEquals("https://example.com/room.jpg", deserialized.getImageUrl());
        assertEquals("https://example.com/product/room", deserialized.getUrl());
        assertEquals("SKU-DELUXE-001", deserialized.getSku());
        assertEquals(LocalDate.of(2025, 6, 30), deserialized.getServiceEndsOn());
        assertEquals(CountryCode.GB, deserialized.getPurchaseCountry());
        assertEquals(500L, deserialized.getForeignRetailerAmount());
    }

    @Test
    void productRequest_serviceEndsOn_shouldSerializeAsDateOnly() {
        ProductRequest request = ProductRequest.builder()
                .name("Subscription")
                .serviceEndsOn(LocalDate.of(2025, 1, 1))
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"service_ends_on\":\"2025-01-01\""),
                "Expected yyyy-MM-dd, got: " + json);
        assertFalse(json.contains("T00:00:00Z"), "Must not contain ISO-8601 timestamp: " + json);
    }

    @Test
    void productRequest_serviceEndsOn_shouldDeserializeFromSwaggerExample() {
        String json = "{\"name\":\"Item\",\"quantity\":1,\"unit_price\":1000,\"service_ends_on\":\"2025-01-01\"}";

        ProductRequest request = serializer.fromJson(json, ProductRequest.class);

        assertNotNull(request);
        assertEquals(LocalDate.of(2025, 1, 1), request.getServiceEndsOn());
        assertEquals("Item", request.getName());
    }

    @Test
    void productRequest_withoutServiceEndsOn_shouldNotFail() {
        ProductRequest request = ProductRequest.builder().name("Item").quantity(1L).build();
        String json = serializer.toJson(request);
        ProductRequest deserialized = serializer.fromJson(json, ProductRequest.class);
        assertNull(deserialized.getServiceEndsOn());
    }

    // ── ProductResponse — ALL properties ─────────────────────────────────────

    @Test
    void productResponse_allProperties_roundTrip() {
        ProductResponse original = ProductResponse.builder()
                .name("Annual License")
                .quantity(1L)
                .unitPrice(10000L)
                .reference("RESP-REF-001")
                .commodityCode("99998888")
                .unitOfMeasure("license")
                .totalAmount(10000L)
                .taxRate(1000L)
                .taxAmount(1000L)
                .discountAmount(500L)
                .wxpayGoodsId("WX-RESP-001")
                .imageUrl("https://example.com/license.png")
                .url("https://example.com/product/license")
                .sku("SKU-ANNUAL-001")
                .serviceEndsOn(LocalDate.of(2025, 12, 31))
                .build();

        String json = serializer.toJson(original);
        ProductResponse deserialized = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(deserialized);
        assertEquals("Annual License", deserialized.getName());
        assertEquals(1L, deserialized.getQuantity());
        assertEquals(10000L, deserialized.getUnitPrice());
        assertEquals("RESP-REF-001", deserialized.getReference());
        assertEquals("99998888", deserialized.getCommodityCode());
        assertEquals("license", deserialized.getUnitOfMeasure());
        assertEquals(10000L, deserialized.getTotalAmount());
        assertEquals(1000L, deserialized.getTaxRate());
        assertEquals(1000L, deserialized.getTaxAmount());
        assertEquals(500L, deserialized.getDiscountAmount());
        assertEquals("WX-RESP-001", deserialized.getWxpayGoodsId());
        assertEquals("https://example.com/license.png", deserialized.getImageUrl());
        assertEquals("https://example.com/product/license", deserialized.getUrl());
        assertEquals("SKU-ANNUAL-001", deserialized.getSku());
        assertEquals(LocalDate.of(2025, 12, 31), deserialized.getServiceEndsOn());
    }

    @Test
    void productResponse_serviceEndsOn_shouldSerializeAsDateOnly() {
        ProductResponse response = ProductResponse.builder()
                .name("Service")
                .serviceEndsOn(LocalDate.of(2025, 12, 31))
                .build();

        String json = serializer.toJson(response);

        assertTrue(json.contains("\"service_ends_on\":\"2025-12-31\""),
                "Expected yyyy-MM-dd, got: " + json);
        assertFalse(json.contains("T00:00:00Z"), "Must not contain ISO-8601 timestamp: " + json);
    }

    @Test
    void productResponse_serviceEndsOn_shouldDeserializeFromSwaggerExample() {
        String json = "{\"name\":\"Service\",\"quantity\":1,\"service_ends_on\":\"2025-01-01\"}";

        ProductResponse response = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(response);
        assertEquals(LocalDate.of(2025, 1, 1), response.getServiceEndsOn());
    }

    // ── InstrumentData — ALL properties ──────────────────────────────────────

    @Test
    void instrumentData_allProperties_roundTrip() {
        InstrumentData original = InstrumentData.builder()
                .accoountNumber("DE89370400440532013000")
                .country(CountryCode.DE)
                .currency(Currency.EUR)
                .paymentType(PaymentType.REGULAR)
                .mandateId("MANDATE-XYZ-999")
                .dateOfSignature(LocalDate.of(2021, 3, 15))
                .build();

        String json = serializer.toJson(original);
        InstrumentData deserialized = serializer.fromJson(json, InstrumentData.class);

        assertNotNull(deserialized);
        assertEquals("DE89370400440532013000", deserialized.getAccoountNumber());
        assertEquals(CountryCode.DE, deserialized.getCountry());
        assertEquals(Currency.EUR, deserialized.getCurrency());
        assertEquals(PaymentType.REGULAR, deserialized.getPaymentType());
        assertEquals("MANDATE-XYZ-999", deserialized.getMandateId());
        assertEquals(LocalDate.of(2021, 3, 15), deserialized.getDateOfSignature());
    }

    @Test
    void instrumentData_dateOfSignature_shouldSerializeAsDateOnly() {
        InstrumentData data = InstrumentData.builder()
                .mandateId("MND-001")
                .dateOfSignature(LocalDate.of(2021, 1, 1))
                .build();

        String json = serializer.toJson(data);

        assertTrue(json.contains("\"date_of_signature\":\"2021-01-01\""),
                "Expected yyyy-MM-dd, got: " + json);
        assertFalse(json.contains("T00:00:00Z"), "Must not contain ISO-8601 timestamp: " + json);
    }

    @Test
    void instrumentData_dateOfSignature_shouldDeserializeFromSwaggerExample() {
        String json = "{\"mandate_id\":\"abc123\",\"date_of_signature\":\"2021-01-01\"}";

        InstrumentData data = serializer.fromJson(json, InstrumentData.class);

        assertNotNull(data);
        assertEquals(LocalDate.of(2021, 1, 1), data.getDateOfSignature());
        assertEquals("abc123", data.getMandateId());
    }

    // ── MerchantAccount — ALL properties ─────────────────────────────────────

    @Test
    void merchantAccount_allProperties_roundTrip() {
        MerchantAccount original = MerchantAccount.builder()
                .id("ACC-FULL-001")
                .registrationDate(LocalDate.of(2023, 5, 1))
                .lastModified(LocalDate.of(2023, 11, 20))
                .returningCustomer(true)
                .firstTransactionDate(LocalDate.of(2023, 9, 15))
                .lastTransactionDate(LocalDate.of(2025, 3, 28))
                .totalOrderCount(42)
                .lastPaymentAmount(75000L)
                .build();

        String json = serializer.toJson(original);
        MerchantAccount deserialized = serializer.fromJson(json, MerchantAccount.class);

        assertNotNull(deserialized);
        assertEquals("ACC-FULL-001", deserialized.getId());
        assertEquals(LocalDate.of(2023, 5, 1), deserialized.getRegistrationDate());
        assertEquals(LocalDate.of(2023, 11, 20), deserialized.getLastModified());
        assertEquals(true, deserialized.getReturningCustomer());
        assertEquals(LocalDate.of(2023, 9, 15), deserialized.getFirstTransactionDate());
        assertEquals(LocalDate.of(2025, 3, 28), deserialized.getLastTransactionDate());
        assertEquals(42, deserialized.getTotalOrderCount());
        assertEquals(75000L, deserialized.getLastPaymentAmount());
    }

    @Test
    void merchantAccount_allDateFields_shouldSerializeAsDateOnly() {
        MerchantAccount account = MerchantAccount.builder()
                .registrationDate(LocalDate.of(2023, 5, 1))
                .lastModified(LocalDate.of(2023, 5, 1))
                .firstTransactionDate(LocalDate.of(2023, 9, 15))
                .lastTransactionDate(LocalDate.of(2025, 3, 28))
                .build();

        String json = serializer.toJson(account);

        assertTrue(json.contains("\"registration_date\":\"2023-05-01\""), "registration_date: " + json);
        assertTrue(json.contains("\"last_modified\":\"2023-05-01\""), "last_modified: " + json);
        assertTrue(json.contains("\"first_transaction_date\":\"2023-09-15\""), "first_transaction_date: " + json);
        assertTrue(json.contains("\"last_transaction_date\":\"2025-03-28\""), "last_transaction_date: " + json);
        assertFalse(json.contains("T00:00:00Z"), "Must not contain ISO-8601 timestamp: " + json);
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
        assertEquals(10, account.getTotalOrderCount());
        assertEquals(20000L, account.getLastPaymentAmount());
    }

    // ── OrderSubMerchant — ALL properties ────────────────────────────────────

    @Test
    void orderSubMerchant_allProperties_roundTrip() {
        OrderSubMerchant original = OrderSubMerchant.builder()
                .id("SUB-FULL-001")
                .productCategory("Electronics & Gadgets")
                .numberOfTrades(250)
                .registrationDate(LocalDate.of(2021, 8, 22))
                .build();

        String json = serializer.toJson(original);
        OrderSubMerchant deserialized = serializer.fromJson(json, OrderSubMerchant.class);

        assertNotNull(deserialized);
        assertEquals("SUB-FULL-001", deserialized.getId());
        assertEquals("Electronics & Gadgets", deserialized.getProductCategory());
        assertEquals(250, deserialized.getNumberOfTrades());
        assertEquals(LocalDate.of(2021, 8, 22), deserialized.getRegistrationDate());
    }

    @Test
    void orderSubMerchant_registrationDate_shouldSerializeAsDateOnly() {
        OrderSubMerchant subMerchant = OrderSubMerchant.builder()
                .id("SUB-001")
                .registrationDate(LocalDate.of(2023, 1, 15))
                .build();

        String json = serializer.toJson(subMerchant);

        assertTrue(json.contains("\"registration_date\":\"2023-01-15\""),
                "Expected yyyy-MM-dd, got: " + json);
        assertFalse(json.contains("T00:00:00Z"), "Must not contain ISO-8601 timestamp: " + json);
    }

    @Test
    void orderSubMerchant_registrationDate_shouldDeserializeFromSwaggerExample() {
        String json = "{\"id\":\"SUB-123\",\"product_category\":\"Fashion\",\"number_of_trades\":50,\"registration_date\":\"2023-01-15\"}";

        OrderSubMerchant subMerchant = serializer.fromJson(json, OrderSubMerchant.class);

        assertNotNull(subMerchant);
        assertEquals("SUB-123", subMerchant.getId());
        assertEquals("Fashion", subMerchant.getProductCategory());
        assertEquals(50, subMerchant.getNumberOfTrades());
        assertEquals(LocalDate.of(2023, 1, 15), subMerchant.getRegistrationDate());
    }
}
