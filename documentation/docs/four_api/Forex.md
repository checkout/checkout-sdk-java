---
id: forex
title: Forex
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Forex).

## Request a quote

Request an exchange rate between a source and destination currency pair that will be used to process one or more payouts. You must submit a payout with the FX quote identifier before the quote expires. If the FX quote identifier is omitted from a payout, and the source and destination currencies differ, the current market exchange rate will be used.

```java
QuoteRequest request = QuoteRequest.builder()
        .sourceCurrency()
        .sourceAmount()
        .destinationCurrency()
        .processChannelId()
        .build();

QuoteResponse response = fourApi.forexClient().requestQuote(request).get();
```
