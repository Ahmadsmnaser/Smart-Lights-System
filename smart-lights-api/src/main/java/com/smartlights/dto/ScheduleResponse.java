package com.smartlights.dto;

import java.util.List;

public class ScheduleResponse {
    private Integer intervalSeconds;
    private List<ScheduleRuleDTO> rules;

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public List<ScheduleRuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<ScheduleRuleDTO> rules) {
        this.rules = rules;
    }
}
