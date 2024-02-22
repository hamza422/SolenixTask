package com.example.locationservice.spacecraftEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@JsonPropertyOrder({"occurrence_time", "event_name","id","severity"})
@Getter
public class SpacecraftEvent {
    @JsonProperty("occurrence_time")
    private final Timestamp occurrenceTime;
    @JsonProperty("event_name")
    private final String eventName;
    @JsonProperty("id")
    private final String id;
    @JsonProperty("severity")
    private final String severity;

    @Override
    public String toString() {
        return "Event{" +
                "occurrenceTime=" + occurrenceTime +
                ", eventName='" + eventName + '\'' +
                ", id='" + id + '\'' +
                ", severity='" + severity + '\'' +
                '}';
    }
}
