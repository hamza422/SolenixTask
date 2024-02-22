package com.example.locationservice.spacecraftEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path="/api/spacecraftEvent")
public class spacecraftEventController {
    private final spacecraftEventService spacecraftEventService;

    @Autowired
    public spacecraftEventController(spacecraftEventService spacecraftEventService) throws IOException {
        this.spacecraftEventService = spacecraftEventService;
    }
}
