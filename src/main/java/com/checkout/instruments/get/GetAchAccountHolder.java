package com.checkout.instruments.get;

import com.checkout.instruments.InstrumentAccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of a stored ACH instrument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetAchAccountHolder {

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
