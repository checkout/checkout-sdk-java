---
id: sources
title: Sources
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Sources).

## Add a payment source (SEPA)

```java
Address billingAddress = new Address();
SourceData sourceData = new SourceData();

SourceRequest sourceRequest = new SourceRequest("sepa", billingAddress);
sourceRequest.setSourceData(sourceData);

SourceResponse response = api.sourcesClient().requestAsync(sourceRequest).get();

SourceProcessed source = response.getSource();
```

## Add a payment source (ACH)

ACH is not enabled by default, so please let your account manager know if you want to use it.

```java
Address billingAddress = new Address();
SourceData sourceData = new SourceData();

SourceRequest sourceRequest = new SourceRequest("ach", billingAddress);
sourceRequest.setReference("Java SDK test");
sourceRequest.setSourceData(sourceData);
SourceResponse response = api.sourcesClient().requestAsync(sourceRequest).get();

SourceProcessed source = response.getSource();
```
