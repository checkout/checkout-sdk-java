package com.checkout.payments.sender;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentIndividualSender extends PaymentSender {

    /**
     * The sender's first name.
     * [Required]
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The sender's middle name.
     * [Optional]
     */
    @SerializedName("middle_name")
    private String middleName;

    /**
     * The sender's last name.
     * [Required]
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The sender's date of birth.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("dob")
    private String dob;

    /**
     * The sender's address.
     * [Required]
     */
    private Address address;

    /**
     * The sender's identification details.
     * [Optional]
     */
    private AccountHolderIdentification identification;

    /**
     * The reference type for the sender.
     * [Optional]
     */
    @SerializedName("reference_type")
    private String referenceType;

    /**
     * The source of funds for the sender.
     * [Optional]
     */
    @SerializedName("source_of_funds")
    private SourceOfFunds sourceOfFunds;

    /**
     * The sender's date of birth (yyyy-MM-dd).
     * [Optional]
     * Format: yyyy-MM-dd
     * <= 10 characters
     */
    @SerializedName("date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * The sender's country of birth.
     * [Optional]
     */
    @SerializedName("country_of_birth")
    private CountryCode countryOfBirth;

    /**
     * The sender's nationality.
     * [Optional]
     */
    private CountryCode nationality;

    @Builder
    private PaymentIndividualSender(final String reference,
                                    final String firstName,
                                    final String middleName,
                                    final String lastName,
                                    final String dob,
                                    final Address address,
                                    final AccountHolderIdentification identification,
                                    final String referenceType,
                                    final SourceOfFunds sourceOfFunds,
                                    final LocalDate dateOfBirth,
                                    final CountryCode countryOfBirth,
                                    final CountryCode nationality
    ) {
        super(SenderType.INDIVIDUAL, reference);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.identification = identification;
        this.referenceType = referenceType;
        this.sourceOfFunds = sourceOfFunds;
        this.dateOfBirth = dateOfBirth;
        this.countryOfBirth = countryOfBirth;
        this.nationality = nationality;
    }

    public PaymentIndividualSender() {
        super(SenderType.INDIVIDUAL);
    }

}
