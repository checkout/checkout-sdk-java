package com.checkout.disputes;

import com.checkout.common.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public final class DisputeCompiledSubmittedEvidenceResponse extends Resource {

    private String fileId;

}
