package com.checkout.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public final class FileDetailsResponse extends Resource {

    private String id;
    private String filename;
    private String purpose;
    private Integer size;
    private String uploadedOn;

}
