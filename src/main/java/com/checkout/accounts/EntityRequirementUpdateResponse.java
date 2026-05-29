package com.checkout.accounts;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

/**
 * Acknowledges that a requirement response has been accepted for processing.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EntityRequirementUpdateResponse extends Resource {

    /**
     * The unique identifier of the requirement.
     * [Optional]
     */
    private String id;

    /**
     * Processing status of the submitted response.
     * [Optional]
     * Enum: "processing"
     */
    private EntityRequirementUpdateStatus status;

    /**
     * The date and time, in ISO 8601 UTC format, the response was accepted.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    @SerializedName("submitted_at")
    private Instant submittedAt;
}
