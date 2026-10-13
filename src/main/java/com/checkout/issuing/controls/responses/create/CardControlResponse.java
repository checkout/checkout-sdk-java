package com.checkout.issuing.controls.responses.create;

import com.checkout.HttpMetadata;
import com.checkout.issuing.controls.requests.ControlType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * Base card control response, discriminated on {@code control_type}. Returned by create, get, list and update
 * control operations; see {@link VelocityCardControlResponse}, {@link MccCardControlResponse} and
 * {@link MidCardControlResponse}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class CardControlResponse extends HttpMetadata {

    /**
     * The control's type. A velocity_limit determines how much can be spent over a given period of time.
     * An mcc_limit determines the types of businesses from which transactions can be processed.
     * A mid_limit specifies the merchants from whom transactions can be processed.
     * [Required]
     * Enum: "velocity_limit", "mcc_limit", "mid_limit"
     */
    private final ControlType controlType;

    /**
     * The control's unique identifier.
     * [Required]
     * 30 characters, ^ctr_[a-z0-9]{26}$
     */
    private String id;

    /**
     * A description for the control.
     * [Optional]
     * max 256 characters
     */
    private String description;

    /**
     * The ID of the card or control profile.
     * [Required]
     * 30 characters, ^(crd|cpr)_[a-z0-9]{26}$
     */
    private String targetId;

    /**
     * Indicates whether you can change this control. false: an immutable control applied by Checkout.com.
     * true: you applied this control and can change it.
     * [Required]
     */
    private Boolean isEditable;

    /**
     * The date and time the control was created, in UTC.
     * [Required]
     * format: date-time (UTC)
     */
    private Instant createdDate;

    /**
     * The date and time the control was last modified, in UTC.
     * [Required]
     * format: date-time (UTC)
     */
    private Instant lastModifiedDate;

    protected CardControlResponse(final ControlType controlType) {
        this.controlType = controlType;
    }

}
