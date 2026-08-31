package com.checkout.instruments.get;
import lombok.Data;

import java.util.List;

/**
 * A bank account field to collect.
 */
@Data
public final class BankAccountField {

    /**
     * The field identifier.
     * [Required]
     */
    private String id;

    /**
     * The section to display the field in.
     * [Optional]
     */
    private String section;

    /**
     * The field's display name.
     * [Required]
     */
    private String display;

    /**
     * The help text that explains the purpose of the field.
     * [Optional]
     */
    private String helpText;

    /**
     * The type of field.
     * [Required]
     */
    private String type;

    /**
     * Whether the field is required.
     * [Required]
     */
    private Boolean required;

    /**
     * A regular expression that can be used to validate the input of the field.
     * [Optional]
     */
    private String validationRegex;

    /**
     * The minimum length of the field.
     * [Optional]
     */
    private Integer minLength;

    /**
     * The maximum length of the field.
     * [Optional]
     */
    private Integer maxLength;

    /**
     * The allowed options for the field.
     * [Optional]
     */
    private List<BankAccountFieldAllowedOption> allowedOptions;

    /**
     * The field's dependencies.
     * [Optional]
     */
    private List<BankAccountFieldDependency> dependencies;

}
