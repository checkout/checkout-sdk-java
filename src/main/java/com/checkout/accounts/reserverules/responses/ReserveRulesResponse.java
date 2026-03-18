package com.checkout.accounts.reserverules.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.checkout.common.Resource;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReserveRulesResponse extends Resource {

    private List<ReserveRuleResponse> data;

}