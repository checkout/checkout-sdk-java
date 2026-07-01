package com.checkout.issuing.cardholders;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CardholderUpdateRequest {

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private Phone phoneNumber;

    private String dateOfBirth;

    private Address billingAddress;

    private Address residencyAddress;
}
