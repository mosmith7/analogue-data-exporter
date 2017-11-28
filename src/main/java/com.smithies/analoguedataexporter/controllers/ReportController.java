package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.model.ParametersRequest;
import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import com.smithies.analoguedataexporter.services.IAnalogueReportService;
import com.smithies.analoguedataexporter.valueobjects.RawAnalogueEventsReportParametersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public @ResponseBody Integer parameters(@RequestBody ParametersRequest request, Model model) {
        // Redirect to report results page. The site and channel values will stay attached to the model.
        // But the dates should be converted from strings
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Integer id = null;
        try {
            Date dateFrom = format.parse(request.getFrom());
            Date dateTo = format.parse(request.getTo());
            // Save parameters to DB with a specific ID (linked to user ID) and add the ID to the model attributes.
            id = analogueReportService.saveParameters(request.getSiteId(), request.getChannelId(), dateFrom.getTime(), dateTo.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return id;
    }

    @GetMapping(path="raw-analogue-events/parameters/{id}")
    public String parameters(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("reportId", id);
        RawAnalogueEventsReportParametersVO parameters = analogueReportService.getReport(id);
        model.addAttribute("dateFrom", parameters.getFrom());
        model.addAttribute("dateTo", parameters.getTo());
        model.addAttribute("site", parameters.getSite().getId());
        model.addAttribute("channel", parameters.getChannel().getId());
        return "reports/analogue_results";
    }

    @PostMapping(path="raw-analogue-events/generate/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public void generate(@PathVariable("id") Integer reportId, Model model) {

        // This should be done in its own thread
        analogueReportService.generateReport(reportId);

    }

    @GetMapping(path="raw-analogue-events/generated/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public boolean isGenerated(@PathVariable("id") Integer reportId, Model model) {
        return analogueReportService.reportCSVExists(reportId);
    }

    @PostMapping(path="raw-analogue-events/generated")
    public @ResponseBody List<Boolean> areGenerated(@RequestBody List<Integer> reportIds, Model model) {
        return analogueReportService.reportCSVsExist(reportIds);
    }

    @GetMapping("/raw-analogue-events/csv/{id}")
    public @ResponseBody void downloadCsv(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        analogueReportService.downloadCsv(id, response);
    }

    @GetMapping("all")
    public @ResponseBody List<RawAnalogueEventsReportParametersVO> getReports() {
        return analogueReportService.getReports();
    }

    @GetMapping("{id}")
    public @ResponseBody  RawAnalogueEventsReportParametersVO getReport(@PathVariable("id") Integer reportId) {
        return analogueReportService.getReport(reportId);
    }

    @GetMapping("analogue_results")
    public String viewReport(@RequestParam("reportId") Integer reportId, Model model) {
        RawAnalogueEventsReportParametersVO report = analogueReportService.getReport(reportId);
        model.addAttribute("site", report.getSite().getId());
        model.addAttribute("channel", report.getChannel().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("dateFrom", df.format(new Date(report.getFrom())));
        model.addAttribute("dateTo", df.format(new Date(report.getTo())));

        model.addAttribute("reportId", report.getId());

        // Redirect to thymeleaf page
        return "reports/analogue_results";
    }

}
