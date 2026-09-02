package com.checkout.payments.request.source.apm;

import com.checkout.GsonSerializer;
import com.checkout.common.AccountType;
import com.checkout.common.CountryCode;
import com.checkout.instruments.update.AchInstrumentAccountType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the ACH payment source account type.
 *
 * <p>PaymentRequestAchSource is the only schema declaring savings / checking / cash.
 * RequestAchSource previously typed this field as com.checkout.common.AccountType, which declares
 * "current" instead of "checking", so a valid account type could not be sent and an invalid one
 * was offered.
 */
class AchSourceAccountTypeTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeEachAccountTypeToItsWireValue() {
        assertTrue(serializer.toJson(sourceWith(AchSourceAccountType.SAVINGS))
                .contains("\"account_type\":\"savings\""));
        assertTrue(serializer.toJson(sourceWith(AchSourceAccountType.CHECKING))
                .contains("\"account_type\":\"checking\""));
        assertTrue(serializer.toJson(sourceWith(AchSourceAccountType.CASH))
                .contains("\"account_type\":\"cash\""));
    }

    @Test
    void shouldDeclareExactlyTheThreeValuesTheSchemaDeclares() {
        assertEquals(3, AchSourceAccountType.values().length);

        final String names = Arrays.stream(AchSourceAccountType.values())
                .map(Enum::name)
                .collect(Collectors.joining(","));

        assertEquals("SAVINGS,CHECKING,CASH", names);
    }

    @Test
    void shouldDifferFromTheSharedAndInstrumentAccountTypes() {
        final String shared = Arrays.stream(AccountType.values())
                .map(Enum::name).collect(Collectors.joining(","));
        final String instrument = Arrays.stream(AchInstrumentAccountType.values())
                .map(Enum::name).collect(Collectors.joining(","));

        // The shared enum offers CURRENT, which this position rejects, and cannot express
        // CHECKING. If these are ever unified, this test fails.
        assertTrue(shared.contains("CURRENT"));
        assertFalse(shared.contains("CHECKING"));
        assertFalse(instrument.contains("CASH"));
    }

    @Test
    void shouldRoundTripAnAchSourceWithCheckingAccountType() {
        final RequestAchSource original = RequestAchSource.builder()
                .accountType(AchSourceAccountType.CHECKING)
                .country(CountryCode.US)
                .accountNumber("136549956")
                .bankCode("021000021")
                .build();

        final String json = serializer.toJson(original);

        assertTrue(json.contains("\"type\":\"ach\""));
        assertTrue(json.contains("\"account_type\":\"checking\""));
        assertTrue(json.contains("\"account_number\":\"136549956\""));
        assertTrue(json.contains("\"bank_code\":\"021000021\""));

        final RequestAchSource result = serializer.fromJson(json, RequestAchSource.class);

        assertEquals(AchSourceAccountType.CHECKING, result.getAccountType());
        assertEquals("136549956", result.getAccountNumber());
    }

    private RequestAchSource sourceWith(final AchSourceAccountType accountType) {
        return RequestAchSource.builder()
                .accountType(accountType)
                .country(CountryCode.US)
                .accountNumber("136549956")
                .bankCode("021000021")
                .build();
    }
}
