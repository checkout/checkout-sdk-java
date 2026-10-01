package com.checkout.accounts.files.request;


import com.checkout.accounts.files.entities.FilePurpose;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * The request body of POST /entities/{entityId}/files.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FileUploadRequest {

    /**
     * The purpose of the file upload.
     * [Required]
     */
    private FilePurpose purpose;

}
