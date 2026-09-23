package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The certification data returned for the diatf certification type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DiatfCertificationData {

    /**
     * The GPG 45 identity profile the verification meets.
     * [Optional]
     */
    private Gpg45Profile gpg45Profile;

    /**
     * The level of confidence in the verified identity.
     * [Optional]
     */
    private LevelOfConfidence levelOfConfidence;

    /**
     * The outcome of the applicant's right to work check.
     * [Optional]
     * Example: GRANTED
     */
    private String rightToWork;

}
