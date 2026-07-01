package com.checkout.disputes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class EvidenceBundle {

    private Long disputeId;

    private String filename;

    private Long fileSize;

    private Boolean isFileOversized;

    private Instant createdAt;

    private Instant modifiedAt;

}
