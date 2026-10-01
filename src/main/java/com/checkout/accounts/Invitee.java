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
     * The main email address for this sub-entity. Despite the spec's wording, this is the address of
     * the invitee, the user responsible for onboarding the sub-entity.
     * [Optional]
     * Format: email
     */
    private String email;

}
