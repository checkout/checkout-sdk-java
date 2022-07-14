package com.checkout.workflows;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class GetWorkflowsResponse extends HttpMetadata {

    @SerializedName("data")
    private List<Workflow> workflows;

}
