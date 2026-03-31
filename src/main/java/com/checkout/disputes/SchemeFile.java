package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class SchemeFile {

    @SerializedName("dispute_status")
    private String disputeStatus;

    private String file;
}
