package com.checkout.issuing.transactions.entities;

import lombok.Data;

@Data
public class DigitalCard {

    private String id;

    private WalletType walletType;    
}