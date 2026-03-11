package com.checkout.identities.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base class for all identity response objects containing common fields
 *
 * @param <T> The status enum type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseIdentityResponseStatus<T extends Enum<T>> extends BaseIdentityResponse {
    /**
     * The status.
     */
    private T status;

    /**
     * One or more response codes that provide more information about the status.
     */
    private List<ResponseCode> responseCodes;
}