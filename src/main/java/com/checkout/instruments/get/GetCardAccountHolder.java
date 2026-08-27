package com.checkout.instruments.get;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of a stored card instrument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetCardAccountHolder {

    /**
     * The first name of the account holder.
     * [Optional]
     */
    private String firstName;

    /**
     * The last name of the account holder.
     * [Optional]
     */
    private String lastName;

    /**
     * The billing address of the account holder.
     * [Optional]
     */
    private Address billingAddress;

    /**
     * The phone number of the account holder.
     * [Optional]
     */
    private Phone phone;

}
