---
id: instruments
title: Instruments
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Instruments).

## Create an instrument

Create a card or bank account payment instrument to use for future payments and payouts.

```java
// other instruments available
CreateInstrumentTokenRequest request = CreateInstrumentTokenRequest.builder()
    .token("token")
    .accountHolder(AccountHolder.builder().build())
    .customer(CreateCustomerInstrumentRequest.builder()
            .id()
            .build())
    .build();

CreateInstrumentTokenResponse response = fourApi.instrumentsClient().create(request).get();
```

## Get instrument details

Retrieve the details of a payment instrument.

```java
GetCardInstrumentResponse getResponse = (GetCardInstrumentResponse) fourApi.instrumentsClient().get(instrumentId).get();

```

## Update an instrument

Update the details of a payment instrument.

```java
UpdateInstrumentCardRequest updateRequest = UpdateInstrumentCardRequest.builder()
    .expiryMonth()
    .expiryYear()
    .name()
    .customer(UpdateCustomerRequest.builder().build())
    .accountHolder(AccountHolder.builder().build())
    .build();

UpdateInstrumentCardResponse updateResponse = fourApi.instrumentsClient().update(instrumentId, updateRequest).get();
```

## Delete an instrument

Delete a payment instrument.

```java
fourApi.instrumentsClient().delete(instrumentId).get();
```
