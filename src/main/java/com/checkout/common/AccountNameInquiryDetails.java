package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountNameInquiryDetails {

    private NameCheckType firstName;
    private NameCheckType middleName;
    private NameCheckType lastName;
}
