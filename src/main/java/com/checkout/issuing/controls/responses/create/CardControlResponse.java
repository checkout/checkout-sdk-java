package com.checkout.issuing.controls.responses.create;

import com.checkout.HttpMetadata;
import com.checkout.issuing.controls.requests.ControlType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class CardControlResponse extends HttpMetadata {

    private final ControlType controlType;

    private String id;

    private String description;

    private String targetId;

    private Instant createdDate;

    private Instant lastModifiedDate;

    protected CardControlResponse(final ControlType controlType) {
        this.controlType = controlType;
    }

}
