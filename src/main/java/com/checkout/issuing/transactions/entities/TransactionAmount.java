package com.checkout.issuing.transactions.entities;

import java.util.Currency;

import lombok.Data;

@Data
public class TransactionAmount {
    private Long amount;
    private Currency currency;
}