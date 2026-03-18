package com.checkout.payments.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
public final class PaymentSearchRequest {

    /**
     * The query string.
     * For more information on how to build out your query, see the Search and filter payments documentation.
     */
    @Size(max = 1024)
    private String query;

    /**
     * The number of results to return per page.
     */
    @Min(1)
    @Max(1000)
    private Integer limit;

    /**
     * The UTC date and time for the query start in ISO 8601 format. 
     * Required if to is provided.
     */
    private Instant from;

    /**
     * The UTC date and time for the query end in ISO 8601 format. 
     * Required if from is provided.
     */
    private Instant to;
}