package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base class for attempt responses
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
     */
    private String redirectUrl;

    /**
     * The applicant's details.
     */
    private ClientInformation clientInformation;

    /**
     * The details of the attempt.
     */
    private ApplicantSessionInformation applicantSessionInformation;
}