package com.checkout.issuing.transactions.requests;

import com.checkout.issuing.transactions.entities.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public final class TransactionsQuery {

    private Integer limit;

    private Integer skip;

    private String cardholderId;

    private String cardId;

    private String entityId;

    private TransactionStatus status;

    private Instant from;

    private Instant to;
}