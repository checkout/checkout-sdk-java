package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("dispute_id")
    private Long disputeId;

    private String filename;

    @SerializedName("file_size")
    private Long fileSize;

    @SerializedName("is_file_oversized")
    private Boolean isFileOversized;

    @SerializedName("created_at")
    private Instant createdAt;

    @SerializedName("modified_at")
    private Instant modifiedAt;

}
