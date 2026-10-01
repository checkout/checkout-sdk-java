package com.checkout.accounts.files.response;

import com.checkout.common.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The response of POST /entities/{entityId}/files: the file ID and the upload link. The file content
 * itself is sent to that link, not in the request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class FileUploadResponse extends Resource {

    /**
     * The file identifier.
     */
    private String id;

    /**
     * The maximum file size allowed, in bytes.
     */
    private Long maximumSizeInBytes;

    /**
     * The MIME file types allowed for the document purpose provided on the initial request.
     */
    private List<String> documentTypesForPurpose;

}
