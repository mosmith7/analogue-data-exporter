package com.smithies.analoguedataexporter.services;

import com.smithies.analoguedataexporter.entities.AnalogueReportParameters;
import com.smithies.analoguedataexporter.repositories.AnalogueReportParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ScheduledOldReportCleanup {

    private static final long ONE_DAY_IN_MILLIS = 1000 * 60 * 60 * 24l;
    private static final long ONE_MONTH_IN_MILLIS = ONE_DAY_IN_MILLIS * 30;

    @Autowired
    private AnalogueReportParametersRepository repo;

    @Autowired
    private IAnalogueReportService service;

    @Scheduled(fixedRate= ONE_DAY_IN_MILLIS)
    public void cleanupOldReports() {
        // Every day, remove all csv files which are older than one month. Users can regenerate them if needed.
        long threshold = System.currentTimeMillis() - ONE_MONTH_IN_MILLIS;
        List<AnalogueReportParameters> reports = repo.findByDateCreatedBefore(threshold);
        // Delete the files with these names
        reports.forEach(report -> service.deleteCSVFile(report));
    }
}
