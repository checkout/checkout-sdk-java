package com.checkout.marketplace.balances;

import lombok.Data;

import java.util.List;

@Data
public final class BalancesResponse {

    List<CurrencyAccountBalance> data;

}
