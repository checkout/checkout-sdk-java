package com.checkout.workflows.beta.reflow;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ReflowByEventsRequest extends ReflowRequest {

    private List<String> events;

    @Builder
    public ReflowByEventsRequest(final List<String> events, final List<String> workflows) {
        super(workflows);
        this.events = events;
    }

}
