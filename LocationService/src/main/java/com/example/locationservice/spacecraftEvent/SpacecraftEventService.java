package com.example.locationservice.spacecraftEvent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpacecraftEventService {

    private static Logger LOGGER = LoggerFactory.getLogger(SpacecraftEventService.class);
    private List<Latitude> listOfLatitutes;
    private  List<Longitude> listOfLongitudes;
    private  List<SpacecraftEvent> listOfEvents;

    private List<CombinedEventData> listOfCombineData;

    private static final String LATITUDE_FILENAME="/files/latitudes.json";
    private static final String LONGITUDE_FILENAME="/files/longitudes.json";
    private static final String EVENT_FILENAME="/files/events.json";

    public List<CombinedEventData> combineData()  {
        return listOfCombineData;
    }

    public CombinedEventData getEventData(String id) {
        return listOfCombineData.stream()
                .filter(data-> data.getSpacecraftEvent().getId().equals(id))
                .findFirst().get();
    }

    @PostConstruct
    private void fetchAllData()  {
        loadAllJsonFiles();

        listOfCombineData =listOfEvents.stream()
                .map(event -> {
                    Latitude closestLatitude = findClosestLatitude(listOfLatitutes, event.getOccurrenceTime());
                    Longitude closestLongitude = findClosestLongitude(listOfLongitudes, event.getOccurrenceTime());

                    return new CombinedEventData( closestLatitude, closestLongitude, event);
                })
                .collect(Collectors.toList());
    }

    private void loadAllJsonFiles(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            listOfLatitutes = mapper.readValue(getClass().getResourceAsStream(LATITUDE_FILENAME), new TypeReference<>() {
            });
            listOfLongitudes = mapper.readValue(getClass().getResourceAsStream(LONGITUDE_FILENAME), new TypeReference<>() {
            });
            listOfEvents = mapper.readValue(getClass().getResourceAsStream(EVENT_FILENAME), new TypeReference<>() {
            });
        }
        catch (IOException ex){
            LOGGER.error("File not found");
        }
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
