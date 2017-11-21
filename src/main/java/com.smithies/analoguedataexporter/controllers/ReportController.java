package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.model.AnalogueParameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(path="reports")
public class ReportController {

    @GetMapping()
    public String getReports(Model model)
    {
        return "reports/index";
    }

    @GetMapping(path="raw-analogue-events")
    public String getRawAnalogueEvents(Model model)
    {
        return "reports/raw-analogue-events";
    }

    @PostMapping(path="raw-analogue-events")
    public void generateReport(@ModelAttribute("site") String site, @ModelAttribute("channel") String channel,
                               @ModelAttribute("from") String from, @ModelAttribute("to") String to) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFrom = format.parse(from);
            Date dateTo = format.parse(to);
            System.out.println(site);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
