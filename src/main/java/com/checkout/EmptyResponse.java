package com.checkout;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmptyResponse extends HttpMetadata {
}
