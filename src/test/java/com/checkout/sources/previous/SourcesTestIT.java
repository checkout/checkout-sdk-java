package com.checkout.sources.previous;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SourcesTestIT extends SandboxTestFixture {

    SourcesTestIT() {
        super(PlatformType.PREVIOUS);
    }

    @Test
    void shouldCreateSepaSource() throws Exception {
        final SepaSourceRequest sourceRequest = createSepaSourceRequest();
        final SepaSourceResponse sourceResponse = previousApi.sourcesClient().createSepaSource(sourceRequest).get();

        assertNotNull(sourceResponse);
        assertFalse(StringUtils.isEmpty(sourceResponse.getId()));
        assertEquals("10000", sourceResponse.getResponseCode());
        assertNotNull(sourceResponse.getResponseData());
        assertEquals(SourceType.SEPA, sourceResponse.getType());
        assertTrue(sourceResponse.getResponseData().containsKey("mandate_reference"));
    }

    private SepaSourceRequest createSepaSourceRequest() {
        final Address billingAddress = new Address();
        billingAddress.setAddressLine1("CheckoutSdk.com");
        billingAddress.setAddressLine2("90 Tottenham Court Road");
        billingAddress.setCity("London");
        billingAddress.setState("London");
        billingAddress.setZip("W1T 4TJ");
        billingAddress.setCountry(CountryCode.GB);

        final Phone phone = new Phone();
        phone.setCountryCode("+1");
        phone.setNumber("415 555 2671");

        final SourceData sourceData = new SourceData();
        sourceData.put("first_name", "Marcus");
        sourceData.put("last_name", "Barrilius Maximus");
        sourceData.put("account_iban", "DE68100100101234567895");
        sourceData.put("bic", "PBNKDEFFXXX");
        sourceData.put("billing_descriptor", "Java SDK test");
        sourceData.put("mandate_type", "single");

        final SepaSourceRequest request = SepaSourceRequest.builder()
                .phone(phone)
                .billingAddress(billingAddress)
                .sourceData(sourceData)
                .reference("Java SDK tes")
                .build();

        return request;
    }
}