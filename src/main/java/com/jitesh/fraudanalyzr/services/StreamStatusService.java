package com.jitesh.fraudanalyzr.services;

import com.jitesh.fraudanalyzr.dto.StreamStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StreamStatusService {

    private final AtomicLong processedEvents = new AtomicLong();

    public void increment() {
        processedEvents.incrementAndGet();
    }

    public StreamStatus getStatus() {
        return StreamStatus.builder()
                .status("RUNNING")
                .processedEvents(processedEvents.get())
                .lastUpdated(new Date())
                .build();
    }
}
