package com.example.locationservice.spacecraftEvent;

import lombok.Getter;

@Getter
public class CombinedEventData {

    private final Latitude latitude;
    private final Longitude longitude;
    private final SpacecraftEvent spacecraftEvent;

    public CombinedEventData(Latitude latitude, Longitude longitude, SpacecraftEvent spacecraftEvent) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.spacecraftEvent = spacecraftEvent;
    }
}
