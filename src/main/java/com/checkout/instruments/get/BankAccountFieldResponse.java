package com.checkout.instruments.get;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The bank account field formatting requirements for a country and currency.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class BankAccountFieldResponse extends HttpMetadata {

    /**
     * The sections of fields to collect.
     * [Optional]
     */
    private List<BankAccountSection> sections;

}
