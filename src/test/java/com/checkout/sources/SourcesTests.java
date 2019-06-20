package com.checkout.sources;

import com.checkout.ApiTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;
import org.junit.Assert;
import org.junit.Test;

public class SourcesTests extends ApiTestFixture {

    @Test
    public void can_request_sepa_source() throws Exception {
        SourceRequest sourceRequest = createSepaSourceRequest();
        SourceResponse sourceResponse = getApi().sourcesClient().requestAsync(sourceRequest).get();

        Assert.assertNotNull(sourceResponse);
        SourceProcessed source = sourceResponse.getSource();
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(source.getId()));
        Assert.assertEquals("10000", source.getResponseCode());
        Assert.assertNotNull(source.getResponseData());
        Assert.assertTrue(sourceRequest.getType().equalsIgnoreCase(source.getType()));
        Assert.assertTrue(source.getResponseData().containsKey("mandate_reference"));
    }

    private SourceRequest createSepaSourceRequest() {
        Address billingAddress = new Address();
        billingAddress.setAddressLine1("Checkout.com");
        billingAddress.setAddressLine2("90 Tottenham Court Road");
        billingAddress.setCity("London");
        billingAddress.setState("London");
        billingAddress.setZip("W1T 4TJ");
        billingAddress.setCountry("GB");

        Phone phone = new Phone();
        phone.setCountryCode("+1");
        phone.setNumber("415 555 2671");

        SourceData sourceData = new SourceData();
        sourceData.put("first_name", "Marcus");
        sourceData.put("last_name", "Barrilius Maximus");
        sourceData.put("account_iban", "DE68100100101234567895");
        sourceData.put("bic", "PBNKDEFFXXX");
        sourceData.put("billing_descriptor", "Java SDK test");
        sourceData.put("mandate_type", "single");

        SourceRequest request = new SourceRequest("sepa", billingAddress);
        request.setPhone(phone);
        request.setReference("Java SDK test");
        request.setSourceData(sourceData);

        return request;
    }
}