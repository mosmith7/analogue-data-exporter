package com.smithies.analoguedataexporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping(path="analogue-events")
public class AnalogueEventController {

    @Autowired
    private MainController mainController;

    @GetMapping()
    public String index(Model model)
    {
        model.addAttribute("events", mainController.getEvents());
        return "analogue_events/index";
    }
}
