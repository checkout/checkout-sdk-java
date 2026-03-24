package com.checkout.networktokens.requests;

import com.checkout.networktokens.entities.TransactionType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RequestCryptogramRequest {

    private TransactionType transactionType;

}