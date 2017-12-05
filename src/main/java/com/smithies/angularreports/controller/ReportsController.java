package com.smithies.angularreports.controller;

import com.smithies.angularreports.model.ParametersRequest;
import com.smithies.angularreports.repositories.InterlockingRepository;
import com.smithies.angularreports.services.IAnalogueReportService;
import com.smithies.angularreports.valueobjects.RawAnalogueEventsReportParametersVO;
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
public class ReportsController {

    @Autowired
    private InterlockingRepository siteRepo;

    @Autowired
    private IAnalogueReportService analogueReportService;

    @PostMapping(path="parameters")
    public @ResponseBody
    Integer parameters(@RequestBody ParametersRequest request, Model model) {
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

    @GetMapping(path="parameters/{id}")
    public @ResponseBody RawAnalogueEventsReportParametersVO parameters(@PathVariable("id") Integer id, Model model) {
        return analogueReportService.getReport(id);
    }

    @PostMapping(path="generate/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public void generate(@PathVariable("id") Integer reportId, Model model) {

        // This should be done in its own thread
        analogueReportService.generateReport(reportId);

    }

    @GetMapping(path="generated/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public @ResponseBody boolean isGenerated(@PathVariable("id") Integer reportId, Model model) {
        return analogueReportService.reportCSVExists(reportId);
    }

    @PostMapping(path="generated")
    public @ResponseBody List<Boolean> areGenerated(@RequestBody List<Integer> reportIds, Model model) {
        return analogueReportService.reportCSVsExist(reportIds);
    }

    @GetMapping("/csv/{id}")
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

}