package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.checkout.GsonSerializer;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.CardSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.currencyaccountsource.CurrencyAccountSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.klarnasource.KlarnaSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.paypalsource.PaypalSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.sepasource.SepaSource;

public final  class RequestAPaymentOrPayoutResponseCreatedSerializationTest {

  private final GsonSerializer serializer = new GsonSerializer();

  @Test
    void shouldDeserializeCardSource() {
        String json = "{\n" +
                "  \"id\": \"pay_123\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"approved\": true,\n" +
                "  \"status\": \"Authorized\",\n" +
                "  \"response_code\": \"10000\",\n" +
                "  \"processed_on\": \"2021-06-08T12:25:01Z\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"card\",\n" +
                "    \"id\": \"src_123\",\n" +
                "    \"last4\": \"1234\",\n" +
                "    \"fingerprint\": \"abc123\",\n" +
                "    \"bin\": \"123456\",\n" +
                "    \"scheme\": \"Visa\",\n" +
                "    \"card_type\": \"Credit\",\n" +
                "    \"expiry_month\": 12,\n" +
                "    \"expiry_year\": 2025\n" +
                "  }\n" +
                "}";

        RequestAPaymentOrPayoutResponseCreated response = serializer.fromJson(json, RequestAPaymentOrPayoutResponseCreated.class);

        assertNotNull(response);
        assertNotNull(response.getSource());
        assertInstanceOf(CardSource.class, response.getSource());
        CardSource cardSource = (CardSource) response.getSource();
        assertEquals("1234", cardSource.getLast4());
        assertEquals("abc123", cardSource.getFingerprint());
        assertEquals("123456", cardSource.getBin());
        assertEquals(12, cardSource.getExpiryMonth());
        assertEquals(2025, cardSource.getExpiryYear());
    }

    @Test
    void shouldDeserializeKlarnaSource() {
        String json = "{\n" +
                "  \"id\": \"pay_123\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"currency\": \"EUR\",\n" +
                "  \"approved\": true,\n" +
                "  \"status\": \"Authorized\",\n" +
                "  \"response_code\": \"10000\",\n" +
                "  \"processed_on\": \"2021-06-08T12:25:01Z\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"klarna\"\n" +
                "  }\n" +
                "}";

        RequestAPaymentOrPayoutResponseCreated response = serializer.fromJson(json, RequestAPaymentOrPayoutResponseCreated.class);

        assertNotNull(response);
        assertNotNull(response.getSource());
        assertInstanceOf(KlarnaSource.class, response.getSource());
    }

    @Test
    void shouldDeserializePaypalSource() {
        String json = "{\n" +
                "  \"id\": \"pay_123\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"approved\": true,\n" +
                "  \"status\": \"Authorized\",\n" +
                "  \"response_code\": \"10000\",\n" +
                "  \"processed_on\": \"2021-06-08T12:25:01Z\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"paypal\"\n" +
                "  }\n" +
                "}";

        RequestAPaymentOrPayoutResponseCreated response = serializer.fromJson(json, RequestAPaymentOrPayoutResponseCreated.class);

        assertNotNull(response);
        assertNotNull(response.getSource());
        assertInstanceOf(PaypalSource.class, response.getSource());
    }

    @Test
    void shouldDeserializeCurrencyAccountSource() {
        String json = "{\n" +
                "  \"id\": \"pay_123\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"approved\": true,\n" +
                "  \"status\": \"Authorized\",\n" +
                "  \"response_code\": \"10000\",\n" +
                "  \"processed_on\": \"2021-06-08T12:25:01Z\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"currency_account\",\n" +
                "    \"id\": \"ca_123\",\n" +
                "    \"amount\": 500\n" +
                "  }\n" +
                "}";

        RequestAPaymentOrPayoutResponseCreated response = serializer.fromJson(json, RequestAPaymentOrPayoutResponseCreated.class);

        assertNotNull(response);
        assertNotNull(response.getSource());
        assertInstanceOf(CurrencyAccountSource.class, response.getSource());
        CurrencyAccountSource currencyAccountSource = (CurrencyAccountSource) response.getSource();
        assertEquals("ca_123", currencyAccountSource.getId());
        assertEquals(Integer.valueOf(500), currencyAccountSource.getAmount());
    }

    @Test
    void shouldDeserializeSepaSource() {
        String json = "{\n" +
                "  \"id\": \"pay_123\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"currency\": \"EUR\",\n" +
                "  \"approved\": true,\n" +
                "  \"status\": \"Authorized\",\n" +
                "  \"response_code\": \"10000\",\n" +
                "  \"processed_on\": \"2021-06-08T12:25:01Z\",\n" +
                "  \"source\": {\n" +
                "    \"type\": \"sepa\",\n" +
                "    \"id\": \"src_sepa_123\"\n" +
                "  }\n" +
                "}";

        RequestAPaymentOrPayoutResponseCreated response = serializer.fromJson(json, RequestAPaymentOrPayoutResponseCreated.class);

        assertNotNull(response);
        assertNotNull(response.getSource());
        assertInstanceOf(SepaSource.class, response.getSource());
        SepaSource sepaSource = (SepaSource) response.getSource();
        assertEquals("src_sepa_123", sepaSource.getId());
    }

}
