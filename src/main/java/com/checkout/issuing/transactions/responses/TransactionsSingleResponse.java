package com.checkout.issuing.transactions.responses;

import com.checkout.common.Resource;
import com.checkout.issuing.transactions.entities.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionsSingleResponse extends Resource {

    private String id;

    private Instant createdOn;

    private TransactionStatus status;

    private TransactionType transactionType;

    private TransactionClient client;

    private TransactionEntity entity;

    private TransactionCard card;

    private DigitalCard digitalCard;

    private TransactionCardholder cardholder;

    private TransactionAmounts amounts;

    private Merchant merchant;

    private ReferenceTransaction referenceTransaction;

    private List<TransactionMessage> messages;
}