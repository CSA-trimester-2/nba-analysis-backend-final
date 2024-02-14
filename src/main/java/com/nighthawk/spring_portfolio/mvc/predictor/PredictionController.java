// PredictionController.java
package com.nighthawk.spring_portfolio.mvc.predictor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nba")
public class PredictionController {

    private final PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/predict")
    public ResponseEntity<String> predictMoneyline(@RequestParam int teamIdOne, @RequestParam int teamIdTwo) {
        String prediction = predictionService.predictOutcome(teamIdOne, teamIdTwo);
        return ResponseEntity.ok(prediction);
    }
    
}
