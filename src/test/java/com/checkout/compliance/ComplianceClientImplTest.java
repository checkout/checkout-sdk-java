package com.checkout.compliance;

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
public class ComplianceClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private ComplianceClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new ComplianceClientImpl(apiClient, configuration);
    }

    // Async

    @Test
    void shouldGetComplianceRequest() throws ExecutionException, InterruptedException {
        final ComplianceRequestDetails response = mock(ComplianceRequestDetails.class);

        when(apiClient.getAsync(eq("compliance-requests/pay_123"), eq(authorization),
                eq(ComplianceRequestDetails.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final ComplianceRequestDetails actual = client.getComplianceRequest("pay_123").get();

        assertNotNull(actual);
        assertEquals(response, actual);
    }

    @Test
    void shouldRespondToComplianceRequest() throws ExecutionException, InterruptedException {
        final ComplianceRespondRequest request = mock(ComplianceRespondRequest.class);
        final ComplianceRequestDetails response = mock(ComplianceRequestDetails.class);

        when(apiClient.postAsync(eq("compliance-requests/pay_123"), eq(authorization),
                eq(ComplianceRequestDetails.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final ComplianceRequestDetails actual = client.respondToComplianceRequest("pay_123", request).get();

        assertNotNull(actual);
        assertEquals(response, actual);
    }

    // Sync

    @Test
    void shouldGetComplianceRequestSync() {
        final ComplianceRequestDetails response = mock(ComplianceRequestDetails.class);

        when(apiClient.get(eq("compliance-requests/pay_123"), eq(authorization),
                eq(ComplianceRequestDetails.class)))
                .thenReturn(response);

        final ComplianceRequestDetails actual = client.getComplianceRequestSync("pay_123");

        assertNotNull(actual);
        assertEquals(response, actual);
    }

    @Test
    void shouldRespondToComplianceRequestSync() {
        final ComplianceRespondRequest request = mock(ComplianceRespondRequest.class);
        final ComplianceRequestDetails response = mock(ComplianceRequestDetails.class);

        when(apiClient.post(eq("compliance-requests/pay_123"), eq(authorization),
                eq(ComplianceRequestDetails.class), eq(request), isNull()))
                .thenReturn(response);

        final ComplianceRequestDetails actual = client.respondToComplianceRequestSync("pay_123", request);

        assertNotNull(actual);
        assertEquals(response, actual);
    }
}
