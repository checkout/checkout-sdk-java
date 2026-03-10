package com.checkout.issuing.transactions.entities;

import lombok.Data;

@Data
public class TransactionAmounts {

    private TransactionAmount totalHeld;

    private TransactionAmount totalAuthorized;

    private TransactionAmount totalReversed;

    private TransactionAmount totalCleared;

    private TransactionAmount totalRefunded;
}