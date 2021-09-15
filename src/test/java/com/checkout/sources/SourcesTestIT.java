package com.checkout.sources;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SourcesTestIT extends SandboxTestFixture {

    SourcesTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void can_request_sepa_source() throws Exception {
        final SourceRequest sourceRequest = createSepaSourceRequest();
        final SourceResponse sourceResponse = defaultApi.sourcesClient().requestAsync(sourceRequest).get();

        assertNotNull(sourceResponse);
        final SourceProcessed source = sourceResponse.getSource();
        assertFalse(StringUtils.isEmpty(source.getId()));
        assertEquals("10000", source.getResponseCode());
        assertNotNull(source.getResponseData());
        assertTrue(sourceRequest.getType().equalsIgnoreCase(source.getType()));
        assertTrue(source.getResponseData().containsKey("mandate_reference"));
    }

    private SourceRequest createSepaSourceRequest() {
        final Address billingAddress = new Address();
        billingAddress.setAddressLine1("CheckoutSdk.com");
        billingAddress.setAddressLine2("90 Tottenham Court Road");
        billingAddress.setCity("London");
        billingAddress.setState("London");
        billingAddress.setZip("W1T 4TJ");
        billingAddress.setCountry("GB");

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

        final SourceRequest request = new SourceRequest("sepa", billingAddress);
        request.setPhone(phone);
        request.setReference("Java SDK test");
        request.setSourceData(sourceData);

        return request;
    }
}