package com.checkout.issuing.cardholders;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardholderDetailsResponse extends Resource {
    
    private String id;

    private CardholderType type;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private Phone phoneNumber;

    private String dateOfBirth;

    private Address billingAddress;

    private Address residencyAddress;

    private String reference;

    private String accountEntityId;

    private String parentSubEntityId;

    private String entityId;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
