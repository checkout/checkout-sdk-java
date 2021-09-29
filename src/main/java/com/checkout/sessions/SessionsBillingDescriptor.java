package com.checkout.sessions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class SessionsBillingDescriptor {

    private String name;

}
