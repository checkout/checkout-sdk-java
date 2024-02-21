package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardSubEntityDocuments {

    private Document identityVerification;

    private CompanyVerification companyVerification;

    private TaxVerification taxVerification;
}
