package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Evidence for creating or updating disputes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeEvidence {

    /**
     * The complete file name, including the extension
     */
    private String name;

    /**
     * The base64-encoded string that represents a single JPG, PDF, TIFF, or ZIP file.
     * ZIP files can contain multiple JPG, PDF, or TIFF files.
     */
    private String content;

    /**
     * A brief description of the evidence
     */
    private String description;

}