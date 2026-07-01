package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class Representative {

    private String firstName;

    private String middleName;

    private String lastName;

    private Address address;

    private Identification identification;

    private AccountPhone phone;

    private DateOfBirth dateOfBirth;

    private PlaceOfBirth placeOfBirth;

    private List<EntityRoles> roles;

    private OnboardSubEntityDocuments documents;

}
