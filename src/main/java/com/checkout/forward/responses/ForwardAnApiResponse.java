package com.checkout.forward.responses;

import com.checkout.HttpMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class ForwardAnApiResponse extends HttpMetadata {

    /**
     * The unique identifier for the forward request (Required)
     */
    private String requestId;

    /**
     * The HTTP response received from the destination, if the forward request completed successfully. Sensitive PCI
     * data will be removed from the response (Optional)
     */
    private DestinationResponse destinationResponse;

}
