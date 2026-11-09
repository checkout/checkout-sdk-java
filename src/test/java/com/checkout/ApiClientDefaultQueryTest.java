package com.checkout;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.google.gson.reflect.TypeToken;

/**
 * A custom ApiClient written before the Type overloads of query existed keeps working through the
 * default methods.
 */
class ApiClientDefaultQueryTest {

    private static final Type GENERIC_TYPE = new TypeToken<ItemsResponse<String>>() {
    }.getType();

    private final ApiClient customClient = mock(ApiClient.class, withSettings().defaultAnswer(CALLS_REAL_METHODS));
    private final SdkAuthorization authorization = new SdkAuthorization(PlatformType.DEFAULT, "sk_test");

    @Test
    void shouldDelegatePlainClassTypesToTheClassOverloads() {
        final EmptyResponse response = new EmptyResponse();
        final CompletableFuture<EmptyResponse> future = CompletableFuture.completedFuture(response);
        when(customClient.query(eq("x"), eq(authorization), any(), eq(EmptyResponse.class))).thenReturn(response);
        when(customClient.queryAsync(eq("x"), eq(authorization), any(), eq(EmptyResponse.class))).thenReturn(future);

        assertSame(response, customClient.query("x", authorization, new Object(), (Type) EmptyResponse.class));
        assertSame(future, customClient.queryAsync("x", authorization, new Object(), (Type) EmptyResponse.class));
    }

    @Test
    void shouldReportGenericTypesThatTheImplementationDoesNotSupport() {
        assertThrows(UnsupportedOperationException.class, () -> customClient.query("x", authorization, new Object(), GENERIC_TYPE));
        assertThrows(UnsupportedOperationException.class, () -> customClient.queryAsync("x", authorization, new Object(), GENERIC_TYPE));
    }

}
