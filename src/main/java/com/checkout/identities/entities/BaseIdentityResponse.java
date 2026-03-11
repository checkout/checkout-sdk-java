package com.checkout.identities.entities;

import com.checkout.common.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Base class for all identity response objects containing common fields
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseIdentityResponse extends Resource {

    /**
     * The unique identifier.
     */
    private String id;

    /**
     * The date and time when the resource was created, in UTC.
     */
    private Instant createdOn;

    /**
     * The date and time when the resource was modified, in UTC.
     */
    private Instant modifiedOn;

}