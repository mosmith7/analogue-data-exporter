package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import com.smithies.analoguedataexporter.services.IAnalogueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(path="reports")
public class ReportController {

    @Autowired
    private InterlockingRepository siteRepo;

    @Autowired
    private IAnalogueReportService analogueReportService;

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
            // Save parameters to DB with a specific ID (linked to user ID) and add the ID to the model attributes.
            Integer id = analogueReportService.saveParameters(siteId, channelId, dateFrom.getTime(), dateTo.getTime());
            model.addAttribute("reportId", id);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Start new thread to start generating reports

        return "reports/analogue_results";
    }

    @GetMapping("/raw-analogue-events/csv/{id}")
    public @ResponseBody void downloadCsv(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        analogueReportService.downloadCsv(id, response);
    }
}
