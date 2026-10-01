package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * A representative of the sub-entity. One class covers every shape the Accounts API defines:
 * <ul>
 * <li>v3.0 person of interest: {@code individual}, {@code roles}, {@code companyPosition},
 * {@code ownershipPercentage}, {@code documents}.</li>
 * <li>v3.0 controlling company (EEA and GB Company Full): {@code company} and
 * {@code ownershipPercentage}.</li>
 * <li>v2.0 company representatives: the deprecated flat person fields, {@code roles},
 * {@code documents} and, on the US variants, {@code identification}.</li>
 * </ul>
 */
@Data
@Builder
public final class Representative {

    /**
     * The representative's first name. Accounts API v2.0 only.
     * [Required] (v2.0)
     * min 2 characters, max 50 characters
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private String firstName;

    /**
     * The representative's middle name. Required if it appears in official documents. Accounts API
     * v2.0 only.
     * [Optional]
     * min 2 characters, max 50 characters
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private String middleName;

    /**
     * The representative's last name. Accounts API v2.0 only.
     * [Required] (v2.0)
     * min 2 characters, max 50 characters
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private String lastName;

    /**
     * The representative's address. Accounts API v2.0 only.
     * [Required] (v2.0)
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private Address address;

    /**
     * The representative's identification. Accounts API v2.0 US Company variants only.
     * [Required] for US Company Full (2.0); [Optional] for US Company Lite (2.0).
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private Identification identification;

    /**
     * The representative's phone number. Accounts API v2.0 only.
     * [Optional]
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private AccountPhone phone;

    /**
     * The date of birth of the person according to the Gregorian calendar. Accounts API v2.0 only.
     * [Required] for the v2.0 Full variants; [Optional] for the v2.0 Lite variants.
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private DateOfBirth dateOfBirth;

    /**
     * The place of birth of the person. Accounts API v2.0 only.
     * [Required] for EEA Company Full (2.0); [Optional] for EEA Company Lite (2.0). Not part of the
     * other v2.0 variants.
     *
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private PlaceOfBirth placeOfBirth;

    /**
     * The individual's roles within the company. For sole traders, must be {@code ubo} only.
     * [Required] for every variant except EEA and US Company Lite (2.0), where it is [Optional].
     */
    private List<EntityRoles> roles;

    /**
     * Verification documents for the individual representative. The API validates this object
     * strictly on v3.0: it accepts only {@code identity_verification},
     * {@code certified_authorised_signatory}, {@code proof_of_residential_address} and
     * {@code proof_of_registration}, and rejects any other key. See
     * {@link OnboardSubEntityDocuments} for which apply to each variant.
     * [Required] for the EEA, GB and US Sole Trader Full (3.0) variants and EEA Company Full (2.0);
     * [Optional] otherwise.
     */
    private OnboardSubEntityDocuments documents;

    /**
     * Information about the individual representing the sub-entity.
     * [Required] for every v3.0 person of interest.
     */
    private RepresentativeIndividual individual;

    /**
     * The representative's id.
     * [Optional]
     * ^rep_[a-z0-9]{26}$
     * 30 characters
     */
    private String id;

    /**
     * The position of the representative within the company (required for the
     * {@code control_person} role).
     * [Optional] (EEA, GB and US Company Full (3.0) and US ISV Seller Company (3.0))
     */
    private CompanyPosition companyPosition;

    /**
     * The percentage ownership of the UBO or controlling company (required when over 25%).
     * [Optional]
     * min 25, max 100 on the EEA, GB and US Company Full (3.0) variants; min 0, max 100 on the US ISV
     * Seller variants
     */
    private Integer ownershipPercentage;

    /**
     * The controlling company, when the representative is a company rather than an individual.
     * [Required] for a controlling company representative (EEA and GB Company Full (3.0) only).
     * The API reads only three fields here, all [Required]: {@code legalName}, {@code tradingName}
     * and {@code registeredAddress}. Leave the other {@link Company} fields unset.
     */
    private Company company;

}
