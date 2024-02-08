package com.nighthawk.spring_portfolio.nbapredictor.monte_carlo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MonteCarloController {

    @PostMapping("/simulate")
    public Map<String, Double[]> simulateFantasyPoints(@RequestBody String jsonData) {
        MonteCarloSimulator simulator = new MonteCarloSimulator();
        return simulator.simulateFantasyPoints(jsonData);
    }
}
