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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokensClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private TokensClient tokensClient;

    @BeforeEach
    void setup() {
        this.tokensClient = new TokensClientImpl(apiClient, configuration);
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
            tokensClient.request((CardTokenRequest) null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("cardTokenRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRequestCardToken() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final CardTokenRequest cardTokenRequest = CardTokenRequest.builder().number("123").expiryMonth(3).expiryYear(2024).build();

        tokensClient.request(cardTokenRequest);

        verify(apiClient).postAsync(eq("tokens"), eq(authorization), eq(CardTokenResponse.class), eq(cardTokenRequest), isNull());

    }

    @Test
    void shouldThrowException_whenRequestIsNull_walletToken() {

        try {

            tokensClient.request((WalletTokenRequest) null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("walletTokenRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRequestApplePayToken() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

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

        final ApplePayTokenRequest applePayTokenRequest = ApplePayTokenRequest.builder()
                .applePayTokenData(applePayTokenData)
                .build();

        tokensClient.request(applePayTokenRequest);

        verify(apiClient).postAsync(eq("tokens"), eq(authorization), eq(TokenResponse.class), eq(applePayTokenRequest), isNull());

    }

    @Test
    void shouldRequestGooglePayToken() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final GooglePayTokenData googlePayTokenData = GooglePayTokenData.builder()
                .signature("TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ")
                .protocolVersion("ECv1")
                .signedMessage("Signed Message")
                .build();

        final GooglePayTokenRequest googlePayTokenRequest = GooglePayTokenRequest.builder()
                .googlePayTokenData(googlePayTokenData)
                .build();

        tokensClient.request(googlePayTokenRequest);

        verify(apiClient).postAsync(eq("tokens"), eq(authorization), eq(TokenResponse.class), eq(googlePayTokenRequest), isNull());

    }

}