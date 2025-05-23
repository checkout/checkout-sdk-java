package com.checkout.forward.responses;

import com.checkout.HttpMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class GetForwardResponse extends HttpMetadata {

    /**
     * The unique identifier for the forward request (Required)
     */
    private String requestId;

    /**
     * The client entity linked to the forward request (Required)
     */
    private String entityId;
    /**
     * The parameters of the HTTP request forwarded to the destination (Required)
     */
    private DestinationRequest destinationRequest;

    /**
     * The date and time the forward request was created, in UTC (Required)
     */
    private Instant createdOn;

    /**
     * The unique reference for the forward request (Optional)
     */
    private String reference;

    /**
     * The HTTP response received from the destination. Sensitive PCI data is not included in the response
     * (Optional)
     */
    private DestinationResponse destinationResponse;

}
