package com.checkout.issuing.transactions.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class TransactionsListResponse extends Resource {

    private Integer limit;

    private Integer skip;

    private Integer totalCount;

    private List<TransactionResponse> data;
}