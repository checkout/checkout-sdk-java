package com.checkout.reports;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ReportsQuery {

    @SerializedName("created_after")
    private Instant createdAfter;

    @SerializedName("created_before")
    private Instant createdBefore;

    @SerializedName("entity_id")
    private String entityId;

    @Size(min = 1, max = 100)
    private Integer limit;

    @SerializedName("pagination_token")
    private String paginationToken;

}
