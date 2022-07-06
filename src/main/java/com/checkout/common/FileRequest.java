package com.checkout.common;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.entity.ContentType;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class FileRequest extends AbstractFileRequest {

    @NonNull
    private FilePurpose purpose;

    @Builder
    private FileRequest(final File file,
                        final ContentType contentType,
                        @NonNull final FilePurpose purpose) {
        super(file, contentType);
        this.purpose = purpose;
    }

}
