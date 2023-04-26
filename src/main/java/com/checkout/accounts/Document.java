package com.checkout.accounts;

import com.checkout.common.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Document {

    private DocumentType type;

    private String front;

    private String back;

}
