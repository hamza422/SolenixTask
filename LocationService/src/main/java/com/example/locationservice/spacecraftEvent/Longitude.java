package com.example.locationservice.spacecraftEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"timestamp", "position"})
public class Longitude {
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @JsonProperty("position")
    private double position;

    @Override
    public String toString() {
        return "Longitude{" +
                "timestamp=" + timestamp +
                ", position=" + position +
                '}';
    }
}
