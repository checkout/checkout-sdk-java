package com.checkout.accounts.files.response;

import com.checkout.common.Resource;
import com.checkout.accounts.files.entities.FilePurpose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * The details of a sub-entity's file, as returned by GET /entities/{entityId}/files/{fileId}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class FileDetailsResponse extends Resource {

    /**
     * The ID of the file.
     */
    private String id;

    /**
     * The current status of the file.
     */
    private String status;

    /**
     * If {@code status} is {@code invalid}, the reasons why the file was invalid; otherwise null.
     */
    private List<String> statusReasons;

    /**
     * The size of the file, in KB.
     */
    private Long size;

    /**
     * The MIME type of the file.
     */
    private String mimeType;

    /**
     * The date and time the file was uploaded, in ISO 8601 UTC format.
     * Format: date-time (RFC 3339)
     */
    private Instant uploadedOn;

    /**
     * The purpose of the file, as provided in the initial request. A value {@link FilePurpose} does
     * not define deserializes to null.
     */
    private FilePurpose purpose;

}
