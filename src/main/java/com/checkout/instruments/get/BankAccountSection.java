package com.checkout.instruments.get;

import lombok.Data;

import java.util.List;

/**
 * A section of bank account fields to collect.
 */
@Data
public final class BankAccountSection {

    /**
     * The name of the section.
     * [Required]
     */
    private String name;

    /**
     * The fields to collect in this section.
     * [Optional]
     */
    private List<BankAccountField> fields;

}
