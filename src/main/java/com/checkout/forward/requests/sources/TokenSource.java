package com.checkout.forward.requests.sources;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TokenSource extends AbstractSource {

    /**
     * The unique Checkout.com token (Required, pattern ^(tok)_(\w{26})$)
     */
    private String token;

    /**
     * Initializes a new instance of the TokenSource class.
     */
    @Builder
    private TokenSource(String token) {
        super(SourceType.TOKEN);
        this.token = token;
    }

    public TokenSource() {
        super(SourceType.TOKEN);
    }

}
