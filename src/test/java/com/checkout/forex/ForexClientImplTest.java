package com.checkout.forex;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForexClientImplTest {

    private ForexClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        client = new ForexClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestQuote() throws ExecutionException, InterruptedException {

        setupMockCredentials();

        final QuoteRequest request = createQuoteRequest();
        final QuoteResponse response = createQuoteResponse();

        when(apiClient.postAsync(eq("forex/quotes"), eq(authorization), eq(QuoteResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<QuoteResponse> future = client.requestQuote(request);

        validateQuoteResponse(future.get(), response);
    }

    @Test
    void shouldGetRates() throws ExecutionException, InterruptedException {

        setupMockCredentials();

        final RatesQueryFilter request = createRatesQueryFilter();
        final RatesQueryResponse response = createRatesQueryResponse();

        when(apiClient.queryAsync(eq("forex/rates"), eq(authorization), eq(request),
                eq(RatesQueryResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RatesQueryResponse> future = client.getRates(request);

        validateRatesQueryResponse(future.get(), response);
    }

    // Synchronous method tests
    @Test
    void shouldRequestQuoteSync() {

        setupMockCredentials();

        final QuoteRequest request = createQuoteRequest();
        final QuoteResponse response = createQuoteResponse();

        when(apiClient.post(eq("forex/quotes"), eq(authorization), eq(QuoteResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final QuoteResponse result = client.requestQuoteSync(request);

        validateQuoteResponse(result, response);
    }

    @Test
    void shouldGetRatesSync() {

        setupMockCredentials();

        final RatesQueryFilter request = createRatesQueryFilter();
        final RatesQueryResponse response = createRatesQueryResponse();

        when(apiClient.query(eq("forex/rates"), eq(authorization), eq(request),
                eq(RatesQueryResponse.class)))
                .thenReturn(response);

        final RatesQueryResponse result = client.getRatesSync(request);

        validateRatesQueryResponse(result, response);
    }

    // Common methods
    private void setupMockCredentials() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
    }

    private QuoteRequest createQuoteRequest() {
        return mock(QuoteRequest.class);
    }

    private QuoteResponse createQuoteResponse() {
        return mock(QuoteResponse.class);
    }

    private RatesQueryFilter createRatesQueryFilter() {
        return RatesQueryFilter.builder()
                .product("card_payouts")
                .source(ForexSource.VISA)
                .currencyPairs("GBPEUR,USDNOK,JPNCAD")
                .processChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();
    }

    private RatesQueryResponse createRatesQueryResponse() {
        return mock(RatesQueryResponse.class);
    }

    private void validateQuoteResponse(QuoteResponse actual, QuoteResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateRatesQueryResponse(RatesQueryResponse actual, RatesQueryResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}