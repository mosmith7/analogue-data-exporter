package com.smithies.analoguedataexporter.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import com.smithies.analoguedataexporter.entities.AnalogueReportParameters;
import com.smithies.analoguedataexporter.factory.AnalogueReportVOFactory;
import com.smithies.analoguedataexporter.repositories.AnalogueEventRepository;
import com.smithies.analoguedataexporter.repositories.AnalogueReportParametersRepository;
import com.smithies.analoguedataexporter.repositories.ChannelRepository;
import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import com.smithies.analoguedataexporter.valueobjects.RawAnalogueEventsReportParametersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Iterator;
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

    @Autowired
    private AnalogueReportParametersRepository reportRepository;

    @Override
    public void downloadCsv(Integer id, HttpServletResponse response) throws IOException {

        // Set response headers
        response.setHeader("Content-Type", "text/csv");
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.csv", id));

        // Get response output stream
        OutputStream outputStream = response.getOutputStream();

        // Create CSV writer
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream));

        // Stream file to user
        String filename = idToFilename(id);
        CSVReader reader = null;
        reader = new CSVReader(new FileReader(filename));
        Iterator<String[]> iterator = reader.iterator();
        while (iterator.hasNext()) {
            writer.writeNext(iterator.next());
        }

        // Close writer
        writer.close();

        // Close output stream
        outputStream.close();
    }

    @Override
    public void generateReport(Integer id) {
        AnalogueReportParameters parameters = parametersRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Could not find the report parameters with id " + id);
        });

//        List<AnalogueEvent> events = analogueRepository.findByChannel_IdAndDateBetween(parameters.getChannel().getId(),
//                parameters.getDateFrom(), parameters.getDateTo());

        List<AnalogueEvent> events = analogueRepository.findByChannel_IdAndDateBetween(new Integer(10244700),
                1452781798937l, 1452781811527l);

        if (events.isEmpty()) {
            throw new RuntimeException("No data found for the parameters selected");
        }

        String filename = idToFilename(id);
        try {
            FileWriter fileWriter = new FileWriter(filename);
            CSVWriter writer = new CSVWriter(fileWriter);

            // Writer header
            writer.writeNext(new String[]{"Site", "Channel", "Date (UTC)", "Value"});

            events.forEach(e -> {
                writer.writeNext(new String[]{parameters.getInterlocking().getName(), e.getChannel().getName(),
                        String.valueOf(e.getDate()), String.valueOf(e.getValue())});
            });

            // Close writer
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public Integer saveParameters(Short siteId, Integer channelId, long from, long to) {
        AnalogueReportParameters params = new AnalogueReportParameters(siteRepository.findOne(siteId),
                channelRepository.findOne(channelId), from, to);
        AnalogueReportParameters saved = parametersRepository.save(params);
        return saved.getId();
    }

    @Override
    public List<RawAnalogueEventsReportParametersVO> getReports() {
        Iterable<AnalogueReportParameters> reports = reportRepository.findAll();
        return AnalogueReportVOFactory.generateVO(reports);
    }

    @Override
    public RawAnalogueEventsReportParametersVO getReport(Integer id) {
        AnalogueReportParameters report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find report with id " + id));
        return AnalogueReportVOFactory.generateVO(report);

    }

    private String idToFilename(Integer id) {
        return "analogue_report" + id + ".csv";
    }
}
