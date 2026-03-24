package com.checkout.accounts.files.response;

import com.checkout.common.Resource;
import com.checkout.accounts.files.entities.FilePurpose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class FileDetailsResponse extends Resource {

    private String id;

    private String status;

    private List<String> statusReasons;

    private Long size;

    private String mimeType;

    private Instant uploadedOn;

    private FilePurpose purpose;
}