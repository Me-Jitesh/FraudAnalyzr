package com.jitesh.fraudanalyzr.controllers;


import com.jitesh.fraudanalyzr.dto.StreamStatus;
import com.jitesh.fraudanalyzr.services.StreamStatusServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stream")
@CrossOrigin("*")
public class StreamStatusController {

    private final StreamStatusServiceImpl streamStatusService;

    public StreamStatusController(StreamStatusServiceImpl streamStatusService) {
        this.streamStatusService = streamStatusService;
    }

    @GetMapping("/status")
    public StreamStatus getStreamStatus() {
        return streamStatusService.getStatus();
    }
}
