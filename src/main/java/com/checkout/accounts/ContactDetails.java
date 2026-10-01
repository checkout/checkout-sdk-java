package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contact details of the sub-entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ContactDetails {

    /**
     * The phone number of the sub-entity.
     * [Required] for every Accounts API v2.0 variant and the US ISV Seller variants; [Optional] for the
     * other v3.0 variants. On v3.0 {@code countryCode} is required and is the ISO 3166-1 alpha-2
     * country where the number is registered (for example {@code FR}), not the dialling code; v2.0
     * takes {@code number} only. {@code number} is the number without the country calling code, and
     * its format depends on the variant:
     * <ul>
     * <li>v3.0 EEA: ^[0-9]{6,13}$, min 6 characters, max 13 characters</li>
     * <li>v3.0 GB: ^[0-9]{7,11}$, min 7 characters, max 11 characters</li>
     * <li>v3.0 US and US ISV Seller: ^[1-9][0-9]{9,16}$, min 10 characters, max 16 characters</li>
     * <li>v2.0: ^[1-9][0-9]{7,15}$, min 8 characters, max 16 characters; on the US v2.0 variants
     * ^[2-9]{1}[0-9]{9,15}$, min 10 characters</li>
     * </ul>
     */
    private AccountPhone phone;

    /**
     * Email addresses for this sub-entity.
     * [Required] for every Accounts API v2.0 variant and the US ISV Seller variants; [Optional] for the
     * other v3.0 variants.
     */
    private EntityEmailAddresses emailAddresses;

    /**
     * The details of the user responsible for onboarding the sub-entity.
     * [Optional] (not part of the US ISV Seller variants)
     */
    private Invitee invitee;

}
