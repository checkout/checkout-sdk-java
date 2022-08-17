package com.checkout.payments.sender;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.Address;
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

    @SerializedName("last_name")
    private String lastName;

    private Address address;

    private AccountHolderIdentification identification;

    //BETA
    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("reference_type")
    private String referenceType;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("source_of_funds")
    private SourceOfFunds sourceOfFunds;

    @SerializedName("country_of_birth")
    private String countryOfBirth;

    private String nationality;

    @Builder
    private PaymentIndividualSender(final String reference,
                                    final String firstName,
                                    final String lastName,
                                    final Address address,
                                    final AccountHolderIdentification identification,
                                    final String middleName,
                                    final String referenceType,
                                    final String dateOfBirth,
                                    final SourceOfFunds sourceOfFunds,
                                    final String countryOfBirth,
                                    final String nationality) {
        super(SenderType.INDIVIDUAL, reference);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.identification = identification;
        this.middleName = middleName;
        this.referenceType = referenceType;
        this.dateOfBirth = dateOfBirth;
        this.sourceOfFunds = sourceOfFunds;
        this.countryOfBirth = countryOfBirth;
        this.nationality = nationality;
    }

    public PaymentIndividualSender() {
        super(SenderType.INDIVIDUAL);
    }

}
