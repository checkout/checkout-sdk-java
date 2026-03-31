package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ClientInformation {
    /**
     * The applicant's residence country.
     */
    private String preSelectedResidenceCountry;

    /**
     * The language you want to use for the user interface.
     */
    private String preSelectedLanguage;
}