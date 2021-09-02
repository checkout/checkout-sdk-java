package com.checkout.tokens.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
import com.checkout.tokens.TokenHeader;
import com.checkout.tokens.beta.request.ApplePayTokenData;
import com.checkout.tokens.beta.request.ApplePayTokenRequest;
import com.checkout.tokens.beta.request.CardTokenRequest;
import com.checkout.tokens.beta.request.GooglePayTokenData;
import com.checkout.tokens.beta.request.GooglePayTokenRequest;
import com.checkout.tokens.beta.request.WalletTokenRequest;
import com.checkout.tokens.beta.response.CardTokenResponse;
import com.checkout.tokens.beta.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.checkout.TestHelper.mockFourConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class TokensClientTest {

    @Mock
    private ApiClient apiClient;

    private CheckoutConfiguration checkoutConfiguration;

    private TokensClient tokensClient;

    @BeforeEach
    public void setup() {
        this.checkoutConfiguration = mockFourConfiguration();
        this.tokensClient = new TokensClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    public void shouldThrowExceptionIfApiClientIsNull() {

        try {
            new TokensClientImpl(null, checkoutConfiguration);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("apiClient cannot be null", checkoutArgumentException.getMessage());
        }

    }

    @Test
    public void shouldThrowException_whenRequestIsNull_cardToken() {

        try {
            tokensClient.requestAsync((CardTokenRequest) null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("cardTokenRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    public void shouldRequestCardToken() {

        final CardTokenRequest cardTokenRequest = CardTokenRequest.builder().number("123").expiryMonth(3).expiryYear(2024).build();

        tokensClient.requestAsync(cardTokenRequest);

        verify(apiClient).postAsync(eq("tokens"), any(PublicKeyCredentials.class), eq(CardTokenResponse.class), eq(cardTokenRequest), isNull());

    }

    @Test
    public void shouldThrowException_whenRequestIsNull_walletToken() {

        try {
            tokensClient.requestAsync((WalletTokenRequest) null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("walletTokenRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    public void shouldRequestApplePayToken() {

        final String signature = "MIAGCSqGSIb3DQEHAqCAMIACAQExDzANBglghkgBZQMEAgEFADCABgkqhkiG9w0BBwEAAKCAMIID5j" +
                "CCA4ugAwIBAgIIaGD2mdnMpw8wCgYIKoZIzj0EAwIwejEuMCwGA1UEAwwlQXBwbGUgQXBwbGljYXRpb24gS" +
                "W50ZWdyYXRpb24gQ0EgLSBHMzEmMCQGA1UECwwdQXBwbGUgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkxEzAR" +
                "BgNVBAoMCkFwcGxlIEluYy4xCzAJBgNVBAYTAlVTMB4XDTE2MDYwMzE4MTY0MFoXDTIxMDYwMjE4MTY0MFo" +
                "wYjEoMCYGA1UEAwwfZWNjLXNtcC1icm9rZXItc2lnbl9VQzQtU0FOREJPWDEUMBIGA1UECwwLaU9TIFN5c3" +
                "RlbXMxEzARBgNVBAoMCkFwcGxlIEluYy4xCzAJBgNVBAYTAlVTMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQ" +
                "gAEgjD9q8Oc914gLFDZm0US5jfiqQHdbLPgsc1LUmeY";

        final TokenHeader tokenHeader = TokenHeader.builder()
                .ephemeralPublicKey("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEX1ievoT8DRB8T5zGkhHZHeDr0oBmYEgsDSxyT0MD0IZ2Mpfjz2LdWq6LUwSH9EmxdPEzMunsZKWMyOr3K")
                .publicKeyHash("tqYV+tmG9aMh+l/K6cicUnPqkb1gUiLjSTM9gEz6Nl0=")
                .transactionId("3cee89679130a4b2617c76118a1c62fd400cd45b49dc0916d5b951b560cd17b4")
                .build();

        final ApplePayTokenData applePayTokenData = ApplePayTokenData.builder()
                .version("EC_v1")
                .data("t7GeajLB9skXB6QSWfEpPA4WPhDqB7ekdd")
                .tokenHeader(tokenHeader)
                .signature(signature)
                .build();

        final ApplePayTokenRequest applePayTokenRequest = ApplePayTokenRequest.builder()
                .applePayTokenData(applePayTokenData)
                .build();

        tokensClient.requestAsync(applePayTokenRequest);

        verify(apiClient).postAsync(eq("tokens"), any(PublicKeyCredentials.class), eq(TokenResponse.class), eq(applePayTokenRequest), isNull());

    }

    @Test
    public void shouldRequestGooglePayToken() {

        final GooglePayTokenData googlePayTokenData = GooglePayTokenData.builder()
                .signature("TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ")
                .protocolVersion("ECv1")
                .signedMessage("Signed Message")
                .build();

        final GooglePayTokenRequest googlePayTokenRequest = GooglePayTokenRequest.builder()
                .googlePayTokenData(googlePayTokenData)
                .build();

        tokensClient.requestAsync(googlePayTokenRequest);

        verify(apiClient).postAsync(eq("tokens"), any(PublicKeyCredentials.class), eq(TokenResponse.class), eq(googlePayTokenRequest), isNull());

    }

}