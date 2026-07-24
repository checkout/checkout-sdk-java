package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class Representative {

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private String firstName;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private String middleName;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private String lastName;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private Address address;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private Identification identification;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private AccountPhone phone;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private DateOfBirth dateOfBirth;

    /**
     * @deprecated Not used by the Accounts API v3.0 schema; use {@link #individual} instead.
     */
    @Deprecated
    private PlaceOfBirth placeOfBirth;

    private List<EntityRoles> roles;

    private OnboardSubEntityDocuments documents;

    private RepresentativeIndividual individual;

    private String id;

    private CompanyPosition companyPosition;

    private Integer ownershipPercentage;

}
