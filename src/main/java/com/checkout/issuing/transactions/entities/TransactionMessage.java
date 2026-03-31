package com.checkout.issuing.transactions.entities;

import lombok.Data;

import java.time.Instant;
import java.util.Currency;

@Data
public final class TransactionMessage {

    private String id;

    private MessageInitiator initiator;

    private MessageType type;

    private MessageResult result;

    private Boolean isRelayed;

    private MessageIndicator indicator;

    private String declineReason;

    private String authorizationCode;

    private Long billingAmount;

    private Currency billingCurrency;

    private Instant createdOn;
}