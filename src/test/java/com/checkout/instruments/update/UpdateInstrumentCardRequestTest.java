package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the constructor surface of UpdateInstrumentCardRequest.
 *
 * <p>It previously exposed a constructor taking an InstrumentType that it discarded, hardcoding
 * CARD. A caller passing SEPA silently received a card request. The parameter is gone; these tests
 * stop it coming back.
 */
class UpdateInstrumentCardRequestTest {

    @Test
    void shouldAlwaysSetTypeToCard() {
        assertEquals(InstrumentType.CARD, new UpdateInstrumentCardRequest().getType());
        assertEquals(InstrumentType.CARD, UpdateInstrumentCardRequest.builder().build().getType());
    }

    @Test
    void shouldNotExposeAConstructorTakingAnInstrumentType() {
        final boolean takesInstrumentType = Arrays.stream(UpdateInstrumentCardRequest.class.getDeclaredConstructors())
                .anyMatch(c -> Arrays.asList(c.getParameterTypes()).contains(InstrumentType.class));

        // A type parameter cannot be honoured here: the class is final and its type is always CARD.
        assertFalse(takesInstrumentType);
    }

    @Test
    void shouldExposeAPublicNoArgConstructorLikeItsSiblings() {
        final boolean hasPublicNoArg = Arrays.stream(UpdateInstrumentCardRequest.class.getDeclaredConstructors())
                .anyMatch(c -> c.getParameterCount() == 0 && java.lang.reflect.Modifier.isPublic(c.getModifiers()));

        assertTrue(hasPublicNoArg);
    }

    @Test
    void siblingsShouldAgreeOnTheNoArgShape() {
        // UpdateInstrumentBacsRequest, ...TokenRequest and the rest all take no arguments.
        for (final Class<?> sibling : Arrays.asList(UpdateInstrumentBacsRequest.class,
                UpdateInstrumentTokenRequest.class,
                UpdateInstrumentCardRequest.class)) {
            final boolean hasNoArg = Arrays.stream(sibling.getDeclaredConstructors())
                    .anyMatch(c -> c.getParameterCount() == 0);
            assertTrue(hasNoArg, sibling.getSimpleName() + " should expose a no-arg constructor");
        }
    }

    @Test
    void theNoArgConstructorShouldBeTheOnlyNonBuilderConstructor() {
        final long nonBuilderPublic = Arrays.stream(UpdateInstrumentCardRequest.class.getDeclaredConstructors())
                .filter(c -> java.lang.reflect.Modifier.isPublic(c.getModifiers()))
                .count();

        // The Lombok @Builder constructor is private, so only the no-arg one is public.
        assertEquals(1, nonBuilderPublic);
        final Constructor<?> only = Arrays.stream(UpdateInstrumentCardRequest.class.getDeclaredConstructors())
                .filter(c -> java.lang.reflect.Modifier.isPublic(c.getModifiers()))
                .findFirst()
                .orElseThrow(AssertionError::new);
        assertEquals(0, only.getParameterCount());
    }
}
