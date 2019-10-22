package com.checkout.tokens;

import com.checkout.common.Resource;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TokenResponse extends Resource {
    @Getter
    private String type;
    @Getter
    private String token;
    @Getter
    private Instant expiresOn;
}