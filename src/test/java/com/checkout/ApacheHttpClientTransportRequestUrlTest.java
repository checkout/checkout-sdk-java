package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApacheHttpClientTransportRequestUrlTest {

    private ApacheHttpClientTransport transport;

    @BeforeEach
    void setUp() {
        transport = new ApacheHttpClientTransport(
                URI.create("https://api.sandbox.checkout.com/"),
                HttpClientBuilder.create(),
                Executors.newSingleThreadExecutor(),
                new DefaultTransportConfiguration(),
                mock(CheckoutConfiguration.class));
    }

    private String requestUrl(final String path) throws Throwable {
        final Method method = ApacheHttpClientTransport.class.getDeclaredMethod("getRequestUrl", String.class);
        method.setAccessible(true);
        try {
            return (String) method.invoke(transport, path);
        } catch (final InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    void shouldResolveEncodedPathsUnchanged() throws Throwable {
        assertEquals("https://api.sandbox.checkout.com/payments/pay_1", requestUrl("payments/pay_1"));
        assertEquals("https://api.sandbox.checkout.com/payments/pay_1%2Frefunds", requestUrl("payments/pay_1%2Frefunds"));
        assertEquals("https://api.sandbox.checkout.com/event-types?version=v2", requestUrl("event-types?version=v2"));
        assertEquals("https://api.sandbox.checkout.com/ideal-external/issuers", requestUrl("ideal-external/issuers"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "payments/../workflows/wf_1",
            "payments/./pay_1",
            "payments/..",
            "payments//refunds",
            "payments/pay_1/",
            "/payments/pay_1",
            "//example.com/payments",
            "payments/pay_1#refunds",
            "payments/pay_1/..?x=1"
    })
    void shouldRefuseUnsafeRequestPaths(final String path) {
        assertThrows(CheckoutArgumentException.class, () -> requestUrl(path));
    }

}
