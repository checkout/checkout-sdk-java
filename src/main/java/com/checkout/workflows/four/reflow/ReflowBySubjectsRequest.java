package com.checkout.workflows.four.reflow;


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
public final class ReflowBySubjectsRequest extends ReflowRequest {

    private List<String> subjects;

    @Builder
    public ReflowBySubjectsRequest(final List<String> subjects, final List<String> workflows) {
        super(workflows);
        this.subjects = subjects;
    }

}
