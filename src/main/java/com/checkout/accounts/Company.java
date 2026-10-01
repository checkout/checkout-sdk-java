package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Information about the company represented by the sub-entity: on every company and v3.0 sole
 * trader variant, and as the controlling company of a {@link Representative} (where only
 * {@code legalName}, {@code tradingName} and {@code registeredAddress} apply).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Company {

    /**
     * The legal name of the sub-entity.
     * [Required] for every company variant and the controlling company; not part of the sole trader
     * variants.
     * min 2 characters, max 300 characters
     */
    private String legalName;

    /**
     * The trading name of the sub-entity, also referred to as 'doing business as'.
     * [Required]
     * min 2 characters, max 300 characters
     */
    private String tradingName;

    /**
     * The sub-entity's business registration number: a Commercial Registration or Ministry of Commerce
     * certificate number, or an equivalent registration number.
     * [Required] for the Full variants and US ISV Seller Company (3.0); [Optional] for the Lite (2.0)
     * variants. Not part of the sole trader variants.
     * The format depends on the variant:
     * <ul>
     * <li>EEA: min 2 characters, max 39 characters; a SIRET number for sub-entities based in France.</li>
     * <li>GB (3.0): a Companies House number,
     * ^(((AC|CE|CS|FC|FE|GE|GS|IC|LP|NC|NF|NI|NL|NO|NP|OC|OE|PC|R0|RC|SA|SC|SE|SF|SG|SI|SL|SO|SR|SZ|ZC|\d{2})\d{6})|((IP|SP|RS)[A-Z\d]{6})|(SL\d{5}[\dA]))$,
     * 8 characters. GB (2.0) accepts the same pattern case-insensitively.</li>
     * <li>US: an Employer Identification Number (EIN), ^[0-9]{9}$, 9 characters; US ISV Seller Company
     * (3.0) also accepts the hyphenated form, ^[0-9]{2}-?[0-9]{7}$, min 9 characters, max 11
     * characters.</li>
     * </ul>
     */
    private String businessRegistrationNumber;

    /**
     * The date the company was incorporated, or the date the sole trader started trading.
     * [Required] for every v3.0 variant; [Optional] for EEA, GB and US Company Full (2.0).
     */
    private DateOfIncorporation dateOfIncorporation;

    /**
     * The regulatory licence number of the company.
     * [Optional] (EEA Company Full (3.0) only)
     * ^[a-zA-Z0-9\-]+$
     * min 4 characters, max 32 characters
     */
    private String regulatoryLicenceNumber;

    /**
     * The primary location where business is performed.
     * [Required] for every company and v3.0 sole trader variant.
     */
    private Address principalAddress;

    /**
     * The registered address of the company.
     * [Required] for every company variant and the controlling company; not part of the sole trader
     * variants.
     */
    private Address registeredAddress;

    /**
     * Information about the representatives of this company. See {@link Representative}.
     * [Required]
     * min 1 item; max 1 item for the sole trader variants (the individual themselves, with roles
     * {@code [ubo]}), max 5 on v2.0, max 25 on EEA, GB and US Company Full (3.0), no maximum on US ISV
     * Seller Company (3.0)
     */
    private List<Representative> representatives;

    /**
     * Not defined by any Accounts API company schema. Retained so existing code keeps compiling.
     *
     * @deprecated Not part of any Accounts API schema; the API does not read it. Will be removed in a
     * future major version.
     */
    @Deprecated
    private EntityDocument document;

    /**
     * Seller financial questions and supporting documents.
     * [Required] for EEA and US Company Full (2.0); [Optional] for EEA and US Company Lite (2.0). Not
     * part of the other variants.
     */
    private EntityFinancialDetails financialDetails;

    /**
     * The legal type of the company. Must be {@code individual_or_sole_proprietorship} for the sole
     * trader variants.
     * [Required], except on EEA and US Company Lite (2.0) where it is [Optional]. Not part of GB
     * Company Full and Lite (2.0).
     */
    private BusinessType businessType;

    /**
     * The collection of additional trading names for the sub-entity.
     * [Optional] (US ISV Seller variants only)
     */
    private List<String> additionalTradingNames;

    /**
     * Indicates whether the sub-entity is a registered legal entity. Must be {@code false} for US ISV
     * Seller Sole Trader (3.0).
     * [Required] for US ISV Seller Sole Trader (3.0); not part of the other variants.
     */
    private Boolean isRegisteredCompany;

}
