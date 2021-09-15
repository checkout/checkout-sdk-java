package com.checkout.workflows.four.reflow;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public abstract class ReflowRequest {

    protected List<String> workflows;

    protected ReflowRequest(final List<String> workflows) {
        this.workflows = workflows;
    }

}
