package com.checkout.financial;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.disputes.DisputesQueryFilter;
import com.checkout.disputes.DisputesQueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinancialClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private FinancialClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new FinancialClientImpl(apiClient, configuration);
    }

    @Test
    void shouldQueryFinancialActions() throws ExecutionException, InterruptedException {

        final FinancialActionsQueryFilter query = mock(FinancialActionsQueryFilter.class);
        final FinancialActionsQueryResponse response = mock(FinancialActionsQueryResponse.class);

        when(apiClient.queryAsync("financial-actions", authorization, query, FinancialActionsQueryResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FinancialActionsQueryResponse> future = client.query(query);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }
}
