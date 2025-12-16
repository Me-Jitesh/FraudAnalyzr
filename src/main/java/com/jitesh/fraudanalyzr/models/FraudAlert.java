package com.jitesh.fraudanalyzr.models;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FraudAlert {
    private String accountId;
    private String transactionId;
    private String reason;
    private Date detectedAt;
}
