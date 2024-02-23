package com.example.locationservice.spacecraftEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/spacecraftEvent")
public class SpacecraftEventController {
    private final SpacecraftEventService spacecraftEventService;

    @Autowired
    public SpacecraftEventController(SpacecraftEventService spacecraftEventService) {
        this.spacecraftEventService = spacecraftEventService;
    }

    @GetMapping("/getAllEvents")
    public String getAllElements()   {
        StringBuilder stringBuilder=new StringBuilder();
        List<CombinedEventData> listOfCombineData=spacecraftEventService.combineData();

        for (CombinedEventData data: listOfCombineData){
            stringBuilder.append(data.getSpacecraftEvent().toString());
            stringBuilder.append("<hr>");
            stringBuilder.append(data.getLatitude().toString()).append("&nbsp").append(data.getLongitude().toString());
            stringBuilder.append("<hr>");
            stringBuilder.append("<hr>");
        }

        return stringBuilder.toString();
    }

    @GetMapping("/getEvent/{id}")
    public String getEvent(@PathVariable("id") String id) {
        CombinedEventData data= spacecraftEventService.getEventData(id);

        return data.getSpacecraftEvent().toString()  +
                "<hr>" +
                data.getLatitude().toString() + "&nbsp" + data.getLongitude().toString()+
                "<hr>" +
                "<hr>";
    }
}
