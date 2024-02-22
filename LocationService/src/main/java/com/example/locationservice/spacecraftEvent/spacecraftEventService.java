package com.example.locationservice.spacecraftEvent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class spacecraftEventService {
    private List<Latitude> listOfLatitutes;
    private  List<Longitude> listOfLongitudes;
    private  List<SpacecraftEvent> listOfEvents;

    private List<CombinedEventData> listOfCombineData;

    private static final String LATITUDE_FILENAME="src/main/resources/files/latitudes.json";
    private static final String LONGITUDE_FILENAME="src/main/resources/files/longitudes.json";
    private static final String EVENT_FILENAME="src/main/resources/files/events.json";

    public spacecraftEventService() throws IOException {
        fetchAllData();
    }

    private void fetchAllData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        listOfLatitutes = mapper.readValue(new File(LATITUDE_FILENAME), new TypeReference<>(){});
        listOfLongitudes = mapper.readValue(new File(LONGITUDE_FILENAME), new TypeReference<>(){});
        listOfEvents= mapper.readValue(new File(EVENT_FILENAME), new TypeReference<>(){});

        listOfCombineData =listOfEvents.stream()
                .map(event -> {
                    Latitude closestLatitude = findClosestLatitude(listOfLatitutes, event.getOccurrenceTime());
                    Longitude closestLongitude = findClosestLongitude(listOfLongitudes, event.getOccurrenceTime());

                    return new CombinedEventData( closestLatitude, closestLongitude, event);
                })
                .collect(Collectors.toList());
    }

    private Latitude findClosestLatitude(List<Latitude> listOfLatitudes, Timestamp occurrenceTime) {
        Latitude closestLatitude = null;
        long minDifference = Long.MAX_VALUE;

        for (Latitude latitude : listOfLatitudes) {
            long difference = Math.abs(latitude.getTimestamp().getTime()- occurrenceTime.getTime());
            if (difference < minDifference) {
                minDifference = difference;
                closestLatitude = latitude;
            }
        }

        return closestLatitude;
    }

    private Longitude findClosestLongitude(List<Longitude> listOfLongitude, Timestamp occurrenceTime) {
        Longitude closestLogitude = null;
        long minDifference = Long.MAX_VALUE;

        for (Longitude longitude : listOfLongitude) {
            long difference = Math.abs(longitude.getTimestamp().getTime()- occurrenceTime.getTime());
            if (difference < minDifference) {
                minDifference = difference;
                closestLogitude = longitude;
            }
        }
        return closestLogitude;
    }
}
