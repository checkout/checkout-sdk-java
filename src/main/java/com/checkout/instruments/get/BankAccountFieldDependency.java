package com.checkout.instruments.get;

import lombok.Data;

/**
 * A dependency that controls whether a bank account field is displayed.
 */
@Data
public final class BankAccountFieldDependency {

    /**
     * The field identifier.
     * [Optional]
     */
    private String fieldId;

    /**
     * The value of the dependent field that must match in order for this field to be displayed.
     * [Optional]
     */
    private String value;

}
