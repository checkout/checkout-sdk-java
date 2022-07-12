package com.checkout.sources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SepaSourceResponse extends SourceResponse {

    private ResponseData responseData;
}
