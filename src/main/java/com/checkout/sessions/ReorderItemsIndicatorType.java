package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum ReorderItemsIndicatorType {
    
    @SerializedName("first_time_ordered")
    FIRST_TIME_ORDERED,
    @SerializedName("reordered")
    REORDERED

}
