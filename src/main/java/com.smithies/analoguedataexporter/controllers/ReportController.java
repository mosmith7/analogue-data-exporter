package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.repositories.ChannelRepository;
import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(path="reports")
public class ReportController {

    @Autowired
    private InterlockingRepository siteRepo;

    @GetMapping()
    public String getReports(Model model)
    {
        return "reports/index";
    }

    @GetMapping(path="raw-analogue-events")
    public String getRawAnalogueEvents(Model model)
    {
        model.addAttribute("sites", siteRepo.findAll());
        return "reports/analogue-index";
    }

    @PostMapping(path="raw-analogue-events")
    public String preFillParameters(@ModelAttribute("site") Short site, @ModelAttribute("channel") Integer channel,
                                    @ModelAttribute("from") String from, @ModelAttribute("to") String to, Model model) {
        model.addAttribute("sites", siteRepo.findAll());
        return "reports/analogue-index";
    }

    @PostMapping(path="raw-analogue-events/parameters")
    public String generateReport(@ModelAttribute("site") Short siteId, @ModelAttribute("channel") Integer channelId,
                                 @ModelAttribute("from") String from, @ModelAttribute("to") String to, Model model) {
        // Redirect to report results page. The site and channel values will stay attached to the model.
        // But the dates should be converted from strings
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFrom = format.parse(from);
            Date dateTo = format.parse(to);
            model.addAttribute("dateFrom", dateFrom);
            model.addAttribute("dateTo", dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "reports/analogue_results";
    }
}
