package com.checkout.accounts;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

/**
 * A pending requirement that the sub-entity must resolve to remain compliant or unlock capabilities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EntityRequirementListItem extends Resource {

    /**
     * The unique identifier of the requirement.
     * [Optional]
     */
    private String id;

    /**
     * The ID of the resource (sub-entity or representative) the requirement applies to.
     * [Optional]
     */
    private String resource;

    /**
     * The type of resource the requirement applies to derived from the resource's URN.
     * Common values include {@code company}, {@code individual}, and {@code representative}.
     * Defaults to {@code entity} if the URN cannot be parsed.
     * [Optional]
     */
    @SerializedName("resource_type")
    private String resourceType;

    /**
     * The reason the requirement was raised.
     * [Optional]
     * Enum: "periodic_review" "attestation"
     */
    private EntityRequirementReason reason;

    /**
     * Priority level of this requirement. {@code high} by default, {@code critical} if deadline
     * is within 7 days.
     * [Optional]
     * Enum: "high" "critical"
     */
    private EntityRequirementPriority priority;

    /**
     * The date and time, in ISO 8601 UTC format, by which the requirement must be resolved.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant deadline;

    /**
     * The schema-registry URN identifying the field this requirement applies to.
     * Format: {@code urn:object:{resource_type}#{resource_id}#field:{field_name}}.
     * [Optional]
     */
    private String urn;

    /**
     * Dot-notation path of the field on the resource that needs to be supplied or updated.
     * May be {@code null} for ad-hoc requirements (such as additional documents or free-text
     * responses) that do not map to a specific field on the entity.
     * [Optional]
     */
    @SerializedName("field_path")
    private String fieldPath;

    /**
     * The schema-registry URN of the field, from the public mapping definition.
     * May be {@code null} if no mapping exists.
     * [Optional]
     */
    @SerializedName("field_urn")
    private String fieldUrn;

    /**
     * Optional metadata from the property mapping definition. Shape varies by requirement.
     * [Optional]
     */
    private Map<String, Object> metadata;
}
