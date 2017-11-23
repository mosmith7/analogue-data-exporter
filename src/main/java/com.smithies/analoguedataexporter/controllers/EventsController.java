package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import com.smithies.analoguedataexporter.repositories.AnalogueEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path="/events")
public class EventsController {

    @Autowired
    private AnalogueEventRepository analogueEventRepository;

    @GetMapping(path="/{id}")
    public @ResponseBody
    AnalogueEvent getEvent(@PathVariable("id") final Integer id) {
        AnalogueEvent analogueEvent = analogueEventRepository.findOne(id);
        return analogueEvent;
    }

    @GetMapping(path="")
    public @ResponseBody
    Iterable<AnalogueEvent> getEvents() {
        List<Integer> eventIds = Arrays.asList(685144355, 685144356, 685144353);
        return analogueEventRepository.findAll(eventIds);
    }

}
