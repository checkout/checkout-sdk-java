package com.checkout.disputes;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public final class DisputeCompiledSubmittedEvidenceResponse extends Resource {

    @SerializedName("file_id")
    private String fileId;

}
