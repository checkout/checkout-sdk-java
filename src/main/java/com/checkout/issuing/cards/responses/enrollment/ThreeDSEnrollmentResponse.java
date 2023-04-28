package com.checkout.issuing.cards.responses.enrollment;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ThreeDSEnrollmentResponse extends Resource {

    @SerializedName("created_date")
    private Instant createdDate;
}
