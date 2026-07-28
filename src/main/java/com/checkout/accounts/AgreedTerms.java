package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AgreedTerms {

    private String date;

    private String ipAddress;

    private String name;

    private String email;

    private String version;

}
