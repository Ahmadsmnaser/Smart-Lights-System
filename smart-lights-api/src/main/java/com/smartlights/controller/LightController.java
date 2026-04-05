package com.smartlights.controller;

import com.smartlights.entity.Light;
import com.smartlights.model.LightStatusLog;
import com.smartlights.service.LightService;
import com.smartlights.service.LogService;
import org.springframework.web.bind.annotation.*;
import com.smartlights.dto.ScheduleRequest;
import com.smartlights.dto.ScheduleResponse;
import com.smartlights.dto.ScheduleRuleDTO;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lights")
@CrossOrigin(origins = "*")
public class LightController {

    private final LightService lightService;
    private final LogService logService;

    public LightController(LightService lightService, LogService logService) {
        this.lightService = lightService;
        this.logService = logService;
    }

    @GetMapping
    public List<Light> getAllLights() {
        return lightService.getAllLights();
    }

    @PutMapping("/{id}/pause")
    public Light pauseLight(@PathVariable String id) {
        return lightService.pauseLight(id);
    }

    @PutMapping("/{id}/resume")
    public Light resumeLight(@PathVariable String id) {
        return lightService.resumeLight(id);
    }

    @PutMapping("/{id}/toggle")
    public Light toggle(@PathVariable String id) {
        return lightService.toggleAndLog(id);
    }

    @GetMapping("/{id}/history")
    public List<LightStatusLog> getHistory(@PathVariable String id) {
        return logService.getLast10Logs(id);
    }

    // existing interval update (kept for backward compatibility)
    @PutMapping("/{id}/schedule")
    public void updateSchedule(@PathVariable String id, @RequestBody ScheduleRequest request) {
        lightService.updateSchedule(id, request.getIntervalSeconds());
    }

    // add a new rule for the light
    @PostMapping("/{id}/schedule")
    public void addScheduleRule(@PathVariable String id, @RequestBody com.smartlights.dto.ScheduleRuleDTO rule) {
        lightService.addScheduleRule(id, rule);
    }

    // remove an existing rule by id
    @DeleteMapping("/{id}/schedule/{ruleId}")
    public void deleteScheduleRule(@PathVariable String id, @PathVariable int ruleId) {
        lightService.deleteScheduleRule(id, ruleId);
    }

    @GetMapping("/{id}/schedule")
    public com.smartlights.dto.ScheduleResponse getSchedule(@PathVariable String id) {
        int interval = lightService.getCurrentInterval(id);
        com.smartlights.dto.ScheduleResponse resp = new com.smartlights.dto.ScheduleResponse();
        resp.setIntervalSeconds(interval);
        resp.setRules(lightService.getScheduleRules(id));
        return resp;
    }
}
