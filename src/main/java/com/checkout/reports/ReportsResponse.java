package com.checkout.reports;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ReportsResponse extends Resource {

    private Integer count;

    private Integer limit;

    private List<ReportDetailsResponse> data;

}
