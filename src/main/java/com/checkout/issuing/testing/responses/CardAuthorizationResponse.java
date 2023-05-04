package com.checkout.issuing.testing.responses;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardAuthorizationResponse extends HttpMetadata {

    private String id;

    private TransactionStatus status;
}
