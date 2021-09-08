package com.checkout.workflows.beta.conditions;

import com.google.gson.annotations.SerializedName;

public enum WorkflowConditionType {

    @SerializedName("event")
    EVENT,
    @SerializedName("entity")
    ENTITY

}
