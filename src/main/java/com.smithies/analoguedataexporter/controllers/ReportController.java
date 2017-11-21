package com.smithies.analoguedataexporter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="reports")
public class ReportController {

    @GetMapping()
    public String getReports(Model model)
    {
        return "reports/index";
    }

    @GetMapping(path="raw-analogue-events")
    public String getRawDigitalEvents(Model model)
    {
        return "reports/raw-analogue-events";
    }
}
