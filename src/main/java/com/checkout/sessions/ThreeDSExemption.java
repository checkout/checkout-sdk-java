package com.checkout.sessions;

import com.checkout.common.Exemption;
import lombok.Data;

@Data
public final class ThreeDSExemption {

    private String requested;

    private Exemption applied;

    private String code;
}
