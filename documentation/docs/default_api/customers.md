---
id: customers
title: Customers
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Customers).

## Create a customer

Create a customer which can be linked to one or more payment instruments, and can be passed as a source when making a payment, using the customer’s default instrument.

```java
CustomerRequest request = CustomerRequest.builder()
        .email()
        .name()
        .phone(Phone.builder().build())
        .build();

IdResponse response = api.customersClient().create(request).get();
```

## Get customer details

Returns the details of a customer and their instruments.

```java
CustomerDetailsResponse response = api.customersClient().get(request).get();
```

## Update customer details

Update details of a customer

```java
CustomerRequest request = CustomerRequest.builder()
        .email()
        .name()
        .phone(Phone.builder().build())
        .build();

api.customersClient().update("cus_123456789ASDZX", request).get();
```

## Delete a customer

Delete a customer and all of their linked payment instruments

```java
api.customersClient().delete("cus_123456789ASDZX").get();
```
