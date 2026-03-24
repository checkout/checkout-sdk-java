package com.checkout.compliance.entities;

import lombok.Data;

/**
 * Describes a single requested field within a compliance request.
 */
@Data
public final class RequestedField {

    private String name;

    private String type;

    private String value;

}
