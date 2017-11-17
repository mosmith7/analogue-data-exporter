package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import com.smithies.analoguedataexporter.repositories.EventAnalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/controller")
public class MainController {

    @Autowired
    private EventAnalogueRepository eventAnalogueRepository;

    @GetMapping(path="/{id}")
    public @ResponseBody
    AnalogueEvent getEvent(@PathVariable("id") final Integer id) {
        AnalogueEvent analogueEvent = eventAnalogueRepository.findOne(id);
        return analogueEvent;
    }
}
