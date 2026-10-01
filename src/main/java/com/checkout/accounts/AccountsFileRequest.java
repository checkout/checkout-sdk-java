package com.checkout.accounts;

import com.checkout.common.AbstractFileRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.entity.ContentType;

import java.io.File;

/**
 * A file to upload with {@link AccountsClient#submitFile(AccountsFileRequest)} (POST /files on the
 * Files host), sent as a multipart request. The returned ID is what document {@code front} and
 * {@code back} fields take.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AccountsFileRequest extends AbstractFileRequest {

    /**
     * The purpose of the file upload: the onboarding document the file is for.
     * [Required]
     */
    private AccountsFilePurpose purpose;

    /**
     * Creates a file upload request.
     *
     * @param file        the file to upload (JPEG, PNG or PDF)
     * @param contentType the file's content type; for PDF use
     *                    {@code ContentType.create("application/pdf")}
     * @param purpose     the purpose of the file upload
     */
    @Builder
    private AccountsFileRequest(final File file,
                                   final ContentType contentType,
                                   final AccountsFilePurpose purpose) {
        super(file, contentType);
        this.purpose = purpose;
    }

}
