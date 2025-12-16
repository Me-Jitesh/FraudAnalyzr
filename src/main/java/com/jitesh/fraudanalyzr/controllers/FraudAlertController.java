package com.jitesh.fraudanalyzr.controllers;

import com.jitesh.fraudanalyzr.models.FraudAlert;
import com.jitesh.fraudanalyzr.services.FraudAlertServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fraud")
@CrossOrigin("*")
public class FraudAlertController {

    @Autowired
    private FraudAlertServiceImpl fraudAlertService;

    @GetMapping("/alerts")
    public List<FraudAlert> getFraudAlerts() {
        return fraudAlertService.getAlerts();
    }
}
