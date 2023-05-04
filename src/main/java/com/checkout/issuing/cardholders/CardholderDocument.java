package com.checkout.issuing.cardholders;

import com.checkout.common.DocumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardholderDocument {

    private DocumentType type;

    @SerializedName("front_document_id")
    private String frontDocumentId;

    @SerializedName("back_document_id")
    private String backDocumentId;
}
