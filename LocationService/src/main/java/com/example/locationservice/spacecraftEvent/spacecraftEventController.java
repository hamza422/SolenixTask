package com.example.locationservice.spacecraftEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/api/spacecraftEvent")
public class spacecraftEventController {
    private final spacecraftEventService spacecraftEventService;

    @Autowired
    public spacecraftEventController(spacecraftEventService spacecraftEventService) throws IOException {
        this.spacecraftEventService = spacecraftEventService;
    }

    @GetMapping("/getAllEvents")
    public String getAllElements()  {
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
    public String getEvent(@PathVariable("id") String id)  {
        CombinedEventData data= spacecraftEventService.getEventData(id);

        return data.getSpacecraftEvent().toString()  +
                "<hr>" +
                data.getLatitude().toString() + "&nbsp" + data.getLongitude().toString()+
                "<hr>" +
                "<hr>";
    }
}
