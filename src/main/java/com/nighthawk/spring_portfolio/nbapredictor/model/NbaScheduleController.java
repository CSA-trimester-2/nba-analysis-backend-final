package com.nighthawk.spring_portfolio.nbapredictor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/nba/schedule")
public class NbaScheduleController {

    @Autowired
    private NbaScheduleService nbaScheduleService;

    @GetMapping("/{season}")
    public ResponseEntity<List<NbaGame>> getScheduleBySeason(@PathVariable String season) {
        List<NbaGame> schedule = nbaScheduleService.getNbaSchedule(season);
        return ResponseEntity.ok(schedule);
    }
    
}
