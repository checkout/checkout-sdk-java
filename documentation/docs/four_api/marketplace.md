---
id: marketplace
title: Marketplace
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Marketplace).

## Onboard a sub-entity

Onboard a sub-entity so they can start receiving payments. Once created, Checkout.com will run due diligence checks. If the checks are successful, we'll enable payment capabilities for that sub-entity and they will start receiving payments.

```java
OnboardEntityRequest onboardEntityRequest = OnboardEntityRequest.builder()
    .reference()
    .contactDetails(ContactDetails.builder().build())
    .profile(Profile.builder().build())
    .individual(Individual.builder().build())
    .build();

OnboardEntityResponse entityResponse = fourApi.marketplaceClient().createEntity(onboardEntityRequest).get();
```
## Get sub-entity details

Use this endpoint to retrieve a sub-entity and its full details.

```java
OnboardEntityDetailsResponse entityDetailsResponse = fourApi.marketplaceClient().getEntity(entityId).get();
```
## Update sub-entity details

You can update all fields under the Contact details, Profile, and Company objects. You can also add identification information to complete due diligence requirements.

```java
OnboardEntityResponse updatedEntityResponse = fourApi.marketplaceClient().updateEntity(onboardEntityRequest, entityId).get();
```

## Add a payment instrument

Create a bank account payment instrument for your sub-entity that you can later use as the destination for their payouts.

```java
fourApi.marketplaceClient().createPaymentInstrument(marketplacePaymentInstrument, entityId).get();
```

## Upload a file

Uploads identity documentation required for full due diligence.

Note: This feature must be enabled when the SDK [is instantiated](https://github.com/checkout/checkout-sdk-java/blob/master/README.md).

```java
URL resource = getClass().getClassLoader().getResource("file.txt");
File file = new File(resource.toURI());
MarketplaceFileRequest fileRequest = MarketplaceFileRequest.builder()
                .file(file)
                .contentType()
                .purpose()
                .type()
                .build();
IdResponse fileResponse = fourApi.marketplaceClient().submitFile(fileRequest).get();
```
