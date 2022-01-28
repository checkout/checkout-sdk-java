package com.checkout.sessions;

import com.checkout.CardSourceHelper;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.MarketplaceData;
import com.checkout.common.Phone;
import com.checkout.sessions.channel.AppSession;
import com.checkout.sessions.channel.BrowserSession;
import com.checkout.sessions.channel.ChannelData;
import com.checkout.sessions.channel.SdkEphemeralPublicKey;
import com.checkout.sessions.channel.SdkInterfaceType;
import com.checkout.sessions.channel.ThreeDsMethodCompletion;
import com.checkout.sessions.completion.HostedCompletionInfo;
import com.checkout.sessions.completion.NonHostedCompletionInfo;
import com.checkout.sessions.source.SessionCardSource;

import java.util.Arrays;

abstract class AbstractSessionsTestIT extends SandboxTestFixture {

    public AbstractSessionsTestIT() {
        super(PlatformType.FOUR_OAUTH);
    }

    protected SessionResponse createNonHostedSession(final ChannelData channelData,
                                                     final Category authenticationCategory,
                                                     final ChallengeIndicator challengeIndicator,
                                                     final TransactionType transactionType) {

        final SessionRequest sessionRequest = SessionRequest.builder()
                .source(SessionCardSource.builder()
                        .email(TestHelper.generateRandomEmail())
                        .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                        .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                        .number(CardSourceHelper.Visa.NUMBER)
                        .name("John Doe")
                        .billingAddress(SessionAddress.builderSessionAddress()
                                .addressLine1("Address Line 1")
                                .addressLine2("Address Line 2")
                                .addressLine3("Address Line 3")
                                .city("City")
                                .country(CountryCode.GB)
                                .build())
                        .homePhone(Phone.builder().number("0204567895").countryCode("234").build())
                        .workPhone(Phone.builder().number("0204567895").countryCode("234").build())
                        .mobilePhone(Phone.builder().number("0204567895").countryCode("234").build())
                        .build())
                .amount(6540L)
                .currency(Currency.USD)
                .processingChannelId("pc_5jp2az55l3cuths25t5p3xhwru")
                .marketplace(MarketplaceData.builder().subEntityId("ent_ocw5i74vowfg2edpy66izhts2u").build())
                .authenticationType(AuthenticationType.REGULAR)
                .authenticationCategory(authenticationCategory)
                .challengeIndicator(challengeIndicator)
                .billingDescriptor(SessionsBillingDescriptor.builder().name("SUPERHEROES.COM").build())
                .reference("ORD-5023-4E89")
                .transactionType(transactionType)
                .shippingAddress(SessionAddress.builderSessionAddress()
                        .addressLine1("Checkout.com")
                        .addressLine2("ABC building")
                        .addressLine3("14 Wells Mews")
                        .city("London")
                        .country(CountryCode.GB)
                        .state("ENG")
                        .zip("W1T 4TJ")
                        .build())
                .completion(NonHostedCompletionInfo.builder()
                        .callbackUrl("https://merchant.com/callback")
                        .build()
                )
                .channelData(channelData)
                .build();

        return blocking(() -> fourApi.sessionsClient().requestSession(sessionRequest));

    }

    protected SessionResponse createHostedSession() {

        final SessionRequest sessionRequest = SessionRequest.builder()
                .source(SessionCardSource.builder()
                        .expiryMonth(1)
                        .expiryYear(2030)
                        .number("4485040371536584")
                        .build())
                .amount(100L)
                .currency(Currency.USD)
                .processingChannelId("pc_5jp2az55l3cuths25t5p3xhwru")
                .authenticationType(AuthenticationType.REGULAR)
                .authenticationCategory(Category.PAYMENT)
                .challengeIndicator(ChallengeIndicator.NO_PREFERENCE)
                .reference("ORD-5023-4E89")
                .transactionType(TransactionType.GOODS_SERVICE)
                .shippingAddress(SessionAddress.builderSessionAddress()
                        .addressLine1("Checkout.com")
                        .addressLine2("90 Tottenham Court Road")
                        .city("London")
                        .state("ENG")
                        .country(CountryCode.GB)
                        .zip("W1T 4TJ")
                        .build())
                .completion(HostedCompletionInfo.builder()
                        .successUrl("http://example.com/sessions/success")
                        .failureUrl("http://example.com/sessions/fail")
                        .build())
                .build();

        return blocking(() -> fourApi.sessionsClient().requestSession(sessionRequest));

    }

    protected static ChannelData browserSession() {
        return BrowserSession.builder()
                .acceptHeader("Accept:  *.*, q=0.1")
                .javaEnabled(true)
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

}
