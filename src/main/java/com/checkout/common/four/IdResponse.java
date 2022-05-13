package com.checkout.common.four;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class IdResponse extends HttpMetadata {

    private String id;

}
