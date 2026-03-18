package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Representment details in dispute response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeRepresentment {

    /**
     * The date and time when you received the representment, in UTC
     */
    private Instant receivedOn;

    /**
     * The representment amount, in the minor currency unit
     */
    private DisputeAmount amount;

    /**
     * The evidence provided by the merchant against the chargeback
     */
    private List<DisputeFileEvidence> evidence;

}