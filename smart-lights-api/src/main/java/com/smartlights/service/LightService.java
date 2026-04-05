package com.smartlights.service;

import com.smartlights.entity.Light;
import com.smartlights.model.LightStatusLog;
import com.smartlights.repository.LightRepository;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LightService {

    private final LightRepository lightRepository;
    private final LogService logService;
    private final Scheduler scheduler;

    private final Map<String, Integer> intervals = new HashMap<>();

    // in-memory schedule rules per light
    private final Map<String, List<com.smartlights.dto.ScheduleRuleDTO>> rules = new HashMap<>();
    // simple incremental id generator for each light
    private final Map<String, Integer> nextRuleId = new HashMap<>();

    public LightService(LightRepository lightRepository, LogService logService, Scheduler scheduler) {
        this.lightRepository = lightRepository;
        this.logService = logService;
        this.scheduler = scheduler;
    }

    public List<Light> getAllLights() {
        return lightRepository.findAll();
    }

    public Light getLightById(String id) {
        return lightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Light not found with id: " + id));
    }

    public Light pauseLight(String id) {
        Light light = getLightById(id);
        light.setPaused(true);
        Light savedLight = lightRepository.save(light);

        String state = light.isActive() ? "ON" : "OFF";
        logService.save(id, "PAUSED", state, 0L);

        return savedLight;
    }

    public Light resumeLight(String id) {
        Light light = getLightById(id);
        light.setPaused(false);
        Light savedLight = lightRepository.save(light);

        String state = light.isActive() ? "ON" : "OFF";
        logService.save(id, "RESUMED", state, 0L);

        return savedLight;
    }

    public Light toggleAndLog(String id) {
        Light light = getLightById(id);

        if (light.isPaused()) {
            return light;
        }

        boolean newState = !light.isActive();
        light.setActive(newState);
        Light savedLight = lightRepository.save(light);

        String event = newState ? "ACTIVATED" : "DEACTIVATED";
        String state = newState ? "ON" : "OFF";

        long durationSeconds = 0L;
        Optional<LightStatusLog> latestLog = logService.getLatestLog(id);

        if (latestLog.isPresent()) {
            LocalDateTime previousTimestamp = latestLog.get().getTimestamp();
            durationSeconds = Duration.between(previousTimestamp, LocalDateTime.now()).getSeconds();
        }

        logService.save(id, event, state, durationSeconds);

        System.out.println("[Quartz] Light " + id + " " + event + " at " + LocalDateTime.now()
                + " (duration " + durationSeconds + "s)");

        return savedLight;
    }

    public void setInitialInterval(String id, int intervalSeconds) {
        intervals.put(id, intervalSeconds);
    }

    public void addScheduleRule(String id, com.smartlights.dto.ScheduleRuleDTO rule) {
        int rid = nextRuleId.getOrDefault(id, 1);
        rule.setId(rid);
        nextRuleId.put(id, rid + 1);

        rules.computeIfAbsent(id, k -> new java.util.ArrayList<>()).add(rule);
    }

    public List<com.smartlights.dto.ScheduleRuleDTO> getScheduleRules(String id) {
        return rules.getOrDefault(id, java.util.Collections.emptyList());
    }

    public void deleteScheduleRule(String id, int ruleId) {
        List<com.smartlights.dto.ScheduleRuleDTO> list = rules.get(id);
        if (list != null) {
            list.removeIf(r -> r.getId() == ruleId);
        }
    }

    public int getCurrentInterval(String id) {
        return intervals.getOrDefault(id, 15);
    }

    public void updateSchedule(String id, int intervalSeconds) {
        try {
            intervals.put(id, intervalSeconds);

            TriggerKey triggerKey = TriggerKey.triggerKey("trigger-" + id);

            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(intervalSeconds)
                            .repeatForever())
                    .build();

            scheduler.rescheduleJob(triggerKey, newTrigger);

            String state = getLightById(id).isActive() ? "ON" : "OFF";
            logService.save(id, "SCHEDULE_UPDATED", state, 0L);

            System.out.println("Updated schedule for " + id + " to every " + intervalSeconds + " seconds");
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to update schedule for light " + id, e);
        }
    }
}