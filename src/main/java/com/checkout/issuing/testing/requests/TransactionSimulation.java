package com.checkout.issuing.testing.requests;

import com.checkout.common.Currency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionSimulation {

    private TransactionType type;

    private Integer amount;

    private Currency currency;

    private TransactionMerchant merchant;

    private TransactionAuthorizationType transaction;
}
