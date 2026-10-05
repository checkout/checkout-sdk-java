package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the user responsible for onboarding the sub-entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Invitee {

    /**
     * The email of the user responsible for onboarding the sub-entity.
     * [Required] in the hosted onboarding invite request (with reference and is_draft); [Optional] in
     * the full onboarding variants (every Full and Lite variant); not part of the US ISV Seller variants.
     * Format: email
     */
    private String email;

}
