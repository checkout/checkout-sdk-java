package com.checkout.networkTokens.requests;

import com.checkout.networkTokens.entities.TransactionType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCryptogramRequest {

    private TransactionType transactionType;

}