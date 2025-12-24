package com.jitesh.fraudanalyzr.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private String accountId;
    private Double amount;
    private String location;
    private String type;
    private String merchant;
    private List<Item> items;
    private Date timestamp;
}