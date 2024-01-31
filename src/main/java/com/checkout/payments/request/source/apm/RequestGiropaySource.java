package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolder;
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

    /**
     * @deprecated GiroPay doesn't support this field anymore, will be removed in the future
     */
    @Deprecated
    private String purpose;

    /**
     * @deprecated GiroPay doesn't support this field anymore, will be removed in the future
     */
    @Deprecated
    @SerializedName("info_fields")
    private List<InfoFields> infoFields;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestGiropaySource(final String purpose,
                                 final List<InfoFields> infoFields,
                                 final AccountHolder accountHolder) {
        super(PaymentSourceType.GIROPAY);
        this.purpose = purpose;
        this.infoFields = infoFields;
        this.accountHolder = accountHolder;
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
