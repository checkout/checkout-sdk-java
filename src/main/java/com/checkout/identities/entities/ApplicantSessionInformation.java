package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The details of the attempt.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplicantSessionInformation {

    /**
     * The applicant's IP address during the attempt.
     * [Optional]
     * Example: 123.4.5.6
     */
    private String ipAddress;

    /**
     * The number of sessions the applicant opened during the attempt.
     * [Optional]
     * Example: 3
     */
    private Integer numberOfSessions;

    /**
     * The user agent of the browser the applicant used during the attempt.
     * [Optional]
     */
    private String userAgent;

    /**
     * The type of device the applicant used to start the attempt.
     * [Optional]
     */
    private InitialDevice initialDevice;

    /**
     * The documents the applicant selected in order.
     * Returned for identity verification attempts only; not returned for face authentication
     * attempts.
     * [Optional]
     */
    private List<SelectedDocument> selectedDocuments;
}
