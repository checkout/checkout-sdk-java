package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RepresentativeIndividual {

    private String firstName;

    private String middleName;

    private String lastName;

    private DateOfBirth dateOfBirth;

    private PlaceOfBirth placeOfBirth;

    private List<Citizenship> citizenships;

    private NationalIdType nationalIdType;

    private String nationalIdNumber;

    private String emailAddress;

    private AccountPhone phone;

    private Address address;

}
