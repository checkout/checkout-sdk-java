package com.checkout.accounts.reserverules.responses;

import com.checkout.accounts.reserverules.entities.RollingReserveRule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.checkout.common.Resource;
import java.time.Instant;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ReserveRuleResponse extends Resource {

    private String id;

    private String type;

    private Instant validFrom;

    private RollingReserveRule rolling;

}