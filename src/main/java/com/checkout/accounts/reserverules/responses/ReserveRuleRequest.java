package com.checkout.accounts.reserverules.responses;

import com.checkout.accounts.Headers;
import com.checkout.accounts.reserverules.entities.RollingReserveRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReserveRuleRequest extends Headers {

    private String type;

    private RollingReserveRule rolling;

    private Instant validFrom;

}