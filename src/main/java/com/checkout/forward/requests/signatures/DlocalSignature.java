package com.checkout.forward.requests.signatures;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class DlocalSignature extends AbstractSignature {

    /**
     * The parameters required to generate an HMAC signature for the dLocal API. See their documentation for details.
     * This method requires you to provide the X-Login header value in the destination request headers.
     * When used, the Forward API appends the X-Date and Authorization headers to the outgoing HTTP request before
     * forwarding.
     */
    private DlocalParameters dlocalParameters;

    /**
     * Initializes a new instance of the IdSource class.
     */
    @Builder
    private DlocalSignature(DlocalParameters dlocalParameters) {
        super(SignatureType.DLOCAL);
        this.dlocalParameters = dlocalParameters;
    }

    public DlocalSignature() {
        super(SignatureType.DLOCAL);
    }

}
