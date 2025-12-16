package com.jitesh.fraudanalyzr.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamStatus {
    private String status;
    private long processedEvents;
    private Date lastUpdated;
}
