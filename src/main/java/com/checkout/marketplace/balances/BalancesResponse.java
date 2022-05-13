package com.checkout.marketplace.balances;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class BalancesResponse extends HttpMetadata {

    List<CurrencyAccountBalance> data;

}
