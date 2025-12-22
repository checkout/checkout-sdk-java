package com.checkout.tokens;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokensClientImplTest {

    private TokensClient client;

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new TokensClientImpl(apiClient, configuration);
    }

    @Test
    void shouldThrowExceptionIfApiClientIsNull() {
        try {
            new TokensClientImpl(null, configuration);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("apiClient cannot be null", checkoutArgumentException.getMessage());
        }
    }

    @Test
    void shouldThrowException_whenRequestIsNull_cardToken() {
        try {
            client.requestCardToken((CardTokenRequest) null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("cardTokenRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenRequestIsNull_walletToken() {
        try {
            client.requestWalletToken((WalletTokenRequest) null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("walletTokenRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRequestCardToken() throws ExecutionException, InterruptedException {
        final CardTokenRequest request = createMockCardTokenRequest();
        final CardTokenResponse expectedResponse = mock(CardTokenResponse.class);

        when(apiClient.postAsync(eq("tokens"), eq(authorization), eq(CardTokenResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CardTokenResponse> future = client.requestCardToken(request);
        final CardTokenResponse actualResponse = future.get();
        
        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestApplePayToken() throws ExecutionException, InterruptedException {
        final ApplePayTokenRequest request = createMockApplePayTokenRequest();
        final TokenResponse expectedResponse = mock(TokenResponse.class);

        when(apiClient.postAsync(eq("tokens"), eq(authorization), eq(TokenResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<TokenResponse> future = client.requestWalletToken(request);
        final TokenResponse actualResponse = future.get();
        
        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestGooglePayToken() throws ExecutionException, InterruptedException {
        final GooglePayTokenRequest request = createMockGooglePayTokenRequest();
        final TokenResponse expectedResponse = mock(TokenResponse.class);

        when(apiClient.postAsync(eq("tokens"), eq(authorization), eq(TokenResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<TokenResponse> future = client.requestWalletToken(request);
        final TokenResponse actualResponse = future.get();
        
        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldRequestCardTokenSync() {
        final CardTokenRequest request = createMockCardTokenRequest();
        final CardTokenResponse expectedResponse = mock(CardTokenResponse.class);

        when(apiClient.post(eq("tokens"), eq(authorization), eq(CardTokenResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CardTokenResponse actualResponse = client.requestCardTokenSync(request);
        
        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestApplePayTokenSync() {
        final ApplePayTokenRequest request = createMockApplePayTokenRequest();
        final TokenResponse expectedResponse = mock(TokenResponse.class);

        when(apiClient.post(eq("tokens"), eq(authorization), eq(TokenResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final TokenResponse actualResponse = client.requestWalletTokenSync(request);
        
        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestGooglePayTokenSync() {
        final GooglePayTokenRequest request = createMockGooglePayTokenRequest();
        final TokenResponse expectedResponse = mock(TokenResponse.class);

        when(apiClient.post(eq("tokens"), eq(authorization), eq(TokenResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final TokenResponse actualResponse = client.requestWalletTokenSync(request);
        
        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private CardTokenRequest createMockCardTokenRequest() {
        return CardTokenRequest.builder().number("123").expiryMonth(3).expiryYear(2030).build();
    }

    private ApplePayTokenRequest createMockApplePayTokenRequest() {
        final String signature = "MIAGCSqGSIb3DQEHAqCAMIACAQExDzANBglghkgBZQMEAgEFADCABgkqhkiG9w0BBwEAAKCAMIID5j" +
                "CCA4ugAwIBAgIIaGD2mdnMpw8wCgYIKoZIzj0EAwIwejEuMCwGA1UEAwwlQXBwbGUgQXBwbGljYXRpb24gS" +
                "W50ZWdyYXRpb24gQ0EgLSBHMzEmMCQGA1UECwwdQXBwbGUgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkxEzAR" +
                "BgNVBAoMCkFwcGxlIEluYy4xCzAJBgNVBAYTAlVTMB4XDTE2MDYwMzE4MTY0MFoXDTIxMDYwMjE4MTY0MFo" +
                "wYjEoMCYGA1UEAwwfZWNjLXNtcC1icm9rZXItc2lnbl9VQzQtU0FOREJPWDEUMBIGA1UECwwLaU9TIFN5c3" +
                "RlbXMxEzARBgNVBAoMCkFwcGxlIEluYy4xCzAJBgNVBAYTAlVTMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQ" +
                "gAEgjD9q8Oc914gLFDZm0US5jfiqQHdbLPgsc1LUmeY";

        final Map<String, String> tokenHeader = new HashMap<>();
        tokenHeader.put("ephemeralPublicKey", "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEX1ievoT8DRB8T5zGkhHZHeDr0oBmYEgsDSxyT0MD0IZ2Mpfjz2LdWq6LUwSH9EmxdPEzMunsZKWMyOr3K");
        tokenHeader.put("publicKeyHash", "tqYV+tmG9aMh+l/K6cicUnPqkb1gUiLjSTM9gEz6Nl0=");
        tokenHeader.put("transactionId", "3cee89679130a4b2617c76118a1c62fd400cd45b49dc0916d5b951b560cd17b4");

        final ApplePayTokenData applePayTokenData = ApplePayTokenData.builder()
                .version("EC_v1")
                .data("t7GeajLB9skXB6QSWfEpPA4WPhDqB7ekdd")
                .tokenHeader(tokenHeader)
                .signature(signature)
                .build();

        return ApplePayTokenRequest.builder()
                .applePayTokenData(applePayTokenData)
                .build();
    }

    private GooglePayTokenRequest createMockGooglePayTokenRequest() {
        final GooglePayTokenData googlePayTokenData = GooglePayTokenData.builder()
                .signature("TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ")
                .protocolVersion("ECv1")
                .signedMessage("Signed Message")
                .build();

        return GooglePayTokenRequest.builder()
                .googlePayTokenData(googlePayTokenData)
                .build();
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}