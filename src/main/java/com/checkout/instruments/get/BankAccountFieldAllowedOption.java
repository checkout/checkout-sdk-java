package com.checkout.instruments.get;

import lombok.Data;

/**
 * An allowed option for a bank account field.
 */
@Data
public final class BankAccountFieldAllowedOption {

    /**
     * The option identifier.
     * [Optional]
     */
    private String id;

    /**
     * The option display value.
     * [Optional]
     */
    private String display;

}
