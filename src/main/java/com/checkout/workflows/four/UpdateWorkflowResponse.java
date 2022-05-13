package com.checkout.workflows.four;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class UpdateWorkflowResponse extends HttpMetadata {

    private String name;

    private Boolean active;

}
