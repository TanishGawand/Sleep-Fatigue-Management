package com.project.sleepfatigue.service;
import org.springframework.stereotype.Service;

@Service
public class FatigueService {
     public int calculate(double sleep, int heart, String activityLevel) {
        int score = 0;

        if (sleep < 6) score += 40;
        else if (sleep < 7) score += 20;
        else score += 5;

        if (heart > 90) score += 30;
        else if (heart > 75) score += 15;
        else score += 5;

        if (activityLevel.equals("Low")) score += 20;
        else if (activityLevel.equals("Medium")) score += 10;
        else score += 5;

        return Math.min(score, 100);
    }

    public String getRecommendation(int score) {
        if (score > 70) return "High Fatigue";
        else if (score > 40) return "Moderate Fatigue";
        else return "Low Fatigue";
    }
}
