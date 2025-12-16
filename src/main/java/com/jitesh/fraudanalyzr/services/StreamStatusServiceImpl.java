package com.jitesh.fraudanalyzr.services;

import org.springframework.stereotype.Service;

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
}
