package com.smithies.analoguedataexporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="analogue-events")
public class AnalogueEventController {

    @Autowired
    private EventsController eventsController;

    @GetMapping()
    public String index(Model model)
    {
        model.addAttribute("events", eventsController.getEvents());
        return "analogue_events/index";
    }
}
