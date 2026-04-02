package com.project.sleepfatigue.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.sleepfatigue.service.DataService;
import com.project.sleepfatigue.service.FatigueService;

import java.util.*;

@RestController
@RequestMapping("/fatigue")
public class FatigueController {
     @Autowired
    private DataService dataService;

    @Autowired
    private FatigueService fatigueService;

    private List<Map<String, Object>> history = new ArrayList<>();

    @GetMapping("/live")
    public Map<String, Object> getFatigue() {

        Map<String, Object> data = dataService.getRandomData();

        double sleep = (double) data.get("sleep");
        double activityRaw = (double) data.get("activity");
        int heart = (int) Math.round((double) data.get("heart"));

        // Convert activity score → category
        String activityLevel;
        if (activityRaw < 40) activityLevel = "Low";
        else if (activityRaw < 70) activityLevel = "Medium";
        else activityLevel = "High";

        int score = fatigueService.calculate(sleep, heart, activityLevel);
        String recommendation = fatigueService.getRecommendation(score);

        Map<String, Object> current = new HashMap<>();
        current.put("sleep", sleep);
        current.put("heart", heart);
        current.put("activity", activityLevel);
        current.put("fatigue_score", score);
        current.put("recommendation", recommendation);

        // Store history (last 10)
        history.add(current);
        if (history.size() > 10) history.remove(0);

        Map<String, Object> response = new HashMap<>();
        response.put("current", current);
        response.put("history", history);

        return response;
}
}
