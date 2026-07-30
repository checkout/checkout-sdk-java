package com.checkout.identities.addressdocumentverification.responses;

import lombok.Data;

import java.util.List;

/**
 * The result of the address document check.
 */
@Data
public final class AddressDocumentResult {

    /**
     * The type of address document submitted.
     */
    private String documentType;

    /**
     * The issuer of the address document.
     */
    private String issuer;

    /**
     * The full names of the people named on the document.
     */
    private List<String> fullNames;

    /**
     * The date the document was issued.
     * Format: date (yyyy-MM-dd)
     */
    private String issueDate;

    /**
     * The address extracted from the document.
     */
    private Address address;
}
