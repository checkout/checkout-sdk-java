package com.checkout.sessions;

import com.checkout.CardSourceHelper;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.sessions.channel.AppSession;
import com.checkout.sessions.channel.BrowserSession;
import com.checkout.sessions.channel.ChannelData;
import com.checkout.sessions.channel.MerchantInitiatedSession;
import com.checkout.sessions.channel.RequestType;
import com.checkout.sessions.channel.SdkEphemeralPublicKey;
import com.checkout.sessions.channel.SdkInterfaceType;
import com.checkout.sessions.channel.ThreeDsMethodCompletion;
import com.checkout.sessions.completion.HostedCompletionInfo;
import com.checkout.sessions.completion.NonHostedCompletionInfo;
import com.checkout.sessions.source.SessionCardSource;

import java.util.Arrays;

abstract class AbstractSessionsTestIT extends SandboxTestFixture {

    public AbstractSessionsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    protected SessionResponse createNonHostedSession(final ChannelData channelData,
                                                     final Category authenticationCategory,
                                                     final ChallengeIndicator challengeIndicator,
                                                     final TransactionType transactionType) {

        final SessionRequest sessionRequest = createNonHostedSessionRequest(channelData, authenticationCategory, challengeIndicator, transactionType);
        return blocking(() -> checkoutApi.sessionsClient().requestSession(sessionRequest));
    }

    protected SessionResponse createHostedSession() {
        final SessionRequest sessionRequest = createHostedSessionRequest();
        return blocking(() -> checkoutApi.sessionsClient().requestSession(sessionRequest));
    }

    // Common methods
    protected SessionRequest createNonHostedSessionRequest(final ChannelData channelData,
                                                          final Category authenticationCategory,
                                                          final ChallengeIndicator challengeIndicator,
                                                          final TransactionType transactionType) {
        return SessionRequest.builder()
                .source(createSessionCardSource())
                .amount(6540L)
                .currency(Currency.USD)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .marketplace(createSessionMarketplaceData())
                .authenticationType(AuthenticationType.REGULAR)
                .authenticationCategory(authenticationCategory)
                .challengeIndicator(challengeIndicator)
                .billingDescriptor(createSessionsBillingDescriptor())
                .reference("ORD-5023-4E89")
                .transactionType(transactionType)
                .shippingAddress(createShippingAddress())
                .completion(createNonHostedCompletionInfo())
                .channelData(channelData)
                .build();
    }

    protected SessionRequest createHostedSessionRequest() {
        return SessionRequest.builder()
                .source(createHostedSessionCardSource())
                .amount(100L)
                .currency(Currency.USD)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .authenticationType(AuthenticationType.REGULAR)
                .authenticationCategory(Category.PAYMENT)
                .challengeIndicator(ChallengeIndicator.NO_PREFERENCE)
                .reference("ORD-5023-4E89")
                .transactionType(TransactionType.GOODS_SERVICE)
                .shippingAddress(createShippingAddress())
                .completion(createHostedCompletionInfo())
                .build();
    }

    protected SessionCardSource createSessionCardSource() {
        return SessionCardSource.builder()
                .email(TestHelper.generateRandomEmail())
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .number(CardSourceHelper.Visa.NUMBER)
                .name("John Doe")
                .billingAddress(createBillingAddress())
                .homePhone(createPhone())
                .workPhone(createPhone())
                .mobilePhone(createPhone())
                .build();
    }

    protected SessionCardSource createHostedSessionCardSource() {
        return SessionCardSource.builder()
                .expiryMonth(1)
                .expiryYear(2030)
                .number("4485040371536584")
                .build();
    }

    protected SessionAddress createBillingAddress() {
        return SessionAddress.builderSessionAddress()
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .addressLine3("Address Line 3")
                .city("City")
                .country(CountryCode.GB)
                .build();
    }

    protected SessionAddress createShippingAddress() {
        return SessionAddress.builderSessionAddress()
                .addressLine1("Checkout.com")
                .addressLine2("ABC building")
                .addressLine3("14 Wells Mews")
                .city("London")
                .country(CountryCode.GB)
                .state("ENG")
                .zip("W1T 4TJ")
                .build();
    }

    protected Phone createPhone() {
        return Phone.builder().number("0204567895").countryCode("234").build();
    }

    protected SessionMarketplaceData createSessionMarketplaceData() {
        return SessionMarketplaceData.builder().subEntityId("ent_ocw5i74vowfg2edpy66izhts2u").build();
    }

    protected SessionsBillingDescriptor createSessionsBillingDescriptor() {
        return SessionsBillingDescriptor.builder().name("SUPERHEROES.COM").build();
    }

    protected NonHostedCompletionInfo createNonHostedCompletionInfo() {
        return NonHostedCompletionInfo.builder()
                .callbackUrl("https://merchant.com/callback")
                .build();
    }

    protected HostedCompletionInfo createHostedCompletionInfo() {
        return HostedCompletionInfo.builder()
                .successUrl("http://example.com/sessions/success")
                .failureUrl("http://example.com/sessions/fail")
                .build();
    }

    protected static ChannelData browserSession() {
        return BrowserSession.builder()
                .acceptHeader("Accept:  *.*, q=0.1")
                .javaEnabled(true)
                .javascriptEnabled(true)
                .language("FR-fr")
                .colorDepth("16")
                .screenWidth("1920")
                .screenHeight("1080")
                .timezone("60")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                .threeDsMethodCompletion(ThreeDsMethodCompletion.Y)
                .ipAddress("1.12.123.255")
                .build();
    }

    protected static ChannelData appSession() {
        return AppSession.builder()
                .sdkAppId("dbd64fcb-c19a-4728-8849-e3d50bfdde39")
                .sdkMaxTimeout(5L)
                .sdkEncryptedData("{}")
                .sdkEphemeralPublicKey(SdkEphemeralPublicKey.builder()
                        .kty("EC")
                        .crv("P-256")
                        .x("f83OJ3D2xF1Bg8vub9tLe1gHMzV76e8Tus9uPHvRVEU")
                        .y("x_FEzRu9m36HLN_tue659LNpXW6pCyStikYjKIWI5a0")
                        .build())
                .sdkReferenceNumber("3DS_LOA_SDK_PPFU_020100_00007")
                .sdkTransactionId("b2385523-a66c-4907-ac3c-91848e8c0067")
                .sdkInterfaceType(SdkInterfaceType.BOTH)
                .sdkUIElements(Arrays.asList(UIElements.SINGLE_SELECT, UIElements.HTML_OTHER))
                .build();
    }

    protected static ChannelData merchantInitiatedSession() {
        return MerchantInitiatedSession.builder()
                .requestType(RequestType.RECURRING_TRANSACTION)
                .build();
    }

}
