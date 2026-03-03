package com.checkout.identities.faceauthentications.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Base class for all identity response objects containing common fields
 *
 * @param <T> The status enum type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseIdentityResponse<T extends Enum<T>> extends Resource {

    /**
     * The unique identifier.
     */
    private String id;

    /**
     * The date and time when the resource was created, in UTC.
     */
    @SerializedName("created_on")
    private Instant createdOn;

    /**
     * The date and time when the resource was modified, in UTC.
     */
    @SerializedName("modified_on")
    private Instant modifiedOn;

    /**
     * The status.
     */
    private T status;

    /**
     * One or more response codes that provide more information about the status.
     */
    @SerializedName("response_codes")
    private List<ResponseCode> responseCodes;

}