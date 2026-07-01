package com.checkout.issuing.controls.responses.controlprofile;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ControlProfileResponse extends Resource {

    private String id;

    private String name;

    private Instant createdDate;

    private Instant lastModifiedDate;
}