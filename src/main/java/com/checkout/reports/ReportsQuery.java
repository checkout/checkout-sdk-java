package com.checkout.reports;

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

    private Instant createdAfter;

    private Instant createdBefore;

    private String entityId;

    @Size(min = 1, max = 100)
    private Integer limit;

    private String paginationToken;

}
