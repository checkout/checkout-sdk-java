package com.checkout.instruments.update;

import com.checkout.instruments.InstrumentAccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of an ACH instrument being updated.
 *
 * <p>The specification marks all four properties as required, but the descriptions qualify that:
 * the names apply to an individual account holder and the company name to a corporate one. That is a
 * conditional requirement the specification cannot express, so it is not enforced here.
 *
 * <p>This deliberately does not use {@code com.checkout.common.AccountHolder}, which is a superset
 * carrying a phone number, identification, a date of birth and a tax ID that the ACH instrument
 * schema does not declare.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateAchAccountHolder {

    /**
     * First name. Required for individual account holder type.
     * [Required]
     */
    private String firstName;

    /**
     * Last name. Required for individual account holder type.
     * [Required]
     */
    private String lastName;

    /**
     * Company name. Required for corporate account holder type.
     * [Required]
     */
    private String companyName;

    /**
     * Account holder type.
     * [Required]
     * Enum: "individual" "corporate"
     */
    private InstrumentAccountHolderType type;

}
