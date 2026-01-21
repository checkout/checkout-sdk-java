package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.SenderType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Identification;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class IndividualSender extends AbstractSender {

    /**
     * The sender's first name
     */
    private String firstName;
    /**
     * The sender's last name
     */
    private String lastName;
    /**
     * @deprecated This property will be removed in the future, and should be used with caution.
     * This field is deprecated. Use date_of_birth instead. The sender's date of birth, in the format yyyy-mm-dd.
     */
    @Deprecated
    private Instant dob;
    /**
     * The sender's date of birth, in the format yyyy-mm-dd.
     */
    private String dateOfBirth;
    /**
     * The sender's address
     */
    private Address address;
    /**
     * The sender's reference for the payout
     */
    private String reference;

    private Identification identification;

    @Builder
    private IndividualSender(
            final String firstName,
            final String lastName,
            final Instant dob,
            final String dateOfBirth,
            final Address address,
            final Identification identification,
            final String reference
    ) {
        super(SenderType.INDIVIDUAL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.identification = identification;
        this.reference = reference;
    }

    public IndividualSender() {
        super(SenderType.INDIVIDUAL);
    }

}
