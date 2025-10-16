package com.checkout.accounts;

import com.checkout.common.AbstractFileRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.hc.core5.http.ContentType;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AccountsFileRequest extends AbstractFileRequest {

    private AccountsFilePurpose purpose;

    @Builder
    private AccountsFileRequest(final File file,
                                   final ContentType contentType,
                                   final AccountsFilePurpose purpose) {
        super(file, contentType);
        this.purpose = purpose;
    }

}
