package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDevice;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.CustomerDeviceClient;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashApp;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashAppAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashAppAddress;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashAppCustomerProfile;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp.CashAppActionType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodStatus;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Wire-format tests for the Cash App Pay payment method and the customer device fields, using the
 * SDK serializer so the global naming policy is in play.
 */
class CashAppSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    private static JsonObject parse(final String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    private PaymentSetupsRequest buildRequest(final CustomerDeviceClient client, final OsType os) {
        final CashApp cashApp = new CashApp();
        cashApp.setInitialization(PaymentMethodInitialization.ENABLED);
        cashApp.setCustomerProfileSharing(true);
        return PaymentSetupsRequest.builder()
                .paymentMethods(PaymentMethods.builder().cashapp(cashApp).build())
                .customer(Customer.builder().device(CustomerDevice.builder()
                        .locale("en_GB")
                        .fingerprint("fp_abc123xyz")
                        .ipv4("203.0.113.0")
                        .ipv6("2001:db8:85a3::8a2e:370:7334")
                        .client(client)
                        .os(os)
                        .build()).build())
                .build();
    }

    @Test
    void shouldSerializeCashAppUnderSingleWordKey() {
        final String json = serializer.toJson(buildRequest(CustomerDeviceClient.MOBILE_WEB, OsType.ANDROID));
        final JsonObject methods = parse(json).getAsJsonObject("payment_methods");

        assertTrue(methods.has("cashapp"));
        assertFalse(json.contains("cash_app"));
        assertFalse(json.contains("cashApp"));
        final JsonObject cashapp = methods.getAsJsonObject("cashapp");
        assertEquals("enabled", cashapp.get("initialization").getAsString());
        assertTrue(cashapp.get("customer_profile_sharing").getAsBoolean());
        assertFalse(cashapp.has("customerProfileSharing"));
    }

    @Test
    void shouldSerializeCustomerDeviceWireKeysAndEnumValues() {
        final JsonObject device = parse(serializer.toJson(buildRequest(CustomerDeviceClient.MOBILE_WEB, OsType.ANDROID)))
                .getAsJsonObject("customer").getAsJsonObject("device");

        assertEquals("en_GB", device.get("locale").getAsString());
        assertEquals("fp_abc123xyz", device.get("fingerprint").getAsString());
        assertEquals("203.0.113.0", device.get("ipv4").getAsString());
        assertEquals("2001:db8:85a3::8a2e:370:7334", device.get("ipv6").getAsString());
        assertEquals("mobile_web", device.get("client").getAsString());
        assertEquals("android", device.get("os").getAsString());
        assertEquals(6, device.size());
    }

    @Test
    void shouldSerializeEveryDeviceClientAndOsValueExactly() {
        assertEquals("web", deviceField(CustomerDeviceClient.WEB, OsType.IOS, "client"));
        assertEquals("mobile_web", deviceField(CustomerDeviceClient.MOBILE_WEB, OsType.IOS, "client"));
        assertEquals("app", deviceField(CustomerDeviceClient.APP, OsType.IOS, "client"));
        assertEquals("android", deviceField(CustomerDeviceClient.WEB, OsType.ANDROID, "os"));
        assertEquals("ios", deviceField(CustomerDeviceClient.WEB, OsType.IOS, "os"));
    }

    private String deviceField(final CustomerDeviceClient client, final OsType os, final String field) {
        return parse(serializer.toJson(buildRequest(client, os)))
                .getAsJsonObject("customer").getAsJsonObject("device").get(field).getAsString();
    }

    @Test
    void shouldRoundTripCustomerDeviceEnums() {
        for (final CustomerDeviceClient client : CustomerDeviceClient.values()) {
            for (final OsType os : OsType.values()) {
                final CustomerDevice original = CustomerDevice.builder()
                        .fingerprint("fp").ipv4("1.2.3.4").ipv6("::1").client(client).os(os).build();
                final CustomerDevice copy = serializer.fromJson(serializer.toJson(original), CustomerDevice.class);
                assertEquals(original, copy);
            }
        }
    }

    @Test
    void shouldOmitNewDeviceFieldsWhenNotSet() {
        final String json = serializer.toJson(CustomerDevice.builder().locale("en_GB").build());

        assertEquals("{\"locale\":\"en_GB\"}", json);
    }

    @Test
    void shouldDeserializeFullCashAppResponse() {
        final String json = "{"
                + "\"id\":\"pst_cashapp\","
                + "\"customer\":{\"device\":{\"client\":\"app\",\"os\":\"ios\",\"ipv4\":\"203.0.113.0\","
                + "\"ipv6\":\"2001:db8::1\",\"fingerprint\":\"fp_1\",\"locale\":\"en_GB\"}},"
                + "\"payment_methods\":{\"cashapp\":{"
                + "\"status\":\"action_required\","
                + "\"flags\":[],"
                + "\"initialization\":\"enabled\","
                + "\"customer_profile_sharing\":true,"
                + "\"customer_profile\":{"
                + "\"customer_id\":\"CST_AYVkuLzfsRqEhf4OyQFxQNv22m7IjNFjO6f2J5CDE2nxAC4-21wJ2H8_2kvsdIsDZMN4\","
                + "\"cashtag\":\"$CASHTAG_C_TOKEN\","
                + "\"reference_id\":\"value\","
                + "\"full_name\":\"John Middle Doe\","
                + "\"given_name\":\"John\","
                + "\"middle_name\":\"Middle\","
                + "\"family_name\":\"Doe\","
                + "\"suffix\":\"Jr.\","
                + "\"birth_date\":\"1990-01-01T00:00:00.0000000\","
                + "\"address\":{"
                + "\"address_line_1\":\"123 Main St\","
                + "\"address_line_2\":\"Apt 2\","
                + "\"address_line_3\":\"Floor 3\","
                + "\"locality\":\"Springfield\","
                + "\"sublocality\":\"Downtown\","
                + "\"administrative_district_level_1\":\"IL\","
                + "\"postal_code\":\"62701\","
                + "\"country\":\"US\"},"
                + "\"phone_number\":\"5555555555\","
                + "\"email_address\":\"cash@cash.com\","
                + "\"customer_since\":\"1970-01-18T12:46:04.8000000+00:00\"},"
                + "\"reference\":\"ORDER-99\","
                + "\"action\":{\"type\":\"redirect\","
                + "\"redirect_url\":\"https://sandbox.api.cash.app/customer-request/v1/requests/GRR_f5xg6wrxhtv3p4w24g0wrexa/interstitial?validity_token=bap03y\"}"
                + "}}}";

        final PaymentSetupsResponse response = serializer.fromJson(json, PaymentSetupsResponse.class);

        assertEquals(CustomerDeviceClient.APP, response.getCustomer().getDevice().getClient());
        assertEquals(OsType.IOS, response.getCustomer().getDevice().getOs());
        assertEquals("203.0.113.0", response.getCustomer().getDevice().getIpv4());
        assertEquals("2001:db8::1", response.getCustomer().getDevice().getIpv6());
        assertEquals("fp_1", response.getCustomer().getDevice().getFingerprint());

        final CashApp cashApp = response.getPaymentMethods().getCashapp();
        assertNotNull(cashApp);
        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, cashApp.getStatus());
        assertEquals(Collections.emptyList(), cashApp.getFlags());
        assertEquals(PaymentMethodInitialization.ENABLED, cashApp.getInitialization());
        assertEquals(Boolean.TRUE, cashApp.getCustomerProfileSharing());
        assertEquals("ORDER-99", cashApp.getReference());

        assertNotNull(cashApp.getAction());
        assertEquals(CashAppActionType.REDIRECT, cashApp.getAction().getType());
        assertEquals("https://sandbox.api.cash.app/customer-request/v1/requests/GRR_f5xg6wrxhtv3p4w24g0wrexa/interstitial?validity_token=bap03y",
                cashApp.getAction().getRedirectUrl());

        assertNotNull(cashApp.getCustomerProfile());
        assertEquals("CST_AYVkuLzfsRqEhf4OyQFxQNv22m7IjNFjO6f2J5CDE2nxAC4-21wJ2H8_2kvsdIsDZMN4",
                cashApp.getCustomerProfile().getCustomerId());
        assertEquals("$CASHTAG_C_TOKEN", cashApp.getCustomerProfile().getCashtag());
        assertEquals("value", cashApp.getCustomerProfile().getReferenceId());
        assertEquals("John Middle Doe", cashApp.getCustomerProfile().getFullName());
        assertEquals("John", cashApp.getCustomerProfile().getGivenName());
        assertEquals("Middle", cashApp.getCustomerProfile().getMiddleName());
        assertEquals("Doe", cashApp.getCustomerProfile().getFamilyName());
        assertEquals("Jr.", cashApp.getCustomerProfile().getSuffix());
        assertEquals("1990-01-01T00:00:00.0000000", cashApp.getCustomerProfile().getBirthDate());
        assertEquals("5555555555", cashApp.getCustomerProfile().getPhoneNumber());
        assertEquals("cash@cash.com", cashApp.getCustomerProfile().getEmailAddress());
        assertEquals("1970-01-18T12:46:04.8000000+00:00", cashApp.getCustomerProfile().getCustomerSince());
        assertNotNull(cashApp.getCustomerProfile().getAddress());
        assertEquals("123 Main St", cashApp.getCustomerProfile().getAddress().getAddressLine1());
        assertEquals("Apt 2", cashApp.getCustomerProfile().getAddress().getAddressLine2());
        assertEquals("Floor 3", cashApp.getCustomerProfile().getAddress().getAddressLine3());
        assertEquals("Springfield", cashApp.getCustomerProfile().getAddress().getLocality());
        assertEquals("Downtown", cashApp.getCustomerProfile().getAddress().getSublocality());
        assertEquals("IL", cashApp.getCustomerProfile().getAddress().getAdministrativeDistrictLevel1());
        assertEquals("62701", cashApp.getCustomerProfile().getAddress().getPostalCode());
        assertEquals(CountryCode.US, cashApp.getCustomerProfile().getAddress().getCountry());
    }

    @Test
    void shouldDeserializeCashAppWithoutProfileOnSubsequentResponses() {
        final CashApp cashApp = serializer.fromJson("{\"status\":\"ready\",\"initialization\":\"enabled\","
                + "\"customer_profile_sharing\":true,\"reference\":\"ORDER-99\"}", CashApp.class);

        assertEquals(PaymentMethodStatus.READY, cashApp.getStatus());
        assertNull(cashApp.getCustomerProfile());
        assertNull(cashApp.getAction());
    }

    @Test
    void shouldLeaveKlarnaActionUnchangedWhenCashAppIsPresent() {
        final String json = "{\"klarna\":{\"status\":\"action_required\",\"initialization\":\"enabled\","
                + "\"action\":{\"type\":\"sdk\",\"client_token\":\"ct_1\",\"session_id\":\"sess_1\"}},"
                + "\"cashapp\":{\"initialization\":\"enabled\"}}";

        final PaymentMethods methods = serializer.fromJson(json, PaymentMethods.class);

        assertEquals("sdk", methods.getKlarna().getAction().getType());
        assertEquals("ct_1", methods.getKlarna().getAction().getClientToken());
        assertEquals("sess_1", methods.getKlarna().getAction().getSessionId());
        assertNull(methods.getKlarna().getAction().getOrderId());
        assertNotNull(methods.getCashapp());

        final Klarna roundTripped = serializer.fromJson(serializer.toJson(methods.getKlarna()), Klarna.class);
        assertEquals(methods.getKlarna(), roundTripped);
        final JsonObject action = parse(serializer.toJson(methods.getKlarna())).getAsJsonObject("action");
        assertFalse(action.has("redirect_url"));
        assertEquals("sdk", action.get("type").getAsString());
    }

    @Test
    void shouldRoundTripCashAppWithEveryProperty() {
        final CashApp original = new CashApp();
        original.setStatus(PaymentMethodStatus.ACTION_REQUIRED);
        original.setFlags(Collections.emptyList());
        original.setInitialization(PaymentMethodInitialization.ENABLED);
        original.setCustomerProfileSharing(true);
        original.setReference("ORDER-99");
        original.setAction(CashAppAction.builder()
                .type(CashAppActionType.REDIRECT)
                .redirectUrl("https://sandbox.api.cash.app/customer-request/v1/requests/GRR_f5xg6wrxhtv3p4w24g0wrexa/interstitial?validity_token=bap03y")
                .build());
        original.setCustomerProfile(CashAppCustomerProfile.builder()
                .customerId("CST_AYVkuLzfsRqEhf4OyQFxQNv22m7IjNFjO6f2J5CDE2nxAC4-21wJ2H8_2kvsdIsDZMN4")
                .cashtag("$CASHTAG_C_TOKEN")
                .referenceId("value")
                .fullName("John Middle Doe")
                .givenName("John")
                .middleName("Middle")
                .familyName("Doe")
                .suffix("Jr.")
                .birthDate("1990-01-01T00:00:00.0000000")
                .address(CashAppAddress.builder()
                        .addressLine1("123 Main St")
                        .addressLine2("Apt 2")
                        .addressLine3("Floor 3")
                        .locality("Springfield")
                        .sublocality("Downtown")
                        .administrativeDistrictLevel1("IL")
                        .postalCode("62701")
                        .country(CountryCode.US)
                        .build())
                .phoneNumber("5555555555")
                .emailAddress("cash@cash.com")
                .customerSince("1970-01-18T12:46:04.8000000+00:00")
                .build());

        final String json = serializer.toJson(original);
        final JsonObject cashapp = parse(json);
        final JsonObject profile = cashapp.getAsJsonObject("customer_profile");
        final JsonObject address = profile.getAsJsonObject("address");

        assertEquals(7, cashapp.size());
        assertEquals("redirect", cashapp.getAsJsonObject("action").get("type").getAsString());
        assertTrue(cashapp.getAsJsonObject("action").has("redirect_url"));
        assertEquals(13, profile.size());
        assertTrue(profile.has("customer_id"));
        assertTrue(profile.has("reference_id"));
        assertTrue(profile.has("customer_since"));
        assertEquals(8, address.size());
        assertEquals("123 Main St", address.get("address_line_1").getAsString());
        assertEquals("Apt 2", address.get("address_line_2").getAsString());
        assertEquals("Floor 3", address.get("address_line_3").getAsString());
        assertEquals("IL", address.get("administrative_district_level_1").getAsString());
        assertFalse(address.has("address_line1"));
        assertFalse(address.has("administrative_district_level1"));

        assertEquals(original, serializer.fromJson(json, CashApp.class));
    }

    @Test
    void shouldDeserializeCashAppInConfirmResponse() {
        final String json = "{\"id\":\"pst_cashapp\",\"payment_methods\":{\"cashapp\":{"
                + "\"status\":\"action_required\",\"reference\":\"ORDER-99\","
                + "\"action\":{\"type\":\"redirect\","
                + "\"redirect_url\":\"https://sandbox.api.cash.app/customer-request/v1/requests/GRR_f5xg6wrxhtv3p4w24g0wrexa/interstitial?validity_token=bap03y\"}}}}";

        final PaymentSetupsConfirmResponse response = serializer.fromJson(json, PaymentSetupsConfirmResponse.class);
        final CashApp cashApp = response.getPaymentMethods().getCashapp();

        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, cashApp.getStatus());
        assertEquals("ORDER-99", cashApp.getReference());
        assertEquals(CashAppActionType.REDIRECT, cashApp.getAction().getType());
        assertEquals("https://sandbox.api.cash.app/customer-request/v1/requests/GRR_f5xg6wrxhtv3p4w24g0wrexa/interstitial?validity_token=bap03y",
                cashApp.getAction().getRedirectUrl());
    }

    @Test
    void shouldDeserializeEverySpecPaymentMethodStatus() {
        assertEquals(PaymentMethodStatus.UNAVAILABLE, statusOf("unavailable"));
        assertEquals(PaymentMethodStatus.ACTION_REQUIRED, statusOf("action_required"));
        assertEquals(PaymentMethodStatus.READY, statusOf("ready"));
        assertEquals(PaymentMethodStatus.INITIALIZATION_REQUIRED, statusOf("initialization_required"));
        assertEquals(PaymentMethodStatus.INVALID, statusOf("invalid"));
    }

    private PaymentMethodStatus statusOf(final String wireValue) {
        return serializer.fromJson("{\"status\":\"" + wireValue + "\"}", CashApp.class).getStatus();
    }
}
