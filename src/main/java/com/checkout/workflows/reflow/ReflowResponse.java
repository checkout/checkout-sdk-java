package com.checkout.workflows.reflow;

import com.checkout.EmptyResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @deprecated Instead use {@link EmptyResponse}
 * This class will be removed in a future version.
 */
@Data
@Deprecated
@EqualsAndHashCode(callSuper = true)
public final class ReflowResponse extends EmptyResponse {
}
