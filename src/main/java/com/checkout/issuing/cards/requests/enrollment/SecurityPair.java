package com.checkout.issuing.cards.requests.enrollment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityPair {

    private String question;

    private String answer;
}
