package com.checkout.balances;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The balances for each currency account (sub-account) belonging to an entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class BalancesResponse extends HttpMetadata {

    /**
     * The balances for each currency account that matched the query.
     * [Optional]
     */
    List<CurrencyAccountBalance> data;

}
