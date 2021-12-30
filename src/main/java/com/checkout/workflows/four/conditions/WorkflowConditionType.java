package com.checkout.workflows.four.conditions;

import com.google.gson.annotations.SerializedName;

public enum WorkflowConditionType {

    @SerializedName("event")
    EVENT,
    @SerializedName("entity")
    ENTITY,
    @SerializedName("processing_channel")
    PROCESSING_CHANNEL

}
