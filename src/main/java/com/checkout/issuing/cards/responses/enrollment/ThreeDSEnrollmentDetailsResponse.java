package com.checkout.issuing.cards.responses.enrollment;

import com.checkout.common.Phone;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ThreeDSEnrollmentDetailsResponse extends Resource {

    private String locale;

    @SerializedName("phone_number")
    private Phone phoneNumber;

    @SerializedName("created_date")
    private Instant createdDate;

    @SerializedName("last_modified_date")
    private Instant lastModifiedDate;
}
