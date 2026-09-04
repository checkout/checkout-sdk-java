package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder's details on an ACH payment source.
 *
 * <p>Maps the AccountHolderAch schema exactly. Deliberately not
 * {@link com.checkout.common.AccountHolder}, which is a superset, and distinct from
 * {@link com.checkout.instruments.create.CreateAchAccountHolder}, which declares four properties
 * only - the instrument schema has no billing address, date of birth or identification.
 *
 * <p>The billing address reuses {@link Address} because that schema's six properties are exactly
 * what this position references. The identification reuses {@link AccountHolderIdentification},
 * which carries one extra property, a date of expiry, that this position does not declare - do not
 * set it.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RequestAchAccountHolder {

    /**
     * The type of account holder.
     * [Required]
     * Enum: "individual" "corporate" "government"
     */
    private AccountHolderType type;

    /**
     * The account holder's first name.
     * [Required]
     */
    private String firstName;

    /**
     * The account holder's last name.
     * [Required]
     */
    private String lastName;

    /**
     * The account holder's company name.
     * [Optional]
     */
    private String companyName;

    /**
     * The account holder's billing address.
     * [Optional]
     */
    private Address billingAddress;

    /**
     * The account holder's date of birth.
     * [Optional]
     */
    private String dateOfBirth;

    /**
     * The account holder's identification.
     * [Optional]
     */
    private AccountHolderIdentification identification;

}
