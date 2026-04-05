package com.smartlights.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "light_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightStatusLog {

    @Id
    private String id;

    private String lightId;
    private String event;            // ACTIVATED, DEACTIVATED, PAUSED, RESUMED
    private String state;            // ON, OFF
    private Long durationSeconds;    // duration since last state
    private LocalDateTime timestamp;
}
