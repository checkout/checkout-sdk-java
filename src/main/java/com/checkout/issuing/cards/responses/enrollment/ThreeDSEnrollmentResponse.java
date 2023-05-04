package com.checkout.issuing.cards.responses.enrollment;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ThreeDSEnrollmentResponse extends Resource {

    @SerializedName("created_date")
    private Instant createdDate;
}
