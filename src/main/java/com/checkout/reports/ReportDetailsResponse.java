package com.checkout.reports;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReportDetailsResponse extends Resource {

    private String id;

    @SerializedName("created_on")
    private String createdOn;

    @SerializedName("last_modified_on")
    private String lastModifiedOn;

    private String type;

    private String description;

    private AccountResponse account;

    private List<String> tags;

    private Instant from;

    private Instant to;

    private List<FileResponse> files;

}
