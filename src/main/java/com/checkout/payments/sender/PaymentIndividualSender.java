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

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentIndividualSender extends PaymentSender {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("dob")
    private String dob;

    private Address address;

    private AccountHolderIdentification identification;

    @SerializedName("reference_type")
    private String referenceType;

    @SerializedName("source_of_funds")
    private SourceOfFunds sourceOfFunds;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("country_of_birth")
    private CountryCode countryOfBirth;

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
                                    final String dateOfBirth,
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
