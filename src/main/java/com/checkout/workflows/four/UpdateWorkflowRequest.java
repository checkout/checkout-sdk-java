package com.checkout.workflows.four;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class UpdateWorkflowRequest {

    private String name;

}
