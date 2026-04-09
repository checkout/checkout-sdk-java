package com.checkout.metadata;

import com.checkout.GsonSerializer;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.SchemeLocalType;
import com.checkout.metadata.card.AccountFundingTransaction;
import com.checkout.metadata.card.AftIndicator;
import com.checkout.metadata.card.CardMetadataFormatType;
import com.checkout.metadata.card.CardMetadataPayouts;
import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;
import com.checkout.metadata.card.CardMetadataSourceType;
import com.checkout.metadata.card.PayoutsTransactionsType;
import com.checkout.metadata.card.PinlessDebitSchemeMetadata;
import com.checkout.metadata.card.PullFunds;
import com.checkout.metadata.card.SchemeMetadata;
import com.checkout.metadata.card.source.CardMetadataBinSource;
import com.checkout.metadata.card.source.CardMetadataCardSource;
import com.checkout.metadata.card.source.CardMetadataIdSource;
import com.checkout.metadata.card.source.CardMetadataTokenSource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for CardMetadataRequest and CardMetadataResponse.
 * Validates serialization/deserialization correctness for all fields.
 */
class CardMetadataSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ─── CardMetadataRequest ────────────────────────────────────────────────

    @Test
    void shouldSerializeRequestWithBinSource() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder().bin("454347").build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"bin\""));
        assertTrue(json.contains("\"bin\":\"454347\""));
        assertTrue(json.contains("\"format\":\"basic\""));
    }

    @Test
    void shouldSerializeRequestWithCardSource() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder().number("4543474002249996").build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"card\""));
        assertTrue(json.contains("\"number\":\"4543474002249996\""));
        assertTrue(json.contains("\"format\":\"card_payouts\""));
    }

    @Test
    void shouldSerializeRequestWithTokenSource() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder().token("tok_ubfj2q76miwundwlk72vxt2i7q").build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"token\""));
        assertTrue(json.contains("\"token\":\"tok_ubfj2q76miwundwlk72vxt2i7q\""));
    }

    @Test
    void shouldSerializeRequestWithIdSource() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataIdSource.builder().id("src_wmlfc3zyhqzehihu7giusaaawu").build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"id\""));
        assertTrue(json.contains("\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\""));
    }

    @Test
    void shouldSerializeRequestWithReferenceField() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder().bin("454347").build())
                .format(CardMetadataFormatType.BASIC)
                .reference("ORD-5023-4E89")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"reference\":\"ORD-5023-4E89\""));
    }

    @Test
    void shouldRoundTripRequest() {
        final CardMetadataRequest original = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder().number("4543474002249996").build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .reference("ORD-5023-4E89")
                .build();

        final String json = serializer.toJson(original);
        final CardMetadataRequest deserialized = serializer.fromJson(json, CardMetadataRequest.class);

        assertNotNull(deserialized);
        assertEquals(CardMetadataFormatType.CARD_PAYOUTS, deserialized.getFormat());
        assertEquals("ORD-5023-4E89", deserialized.getReference());
    }

    @Test
    void shouldDeserializeRequestFromSwaggerCardExample() {
        final String json = "{\"source\":{\"type\":\"card\",\"number\":\"4539467987109256\"},\"format\":\"basic\"}";

        final CardMetadataRequest request = serializer.fromJson(json, CardMetadataRequest.class);

        assertNotNull(request);
        assertEquals(CardMetadataFormatType.BASIC, request.getFormat());
        assertNotNull(request.getSource());
        assertEquals(CardMetadataSourceType.CARD, request.getSource().getType());
    }

    @Test
    void shouldDeserializeRequestFromSwaggerBinExample() {
        final String json = "{\"source\":{\"type\":\"bin\",\"bin\":\"424242\"},\"format\":\"card_payouts\"}";

        final CardMetadataRequest request = serializer.fromJson(json, CardMetadataRequest.class);

        assertNotNull(request);
        assertEquals(CardMetadataFormatType.CARD_PAYOUTS, request.getFormat());
    }

    // ─── CardMetadataResponse ───────────────────────────────────────────────

    @Test
    void shouldDeserializeBasicResponseFromSwaggerExample() {
        final String json = "{"
                + "\"bin\":\"45434720\","
                + "\"scheme\":\"visa\","
                + "\"local_schemes\":[\"cartes_bancaires\"],"
                + "\"card_type\":\"CREDIT\","
                + "\"card_category\":\"CONSUMER\","
                + "\"currency\":\"MUR\","
                + "\"issuer\":\"STATE BANK OF MAURITIUS\","
                + "\"issuer_country\":\"MU\","
                + "\"issuer_country_name\":\"Mauritius\","
                + "\"is_combo_card\":false,"
                + "\"product_id\":\"F\","
                + "\"product_type\":\"Visa Classic\","
                + "\"subproduct_id\":\"VA\","
                + "\"regulated_indicator\":false,"
                + "\"is_reloadable_prepaid\":false,"
                + "\"anonymous_prepaid_description\":\"Anonymous prepaid program and not AMLD5 compliant\""
                + "}";

        final CardMetadataResponse response = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(response);
        assertEquals("45434720", response.getBin());
        assertEquals("visa", response.getScheme());
        assertNotNull(response.getLocalSchemes());
        assertEquals(1, response.getLocalSchemes().size());
        assertEquals(SchemeLocalType.CARTES_BANCAIRES, response.getLocalSchemes().get(0));
        assertEquals(CardType.CREDIT, response.getCardType());
        assertEquals(CardCategory.CONSUMER, response.getCardCategory());
        assertEquals(Currency.MUR, response.getCurrency());
        assertEquals("STATE BANK OF MAURITIUS", response.getIssuer());
        assertEquals(CountryCode.MU, response.getIssuerCountry());
        assertEquals("Mauritius", response.getIssuerCountryName());
        assertFalse(response.getIsComboCard());
        assertEquals("F", response.getProductId());
        assertEquals("Visa Classic", response.getProductType());
        assertEquals("VA", response.getSubproductId());
        assertFalse(response.getRegulatedIndicator());
        assertFalse(response.getIsReloadablePrepaid());
        assertEquals("Anonymous prepaid program and not AMLD5 compliant", response.getAnonymousPrepaidDescription());
    }

    @Test
    void shouldDeserializeCardPayoutsResponseFromSwaggerExample() {
        final String json = "{"
                + "\"bin\":\"45434720\","
                + "\"scheme\":\"visa\","
                + "\"card_payouts\":{"
                + "\"domestic_non_money_transfer\":\"not_supported\","
                + "\"cross_border_non_money_transfer\":\"standard\","
                + "\"domestic_gambling\":\"fast_funds\","
                + "\"cross_border_gambling\":\"unknown\","
                + "\"domestic_money_transfer\":\"unknown\","
                + "\"cross_border_money_transfer\":\"not_supported\""
                + "}"
                + "}";

        final CardMetadataResponse response = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(response);
        assertNotNull(response.getPayouts());
        assertEquals(PayoutsTransactionsType.NOT_SUPPORTED, response.getPayouts().getDomesticNonMoneyTransfer());
        assertEquals(PayoutsTransactionsType.STANDARD, response.getPayouts().getCrossBorderNonMoneyTransfer());
        assertEquals(PayoutsTransactionsType.FAST_FUNDS, response.getPayouts().getDomesticGambling());
        assertEquals(PayoutsTransactionsType.UNKNOWN, response.getPayouts().getCrossBorderGambling());
        assertEquals(PayoutsTransactionsType.UNKNOWN, response.getPayouts().getDomesticMoneyTransfer());
        assertEquals(PayoutsTransactionsType.NOT_SUPPORTED, response.getPayouts().getCrossBorderMoneyTransfer());
    }

    @Test
    void shouldDeserializeSchemeMetadataAndAftFields() {
        final String json = "{"
                + "\"bin\":\"45434720\","
                + "\"scheme\":\"visa\","
                + "\"regulated_type\":\"base_regulated\","
                + "\"scheme_metadata\":{"
                + "  \"accel\":[{\"network_id\":\"aam\",\"network_description\":\"Accel\","
                + "    \"bill_pay_indicator\":true,\"ecommerce_indicator\":true,"
                + "    \"interchange_fee_indicator\":\"00\",\"money_transfer_indicator\":true,"
                + "    \"token_indicator\":false}],"
                + "  \"shazam\":[{\"network_id\":\"shz\",\"network_description\":\"Shazam\","
                + "    \"bill_pay_indicator\":false,\"ecommerce_indicator\":true,"
                + "    \"interchange_fee_indicator\":\"01\",\"money_transfer_indicator\":false,"
                + "    \"token_indicator\":true}]"
                + "},"
                + "\"account_funding_transaction\":{"
                + "  \"aft_indicator\":{"
                + "    \"pull_funds\":{\"cross_border\":true,\"domestic\":true}"
                + "  }"
                + "}"
                + "}";

        final CardMetadataResponse response = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(response);
        assertEquals("base_regulated", response.getRegulatedType());

        final SchemeMetadata schemeMetadata = response.getSchemeMetadata();
        assertNotNull(schemeMetadata);
        assertNotNull(schemeMetadata.getAccel());
        assertEquals(1, schemeMetadata.getAccel().size());
        assertEquals("aam", schemeMetadata.getAccel().get(0).getNetworkId());
        assertEquals("Accel", schemeMetadata.getAccel().get(0).getNetworkDescription());
        assertTrue(schemeMetadata.getAccel().get(0).getBillPayIndicator());
        assertTrue(schemeMetadata.getAccel().get(0).getEcommerceIndicator());
        assertEquals("00", schemeMetadata.getAccel().get(0).getInterchangeFeeIndicator());
        assertTrue(schemeMetadata.getAccel().get(0).getMoneyTransferIndicator());
        assertFalse(schemeMetadata.getAccel().get(0).getTokenIndicator());

        assertNotNull(schemeMetadata.getShazam());
        assertEquals(1, schemeMetadata.getShazam().size());
        assertEquals("shz", schemeMetadata.getShazam().get(0).getNetworkId());

        final AccountFundingTransaction aft = response.getAccountFundingTransaction();
        assertNotNull(aft);
        assertNotNull(aft.getAftIndicator());
        assertNotNull(aft.getAftIndicator().getPullFunds());
        assertTrue(aft.getAftIndicator().getPullFunds().getCrossBorder());
        assertTrue(aft.getAftIndicator().getPullFunds().getDomestic());
    }

    @Test
    void shouldDeserializeDeprecatedSchemeLocalField() {
        final String json = "{\"bin\":\"45434720\",\"scheme\":\"visa\",\"scheme_local\":\"cartes_bancaires\"}";

        final CardMetadataResponse response = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(response);
        assertEquals(SchemeLocalType.CARTES_BANCAIRES, response.getSchemeLocal());
    }

    @Test
    void shouldDeserializeNewSchemeLocalValues() {
        final String json = "{\"bin\":\"45434720\",\"scheme\":\"visa\","
                + "\"local_schemes\":[\"shazam\",\"paypak\",\"maestro\"]}";

        final CardMetadataResponse response = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(response);
        assertNotNull(response.getLocalSchemes());
        assertEquals(3, response.getLocalSchemes().size());
        assertTrue(response.getLocalSchemes().contains(SchemeLocalType.SHAZAM));
        assertTrue(response.getLocalSchemes().contains(SchemeLocalType.PAYPAK));
        assertTrue(response.getLocalSchemes().contains(SchemeLocalType.MAESTRO));
    }

    @Test
    void shouldHandleResponseWithNullOptionalFields() {
        final String json = "{\"bin\":\"45434720\",\"scheme\":\"visa\"}";

        final CardMetadataResponse response = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(response);
        assertEquals("45434720", response.getBin());
        assertEquals("visa", response.getScheme());
        assertNull(response.getLocalSchemes());
        assertNull(response.getCardType());
        assertNull(response.getPayouts());
        assertNull(response.getSchemeMetadata());
        assertNull(response.getAccountFundingTransaction());
        assertNull(response.getIsComboCard());
        assertNull(response.getIsReloadablePrepaid());
        assertNull(response.getAnonymousPrepaidDescription());
    }

    @Test
    void shouldRoundTripFullyPopulatedResponse() {
        final PinlessDebitSchemeMetadata pinlessData = new PinlessDebitSchemeMetadata(
                "aam", "Accel Money Transfer Advantage", true, true, "00", true, false);

        final SchemeMetadata schemeMetadata = new SchemeMetadata();
        schemeMetadata.setAccel(Arrays.asList(pinlessData));
        schemeMetadata.setShazam(Arrays.asList(new PinlessDebitSchemeMetadata(
                "shz", "Shazam", false, true, "01", false, true)));

        final PullFunds pullFunds = new PullFunds();
        pullFunds.setCrossBorder(true);
        pullFunds.setDomestic(false);

        final AftIndicator aftIndicator = new AftIndicator();
        aftIndicator.setPullFunds(pullFunds);

        final AccountFundingTransaction aft = new AccountFundingTransaction();
        aft.setAftIndicator(aftIndicator);

        final CardMetadataPayouts payouts = new CardMetadataPayouts();
        payouts.setDomesticNonMoneyTransfer(PayoutsTransactionsType.NOT_SUPPORTED);
        payouts.setCrossBorderNonMoneyTransfer(PayoutsTransactionsType.STANDARD);
        payouts.setDomesticGambling(PayoutsTransactionsType.FAST_FUNDS);
        payouts.setCrossBorderGambling(PayoutsTransactionsType.UNKNOWN);
        payouts.setDomesticMoneyTransfer(PayoutsTransactionsType.UNKNOWN);
        payouts.setCrossBorderMoneyTransfer(PayoutsTransactionsType.NOT_SUPPORTED);

        final CardMetadataResponse original = new CardMetadataResponse();
        original.setBin("45434720");
        original.setScheme("visa");
        original.setLocalSchemes(Arrays.asList(SchemeLocalType.CARTES_BANCAIRES, SchemeLocalType.SHAZAM));
        original.setCardType(CardType.CREDIT);
        original.setCardCategory(CardCategory.CONSUMER);
        original.setCurrency(Currency.MUR);
        original.setIssuer("STATE BANK OF MAURITIUS");
        original.setIssuerCountry(CountryCode.MU);
        original.setIssuerCountryName("Mauritius");
        original.setIsComboCard(false);
        original.setProductId("F");
        original.setProductType("Visa Classic");
        original.setSubproductId("VA");
        original.setRegulatedIndicator(false);
        original.setRegulatedType("base_regulated");
        original.setIsReloadablePrepaid(false);
        original.setAnonymousPrepaidDescription("Anonymous prepaid program and not AMLD5 compliant");
        original.setPayouts(payouts);
        original.setSchemeMetadata(schemeMetadata);
        original.setAccountFundingTransaction(aft);

        final String json = serializer.toJson(original);
        final CardMetadataResponse deserialized = serializer.fromJson(json, CardMetadataResponse.class);

        assertNotNull(deserialized);
        assertEquals(original.getBin(), deserialized.getBin());
        assertEquals(original.getScheme(), deserialized.getScheme());
        assertEquals(2, deserialized.getLocalSchemes().size());
        assertEquals(CardType.CREDIT, deserialized.getCardType());
        assertEquals(CardCategory.CONSUMER, deserialized.getCardCategory());
        assertEquals(Currency.MUR, deserialized.getCurrency());
        assertEquals("STATE BANK OF MAURITIUS", deserialized.getIssuer());
        assertEquals(CountryCode.MU, deserialized.getIssuerCountry());
        assertEquals("Mauritius", deserialized.getIssuerCountryName());
        assertFalse(deserialized.getIsComboCard());
        assertEquals("F", deserialized.getProductId());
        assertEquals("Visa Classic", deserialized.getProductType());
        assertEquals("VA", deserialized.getSubproductId());
        assertFalse(deserialized.getRegulatedIndicator());
        assertEquals("base_regulated", deserialized.getRegulatedType());
        assertFalse(deserialized.getIsReloadablePrepaid());
        assertEquals("Anonymous prepaid program and not AMLD5 compliant", deserialized.getAnonymousPrepaidDescription());

        assertNotNull(deserialized.getPayouts());
        assertEquals(PayoutsTransactionsType.NOT_SUPPORTED, deserialized.getPayouts().getDomesticNonMoneyTransfer());
        assertEquals(PayoutsTransactionsType.STANDARD, deserialized.getPayouts().getCrossBorderNonMoneyTransfer());
        assertEquals(PayoutsTransactionsType.FAST_FUNDS, deserialized.getPayouts().getDomesticGambling());
        assertEquals(PayoutsTransactionsType.UNKNOWN, deserialized.getPayouts().getCrossBorderGambling());
        assertEquals(PayoutsTransactionsType.UNKNOWN, deserialized.getPayouts().getDomesticMoneyTransfer());
        assertEquals(PayoutsTransactionsType.NOT_SUPPORTED, deserialized.getPayouts().getCrossBorderMoneyTransfer());

        assertNotNull(deserialized.getSchemeMetadata());
        assertNotNull(deserialized.getSchemeMetadata().getAccel());
        assertEquals("aam", deserialized.getSchemeMetadata().getAccel().get(0).getNetworkId());
        assertNotNull(deserialized.getSchemeMetadata().getShazam());

        assertNotNull(deserialized.getAccountFundingTransaction());
        assertNotNull(deserialized.getAccountFundingTransaction().getAftIndicator());
        assertTrue(deserialized.getAccountFundingTransaction().getAftIndicator().getPullFunds().getCrossBorder());
        assertFalse(deserialized.getAccountFundingTransaction().getAftIndicator().getPullFunds().getDomestic());
    }
}
