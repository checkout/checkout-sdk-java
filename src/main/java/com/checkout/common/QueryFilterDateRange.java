package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class QueryFilterDateRange {

    private String from;

    private String to;

}
