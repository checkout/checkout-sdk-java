package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body used to resolve a requirement. The shape of {@code value} is defined by the
 * requirement's {@code _schema} (returned from GET /accounts/entities/{id}/requirements/{requirementId}).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class EntityRequirementUpdateRequest {

    /**
     * The response to the requirement. The expected shape depends on the requirement and is defined
     * by the JSON Schema returned in the requirement details response. Common shapes include a file
     * reference (for document uploads), a primitive value, or a structured object.
     * [Required]
     */
    private Object value;
}
