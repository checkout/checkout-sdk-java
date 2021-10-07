---
id: customers
title: Customers
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Customers).

## Create a customer

Create a customer which can be linked to one or more payment instruments, and can be passed as a source when making a payment, using the customer’s default instrument.

```java
CustomerRequest customerRequest = CustomerRequest.builder()
        .email()
        .name()
        .phone(Phone.builder().build())
        .build();
IdResponse response = fourApi.customersClient().create(customerRequest).get();
```

## Get customer details

Returns the details of a customer and their instruments.

```java
CustomerResponse response = fourApi.customersClient().get(customerId).get();
```

## Update customer details

Update details of a customer.

```java
CustomerRequest customerRequest = CustomerRequest.builder()
        .email()
        .name()
        .phone(Phone.builder()
                .countryCode()
                .number()
                .build())
        .build();

fourApi.customersClient().update(customerId, request).get();
```

## Delete a customer

Delete a customer and all of their linked payment instruments.

```java
fourApi.customersClient().delete(customerId).get();
```
