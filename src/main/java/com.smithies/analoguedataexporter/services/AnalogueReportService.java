package com.smithies.analoguedataexporter.services;

import com.opencsv.CSVWriter;
import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import com.smithies.analoguedataexporter.entities.AnalogueReportParameters;
import com.smithies.analoguedataexporter.repositories.AnalogueEventRepository;
import com.smithies.analoguedataexporter.repositories.AnalogueReportParametersRepository;
import com.smithies.analoguedataexporter.repositories.ChannelRepository;
import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
class AnalogueReportService implements IAnalogueReportService {

    @Autowired
    private AnalogueReportParametersRepository parametersRepository;

    @Autowired
    private InterlockingRepository siteRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private AnalogueEventRepository analogueRepository;

    @Override
    public void downloadCsv(Integer id, HttpServletResponse response) throws IOException {
//        AnalogueReportParameters parameters = parametersRepository.findOptionalOne(id).orElseThrow(() -> {
//            return new RuntimeException("Could not find the report parameters with id " + id);
//        });
        AnalogueReportParameters parameters = new AnalogueReportParameters(siteRepository.findOne(new Short("1")),
                channelRepository.findOne(10244700), 1452781798937l, 1452781798937l);

        // For now just return the saved parameters in a csv

        // Set response headers
        response.setHeader("Content-Type", "text/csv");
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.csv", id));

        // Get response output stream
        OutputStream outputStream = response.getOutputStream();

        // Create CSV writer
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream));

        // Writer header
        writer.writeNext(new String[]{"Site", "Channel", "Date (UTC)", "Value"});

        List<AnalogueEvent> events = analogueRepository.findByChannel_IdAndDateBetween(new Integer(10244700),
                parameters.getDateFrom(), parameters.getDateTo());



        events.forEach(e -> {
            writer.writeNext(new String[]{parameters.getInterlocking().getName(), e.getChannel().getName(),
                    String.valueOf(e.getDate()), String.valueOf(e.getValue())});
        });

        // Close writer
        writer.close();

        // Close output stream
        outputStream.close();;
    }

//    @Override
//    public void generateReport(UUID id) {
//
//    }

    @Override
    @Transactional
    public Integer saveParameters(Short siteId, Integer channelId, long from, long to) {
        AnalogueReportParameters params = new AnalogueReportParameters(siteRepository.findOne(siteId),
                channelRepository.findOne(channelId), from, to);
        AnalogueReportParameters saved = parametersRepository.save(params);
        return saved.getId();
    }
}
