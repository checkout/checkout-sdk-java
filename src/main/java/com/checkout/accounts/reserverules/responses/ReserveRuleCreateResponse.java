package com.checkout.accounts.reserverules.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.checkout.common.Resource;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ReserveRuleCreateResponse extends Resource {

    private String id;

}