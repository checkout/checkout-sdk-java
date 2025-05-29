package com.checkout.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AccountHolderResponse extends AccountHolderBase {

    private AccountNameInquiryType accountNameInquiry;

    private AccountNameInquiryDetails accountNameInquiryDetails;

}
