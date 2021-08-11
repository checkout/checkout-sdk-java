package com.checkout.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public final class IdResponse extends Resource {

    private String id;
}
