package com.checkout.instruments;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentResponse;
import com.checkout.instruments.create.CreateInstrumentTokenResponse;
import com.checkout.instruments.get.BankAccountField;
import com.checkout.instruments.get.InstrumentCustomerResponse;
import com.checkout.payments.request.source.apm.RequestSwishAccountHolder;
import com.checkout.payments.request.source.apm.RequestSwishBillingDescriptor;
import com.checkout.payments.request.source.apm.RequestSwishSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstrumentSchemaRegressionTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldOmitUnsetInstrumentCustomerDefaultsAndBankAccountFieldValues() {
        final String customerJson = serializer.toJson(CreateCustomerInstrumentRequest.builder().build());
        final BankAccountField field = serializer.fromJson("{\"id\":\"iban\",\"display\":\"IBAN\",\"type\":\"string\"}", BankAccountField.class);
        final InstrumentCustomerResponse customer = serializer.fromJson("{}", InstrumentCustomerResponse.class);

        assertFalse(customerJson.contains("\"default\""));
        assertNull(field.getRequired());
        assertNull(field.getMinLength());
        assertNull(field.getMaxLength());
        assertNull(customer.isDefault());
    }

    @Test
    void shouldDeserializeAllTokenStoreResponseProperties() {
        final String json = "{\"type\":\"card\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\",\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\",\"expiry_month\":6,\"expiry_year\":2025,\"last4\":\"9996\",\"bin\":\"454347\",\"account_holder\":{\"first_name\":\"Hannah\",\"last_name\":\"Bret\"},\"network_token\":{\"id\":\"nt_y3oqhf46pyzuxjbcn2giaqnb44\",\"state\":\"active\"}}";

        final CreateInstrumentResponse response = serializer.fromJson(json, CreateInstrumentResponse.class);
        final CreateInstrumentTokenResponse token = assertInstanceOf(CreateInstrumentTokenResponse.class, response);

        assertEquals("Hannah", token.getAccountHolder().getFirstName());
        assertEquals("nt_y3oqhf46pyzuxjbcn2giaqnb44", token.getNetworkToken().getId());
    }

    @Test
    void shouldSerializeAndDeserializeOnlySwishSchemaFields() {
        final RequestSwishSource source = RequestSwishSource.builder()
                .paymentCountry(CountryCode.SE)
                .accountHolder(RequestSwishAccountHolder.builder().firstName("Bruce").lastName("Wayne").build())
                .billingDescriptor(RequestSwishBillingDescriptor.builder().name("CKO Store").build())
                .build();

        final String json = serializer.toJson(source);
        final RequestSwishSource deserialized = serializer.fromJson(json, RequestSwishSource.class);

        assertTrue(json.contains("\"payment_country\":\"SE\""));
        assertTrue(json.contains("\"account_holder\":{\"first_name\":\"Bruce\",\"last_name\":\"Wayne\"}"));
        assertTrue(json.contains("\"billing_descriptor\":{\"name\":\"CKO Store\"}"));
        assertEquals(CountryCode.SE, deserialized.getPaymentCountry());
        assertEquals("Bruce", deserialized.getAccountHolder().getFirstName());
        assertEquals("CKO Store", deserialized.getBillingDescriptor().getName());
    }
}