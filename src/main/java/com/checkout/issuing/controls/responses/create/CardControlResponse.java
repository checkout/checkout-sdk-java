package com.checkout.issuing.controls.responses.create;

import com.checkout.HttpMetadata;
import com.checkout.issuing.controls.requests.ControlType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class CardControlResponse extends HttpMetadata {

    @SerializedName("control_type")
    private final ControlType controlType;

    private String id;

    private String description;

    @SerializedName("target_id")
    private String targetId;

    @SerializedName("created_date")
    private Instant createdDate;

    @SerializedName("last_modified_date")
    private Instant lastModifiedDate;

    protected CardControlResponse(final ControlType controlType) {
        this.controlType = controlType;
    }

}
