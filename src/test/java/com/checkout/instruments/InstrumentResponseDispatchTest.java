package com.checkout.instruments;

import com.checkout.GsonSerializer;
import com.checkout.instruments.create.CreateInstrumentAchResponse;
import com.checkout.instruments.create.CreateInstrumentBacsResponse;
import com.checkout.instruments.create.CreateInstrumentResponse;
import com.checkout.instruments.get.GetAchInstrumentResponse;
import com.checkout.instruments.get.GetBacsInstrumentResponse;
import com.checkout.instruments.get.GetInstrumentResponse;
import com.checkout.instruments.update.UpdateInstrumentAchResponse;
import com.checkout.instruments.update.UpdateInstrumentBacsResponse;
import com.checkout.instruments.update.UpdateInstrumentResponse;
import com.checkout.instruments.update.UpdateInstrumentSepaResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Polymorphic dispatch tests for the Bacs Direct Debit, ACH and SEPA instrument responses.
 *
 * <p>Before these subtypes were registered on the three instrument factories, every assertion here
 * failed with a JsonParseException, because none of the factories declares a default subtype. ACH
 * was already registered on the update factory but not on create or get.
 *
 * <p>The update assertions also guard the id, which the specification declares on the sepa, ach and
 * bacs update responses. It was once declared on the bacs variant only, and was silently dropped on
 * the other two.
 */
class InstrumentResponseDispatchTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDispatchCreateResponseToBacsSubtype() {
        final String json = "{\"type\":\"bacs\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\"}";

        final CreateInstrumentResponse response = serializer.fromJson(json, CreateInstrumentResponse.class);

        final CreateInstrumentBacsResponse bacs = assertInstanceOf(CreateInstrumentBacsResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.BACS, bacs.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", bacs.getId());
    }

    @Test
    void shouldDispatchUpdateResponseToBacsSubtype() {
        final String json = "{\"type\":\"bacs\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\"}";

        final UpdateInstrumentResponse response = serializer.fromJson(json, UpdateInstrumentResponse.class);

        final UpdateInstrumentBacsResponse bacs = assertInstanceOf(UpdateInstrumentBacsResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.BACS, bacs.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", bacs.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", bacs.getFingerprint());
    }

    @Test
    void shouldDispatchUpdateResponseToSepaSubtype() {
        final String json = "{\"type\":\"sepa\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\"}";

        final UpdateInstrumentResponse response = serializer.fromJson(json, UpdateInstrumentResponse.class);

        final UpdateInstrumentSepaResponse sepa = assertInstanceOf(UpdateInstrumentSepaResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.SEPA, sepa.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", sepa.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", sepa.getFingerprint());
    }

    @Test
    void shouldDispatchUpdateResponseToAchSubtype() {
        final String json = "{\"type\":\"ach\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\"}";

        final UpdateInstrumentResponse response = serializer.fromJson(json, UpdateInstrumentResponse.class);

        final UpdateInstrumentAchResponse ach = assertInstanceOf(UpdateInstrumentAchResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.ACH, ach.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", ach.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", ach.getFingerprint());
    }

    @Test
    void shouldDispatchCreateResponseToAchSubtype() {
        final String json = "{\"type\":\"ach\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\"}";

        final CreateInstrumentResponse response = serializer.fromJson(json, CreateInstrumentResponse.class);

        final CreateInstrumentAchResponse ach = assertInstanceOf(CreateInstrumentAchResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.ACH, ach.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", ach.getId());
    }

    @Test
    void shouldDispatchGetResponseToAchSubtype() {
        final String json = "{\"type\":\"ach\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\","
                + "\"created_on\":\"2021-01-01T00:00:00Z\","
                + "\"vault_id\":\"vid_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"instrument_data\":{\"account_type\":\"savings\",\"account_number\":\"4099999992\","
                + "\"bank_code\":\"211370545\",\"currency\":\"USD\",\"country\":\"US\"}}";

        final GetInstrumentResponse response = serializer.fromJson(json, GetInstrumentResponse.class);

        final GetAchInstrumentResponse ach = assertInstanceOf(GetAchInstrumentResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.ACH, ach.getType());
        assertEquals(com.checkout.instruments.update.AchInstrumentAccountType.SAVINGS,
                ach.getInstrumentData().getAccountType());
    }

    @Test
    void shouldDispatchGetResponseToBacsSubtype() {
        final String json = "{\"type\":\"bacs\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\","
                + "\"created_on\":\"2021-01-01T00:00:00Z\","
                + "\"vault_id\":\"vid_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"instrument_data\":{\"account_number\":\"86753246\",\"bank_code\":\"040004\","
                + "\"country\":\"GB\",\"currency\":\"GBP\",\"payment_type\":\"Recurring\"}}";

        final GetInstrumentResponse response = serializer.fromJson(json, GetInstrumentResponse.class);

        final GetBacsInstrumentResponse bacs = assertInstanceOf(GetBacsInstrumentResponse.class, response);
        assertEquals(com.checkout.common.InstrumentType.BACS, bacs.getType());
        assertEquals(BacsPaymentType.RECURRING, bacs.getInstrumentData().getPaymentType());
    }
}
