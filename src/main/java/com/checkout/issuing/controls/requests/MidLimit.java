package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The merchant identification (MID) code rule, which determines the merchants from whom transactions can be processed.
 */
@Data
@Builder
public final class MidLimit {

    /**
     * Sets whether to allow or block the list of MIDs supplied.
     * [Required]
     * Enum: "allow", "block"
     */
    private String type;

    /**
     * The list of merchant identification (MID) codes to allow or block transactions from.
     * [Required]
     * Each item: 1 to 15 characters, ^[A-Za-z0-9{}\[\] ,+\-=.();'\/&amp;@*]{1,15}$
     */
    private List<String> midList;
}
