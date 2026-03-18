package com.checkout.issuing.transactions.entities;

import lombok.Data;

@Data
public class ReferenceTransaction {

    private String transactionId;

    private ReferenceType referenceType;
}