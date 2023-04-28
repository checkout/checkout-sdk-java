package com.checkout.issuing.controls.responses.create;

import com.checkout.HttpMetadata;
import com.checkout.issuing.controls.requests.ControlType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractCardControlResponse extends HttpMetadata {

    private final ControlType type;

    private String id;

    private String description;

    @SerializedName("target_id")
    private String targetId;

    @SerializedName("created_date")
    private Instant createdDate;

    @SerializedName("last_modified_date")
    private Instant lastModifiedDate;

    protected AbstractCardControlResponse(final ControlType type) {
        this.type = type;
    }

}
