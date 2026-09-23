package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base class for attempt responses.
 * Extended only by the identity verification and face authentication attempt responses, which are
 * the two attempt types that carry a redirect URL, session information and a phone number. The
 * address and ID document verification attempt responses extend
 * {@link BaseIdentityResponseStatus} directly, because their schemas declare none of these.
 *
 * @param <T> The status enum type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseAttemptResponse<T extends Enum<T>> extends BaseIdentityResponseStatus<T> {

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    private String redirectUrl;

    /**
     * The applicant's mobile phone number, if sharing the attempt URL via SMS.
     * [Optional]
     */
    private PhoneNumber phoneNumber;

    /**
     * The details of the attempt.
     * [Optional]
     */
    private ApplicantSessionInformation applicantSessionInformation;
}
