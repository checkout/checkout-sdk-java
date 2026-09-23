package com.checkout.identities.entities;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the document the applicant selected.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SelectedDocument {

    /**
     * The country that issued the selected document.
     * [Optional]
     * Standard: ISO 3166-1 alpha-2 country code
     * Pattern: ^[A-Za-z]{2}$
     */
    private CountryCode country;

    /**
     * The type of identity document.
     * [Optional]
     */
    private DocumentType documentType;

}
