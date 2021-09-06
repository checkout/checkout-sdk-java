package com.checkout.risk.preauthentication;

import lombok.Data;

import java.util.List;

@Data
public final class PreAuthenticationWarning {

    private PreAuthenticationDecision decision;

    private List<String> reasons;

}
