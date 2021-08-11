package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.apache.http.entity.ContentType;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
public class FileRequest {

    @NonNull
    private File file;
    @NonNull
    private FilePurpose purpose;
    /**
     * For PDF we need to create manually ContentType.create("application/pdf"),
     * for others you can use ContentType statics
     */
    private ContentType contentType;

}
