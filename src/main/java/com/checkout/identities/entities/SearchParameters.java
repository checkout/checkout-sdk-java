package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for creating AML screening
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchParameters {
    private String configurationIdentifier;
}
