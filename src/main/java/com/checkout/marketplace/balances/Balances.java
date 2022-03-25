package com.checkout.marketplace.balances;

import lombok.Data;

@Data
public final class Balances {

    private Long pending;

    private Long available;

    private Long payable;

    private Long collateral;

}
