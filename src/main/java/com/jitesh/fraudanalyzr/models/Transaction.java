package com.jitesh.fraudanalyzr.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private String accountId;
    private double amount;
    private String merchant;
    private Date timestamp;
}