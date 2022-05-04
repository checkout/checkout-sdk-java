package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestGiropaySource extends AbstractRequestSource {

    private String purpose;

    private String bic;

    @SerializedName("info_fields")
    private List<InfoFields> infoFields;

    @Builder
    private RequestGiropaySource(final String purpose,
                                 final String bic,
                                 final List<InfoFields> infoFields) {
        super(PaymentSourceType.GIROPAY);
        this.purpose = purpose;
        this.bic = bic;
        this.infoFields = infoFields;
    }

    public RequestGiropaySource() {
        super(PaymentSourceType.GIROPAY);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoFields {
        private String label;
        private String text;
    }

}