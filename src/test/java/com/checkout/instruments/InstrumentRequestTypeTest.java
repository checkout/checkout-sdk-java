package com.checkout.instruments;

import com.checkout.GsonSerializer;
import com.checkout.common.InstrumentType;
import com.checkout.instruments.create.CreateInstrumentAchRequest;
import com.checkout.instruments.create.CreateInstrumentBacsRequest;
import com.checkout.instruments.create.CreateInstrumentBankAccountRequest;
import com.checkout.instruments.create.CreateInstrumentSepaRequest;
import com.checkout.instruments.create.CreateInstrumentTokenRequest;
import com.checkout.instruments.update.UpdateInstrumentAchRequest;
import com.checkout.instruments.update.UpdateInstrumentBacsRequest;
import com.checkout.instruments.update.UpdateInstrumentBankAccountRequest;
import com.checkout.instruments.update.UpdateInstrumentCardRequest;
import com.checkout.instruments.update.UpdateInstrumentSepaRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the type discriminator every instrument request sends.
 *
 * <p>The type selects the schema the API validates the request against, so a wrong value makes the
 * whole request fail regardless of its other properties. UpdateInstrumentBankAccountRequest sent
 * "token" until this test was added, and no test covered that class at all.
 */
class InstrumentRequestTypeTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSendTheCorrectTypeOnEveryCreateRequest() {
        assertType("bank_account", new CreateInstrumentBankAccountRequest());
        assertType("token", new CreateInstrumentTokenRequest());
        assertType("sepa", new CreateInstrumentSepaRequest());
        assertType("ach", new CreateInstrumentAchRequest());
        assertType("bacs", new CreateInstrumentBacsRequest());
    }

    @Test
    void shouldSendTheCorrectTypeOnEveryUpdateRequest() {
        assertType("bank_account", new UpdateInstrumentBankAccountRequest());
        assertType("sepa", new UpdateInstrumentSepaRequest());
        assertType("ach", new UpdateInstrumentAchRequest());
        assertType("bacs", new UpdateInstrumentBacsRequest());
    }

    @Test
    void shouldSendCardOnTheCardUpdateRequest() {
        final UpdateInstrumentCardRequest request = UpdateInstrumentCardRequest.builder().build();

        assertEquals(InstrumentType.CARD, request.getType());
        assertTrue(serializer.toJson(request).contains("\"type\":\"card\""));
    }

    private void assertType(final String expectedWireValue, final Object request) {
        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"type\":\"" + expectedWireValue + "\""),
                "expected type " + expectedWireValue + " but serialized " + json);
    }
}
