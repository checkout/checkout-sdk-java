package com.checkout.workflows.beta;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class GetWorkflowsResponse {

    @SerializedName("data")
    private List<Workflow> workflows;

}
