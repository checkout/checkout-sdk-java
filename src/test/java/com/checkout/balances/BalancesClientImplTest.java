package com.checkout.balances;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalancesClientImplTest {

    private static final String ENTITY_ID = "ent_w4jelhppmfiufdnatam37wrfc4";
    private static final String CURRENCY_ACCOUNT_ID = "ca_g5y7d6jo4e2urgforcbf2ey5jm";
    private static final String TOP_UP_INSTRUCTIONS_PATH =
            "entities/" + ENTITY_ID + "/currency-accounts/" + CURRENCY_ACCOUNT_ID + "/top-up-instructions";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private BalancesClient balancesClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.balancesClient = new BalancesClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldRetrieveEntityBalances() throws ExecutionException, InterruptedException {
        final BalancesQuery query = createBalancesQuery();
        final BalancesResponse expectedResponse = mock(BalancesResponse.class);

        when(apiClient.queryAsync(eq("balances/entity_id"), any(SdkAuthorization.class), eq(query), eq(BalancesResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<BalancesResponse> future = balancesClient.retrieveEntityBalances("entity_id", query);

        final BalancesResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldRetrieveEntityBalancesSync() {
        final BalancesQuery query = createBalancesQuery();
        final BalancesResponse expectedResponse = mock(BalancesResponse.class);

        when(apiClient.query(eq("balances/entity_id"), any(SdkAuthorization.class), eq(query), eq(BalancesResponse.class)))
                .thenReturn(expectedResponse);

        final BalancesResponse actualResponse = balancesClient.retrieveEntityBalancesSync("entity_id", query);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRetrieveTopUpInstructions() throws ExecutionException, InterruptedException {
        final TopUpInstructionsResponse expectedResponse = mock(TopUpInstructionsResponse.class);

        when(apiClient.getAsync(eq(TOP_UP_INSTRUCTIONS_PATH), any(SdkAuthorization.class), eq(TopUpInstructionsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<TopUpInstructionsResponse> future =
                balancesClient.retrieveTopUpInstructions(ENTITY_ID, CURRENCY_ACCOUNT_ID);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldRetrieveTopUpInstructionsSync() {
        final TopUpInstructionsResponse expectedResponse = mock(TopUpInstructionsResponse.class);

        when(apiClient.get(eq(TOP_UP_INSTRUCTIONS_PATH), any(SdkAuthorization.class), eq(TopUpInstructionsResponse.class)))
                .thenReturn(expectedResponse);

        final TopUpInstructionsResponse actualResponse =
                balancesClient.retrieveTopUpInstructionsSync(ENTITY_ID, CURRENCY_ACCOUNT_ID);

        validateResponse(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NULL, ca_g5y7d6jo4e2urgforcbf2ey5jm",
            "'', ca_g5y7d6jo4e2urgforcbf2ey5jm",
            "ent_w4jelhppmfiufdnatam37wrfc4, NULL",
            "ent_w4jelhppmfiufdnatam37wrfc4, ''"
    }, nullValues = "NULL")
    void shouldFailWhenTopUpInstructionsIdentifiersAreMissing(final String entityId, final String currencyAccountId) {
        assertThrows(CheckoutArgumentException.class,
                () -> balancesClient.retrieveTopUpInstructions(entityId, currencyAccountId));
        assertThrows(CheckoutArgumentException.class,
                () -> balancesClient.retrieveTopUpInstructionsSync(entityId, currencyAccountId));
    }

    // Common methods
    private BalancesQuery createBalancesQuery() {
        return BalancesQuery.builder().build();
    }

    private <T> void validateResponse(final T expectedResponse, final T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }
}