package com.checkout.compliance.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Describes a single response field provided for a compliance request.
 */
@Data
public class ComplianceRequestRespondedField {

    private String name;

    private Object value;

    @SerializedName("not_available")
    private Boolean notAvailable;

}
