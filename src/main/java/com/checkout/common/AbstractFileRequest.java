package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.entity.ContentType;

import java.io.File;

@Data
@AllArgsConstructor
public abstract class AbstractFileRequest {

    private File file;

    /**
     * For PDF we need to create manually ContentType.create("application/pdf"),
     * for others you can use ContentType statics
     */
    private ContentType contentType;

}
