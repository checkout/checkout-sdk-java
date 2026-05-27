package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * Detailed information about a single requirement.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EntityRequirementDetailsResponse extends EntityRequirementListItem {

    /**
     * A user-facing explanation of what is needed to resolve the requirement.
     * [Optional]
     */
    private String message;

    /**
     * JSON Schema that the value supplied to PUT /accounts/entities/{id}/requirements/{requirementId}
     * must conform to. The shape varies by requirement type (for example: an identity document upload,
     * a free-text response, or a structured object). May be {@code null} if no schema is registered
     * for the requirement's field.
     * [Optional]
     */
    @SerializedName("_schema")
    private Map<String, Object> schema;
}
