package com.smartlights.service;

import com.smartlights.model.LightStatusLog;
import com.smartlights.repository.LightLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LightLogRepository lightLogRepository;

    public LogService(LightLogRepository lightLogRepository) {
        this.lightLogRepository = lightLogRepository;
    }

    public LightStatusLog save(String lightId, String event, String state, Long durationSeconds) {
        LightStatusLog log = new LightStatusLog();
        log.setLightId(lightId);
        log.setEvent(event);
        log.setState(state);
        log.setDurationSeconds(durationSeconds);
        log.setTimestamp(LocalDateTime.now());

        return lightLogRepository.save(log);
    }

    public List<LightStatusLog> getLast10Logs(String lightId) {
        return lightLogRepository.findTop10ByLightIdOrderByTimestampDesc(lightId);
    }

    public Optional<LightStatusLog> getLatestLog(String lightId) {
        return lightLogRepository.findTopByLightIdOrderByTimestampDesc(lightId);
    }
}
