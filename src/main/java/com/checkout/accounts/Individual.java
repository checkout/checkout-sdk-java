package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

/**
 * The top-level {@code individual} of the Accounts API v2.0 sole trader variants.
 */
@Data
@Builder
public final class Individual {

    /**
     * The individual's first name.
     * [Required]
     * min 2 characters, max 50 characters
     */
    private String firstName;

    /**
     * The individual's middle name. Required if it appears in official documents.
     * [Optional]
     * min 2 characters, max 50 characters
     */
    private String middleName;

    /**
     * The individual's last name.
     * [Required]
     * min 2 characters, max 50 characters
     */
    private String lastName;

    /**
     * The trading name of the sub-entity, also referred to as 'doing business as'.
     * [Required]
     * min 2 characters, max 300 characters
     */
    private String tradingName;

    /**
     * Not defined by any Accounts API schema. Retained so existing code keeps compiling.
     *
     * @deprecated Not defined by any Accounts API schema; the API does not read it. Will be removed in
     * a future major version.
     */
    @Deprecated
    private String nationalTaxId;

    /**
     * The registered address of the sole trader's business.
     * [Required]
     */
    private Address registeredAddress;

    /**
     * The date of birth of the person according to the Gregorian calendar.
     * [Required], except on GB Sole Trader Lite (2.0) where it is [Optional].
     */
    private DateOfBirth dateOfBirth;

    /**
     * The place of birth of the person.
     * [Required] for EEA Sole Trader Full and Lite (2.0); not part of the other v2.0 variants.
     */
    private PlaceOfBirth placeOfBirth;

    /**
     * The individual's identification. US Sole Trader (2.0) only.
     * [Required] for US Sole Trader Full (2.0); [Optional] for US Sole Trader Lite (2.0).
     */
    private Identification identification;

    /**
     * Seller financial questions and supporting documents. US Sole Trader (2.0) only.
     * [Required] for US Sole Trader Full (2.0); [Optional] for US Sole Trader Lite (2.0).
     */
    private EntityFinancialDetails financialDetails;

}
