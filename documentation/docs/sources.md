---
id: sources
title: Sources
---

export const Highlight = ({children, color}) => (
<span
style={{
      color: color,
      padding: '0.2rem',
    }}>
{children}
</span>
);

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Sources).

## Add a <Highlight color="#25c2a0">SEPA source</Highlight>

:::note

SEPA is not enabled by default, so please let your account manager know if you want to use it.

:::

```java
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
sourceData.put("billing_descriptor", "SEPA Test");
sourceData.put("mandate_type", "single");

SourceRequest sourceRequest = new SourceRequest("sepa", billingAddress);
sourceRequest.setPhone(phone);
sourceRequest.setReference("Java SDK test");
sourceRequest.setSourceData(sourceData);
SourceResponse response = api.sourcesClient().requestAsync(sourceRequest).get();

SourceProcessed source = response.getSource();
```

## Add a <Highlight color="#25c2a0">ACH source</Highlight>

:::note

ACH is not enabled by default, so please let your account manager know if you want to use it.

:::

```java
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
sourceData.put("account_type", "Checking");
sourceData.put("account_number", "0123456789");
sourceData.put("routing_number", "211370545");
sourceData.put("account_holder_name", "Bruce Wayne");
sourceData.put("billing_descriptor", "ACH Test");
sourceData.put("company_name", "Wayne Enterprises");

SourceRequest sourceRequest = new SourceRequest("ach", billingAddress);
sourceRequest.setPhone(phone);
sourceRequest.setReference("Java SDK test");
sourceRequest.setSourceData(sourceData);
SourceResponse response = api.sourcesClient().requestAsync(sourceRequest).get();

SourceProcessed source = response.getSource();
```
