package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response code information for face authentication operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCode {
    
    /**
     * The response code.
     */
    private Integer code;

    /**
     * The description of the response code.
     */
    private String summary;

}