package com.checkout.instruments.create;

import com.checkout.instruments.InstrumentAccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of an ACH instrument being stored.
 *
 * <p>The specification marks all four properties as required, but the descriptions qualify that:
 * the names apply to an individual account holder and the company name to a corporate one.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAchAccountHolder {

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
