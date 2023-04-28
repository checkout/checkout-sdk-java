package com.checkout.issuing.controls.responses.query;

import com.checkout.HttpMetadata;
import com.checkout.issuing.controls.responses.create.AbstractCardControlResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardControlsQueryResponse extends HttpMetadata {

    private List<AbstractCardControlResponse> controls;
}
