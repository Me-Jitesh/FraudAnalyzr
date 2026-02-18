package com.jitesh.fraudanalyzr.services;

import com.jitesh.fraudanalyzr.dto.StreamStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StreamStatusServiceImpl {

    private final AtomicLong processedCount = new AtomicLong();

    public void incrementProcessed() {
        processedCount.incrementAndGet();
    }

    public long getProcessedCount() {
        return processedCount.get();
    }

    public StreamStatus getStatus() {
        return StreamStatus.builder()
                .status("RUNNING")
                .processedEvents(getProcessedCount())
                .lastUpdated(new Date())
                .build();
    }
}
