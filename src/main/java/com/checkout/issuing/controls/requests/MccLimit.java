package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The merchant category code (MCC) rule, which determines the types of businesses transactions can be processed from.
 */
@Data
@Builder
public final class MccLimit {

    /**
     * Sets whether to allow or block the list of MCCs supplied.
     * [Required]
     * Enum: "allow", "block"
     */
    private String type;

    /**
     * The list of MCCs to allow or block transactions from, as 4-digit ISO 18245 codes.
     * [Required]
     */
    private List<String> mccList;
}
