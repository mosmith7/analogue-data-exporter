package com.smithies.angularreports.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.smithies.angularreports.entities.AnalogueEvent;
import com.smithies.angularreports.entities.AnalogueReportParameters;
import com.smithies.angularreports.factory.AnalogueReportVOFactory;
import com.smithies.angularreports.repositories.AnalogueEventRepository;
import com.smithies.angularreports.repositories.AnalogueReportParametersRepository;
import com.smithies.angularreports.repositories.ChannelRepository;
import com.smithies.angularreports.repositories.InterlockingRepository;
import com.smithies.angularreports.valueobjects.RawAnalogueEventsReportParametersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Transactional
    public Integer saveParameters(Short siteId, Integer channelId, long from, long to) {
        AnalogueReportParameters parameters = new AnalogueReportParameters(siteRepository.findOne(siteId),
                channelRepository.findOne(channelId), from, to);

        // Only save if there are some valid events for the parameters provided
        Long totalEvents = analogueRepository.countByChannel_IdAndDateBetween(parameters.getChannel().getId(),
                parameters.getDateFrom(), parameters.getDateTo());
        if (totalEvents==0) {
            throw new RuntimeException("No data found for the parameters selected");
        }

        AnalogueReportParameters saved = parametersRepository.save(parameters);
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

    @Override
    public boolean reportCSVExists(Integer id) {
        AnalogueReportParameters parameters = parametersRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Could not find the report parameters with id " + id);
        });

        if (new File(parametersToFilename(parameters)).exists()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Boolean> reportCSVsExist(List<Integer> ids) {
        return ids.stream().map(id -> reportCSVExists(id)).collect(Collectors.toList());
    }

    @Override
    public void downloadCsv(Integer id, HttpServletResponse response) throws IOException {

        // Set response headers
        response.setHeader("Content-Type", "text/csv");
        // TODO: Make filename here be the report name
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.csv", id));

        // Get response output stream
        OutputStream outputStream = response.getOutputStream();

        // Create CSV writer
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream));

        // Stream file to user
        AnalogueReportParameters parameters = parametersRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Could not find the report parameters with id " + id);
        });
        String filename = parametersToFilename(parameters);
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
    @Transactional(readOnly = true)
    public void generateReport(Integer id) {
        AnalogueReportParameters parameters = parametersRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Could not find the report parameters with id " + id);
        });

        // If file exists, we may still want to update it, so do generate it again if asked. There may be more data
//        // If csv already exists, don't bother creating it again
//        if (new File(parametersToFilename(parameters)).exists()) {
//            // File already exists so just return
//            return;
//        }

        if (analogueRepository.countByChannel_IdAndDateBetween(parameters.getChannel().getId(),
                parameters.getDateFrom(), parameters.getDateTo())==0) {
            throw new RuntimeException("No data found for the parameters selected");
        }

        String filename = parametersToFilename(parameters);
        try {
            FileWriter fileWriter = new FileWriter(filename);
            CSVWriter writer = new CSVWriter(fileWriter);

            // Writer header
            writer.writeNext(new String[]{"Site", "Channel", "Date (UTC)", "Date (Human Readable)", "Value"});

            // Get a stream from the repository and write the streamed data into a file. To avoid bringing lots of data into memory
            Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try (Stream<AnalogueEvent> events = analogueRepository.findByChannel_IdAndDateBetween(parameters.getChannel().getId(),
                    parameters.getDateFrom(), parameters.getDateTo())) {
                events.forEach(e -> {
                    writer.writeNext(new String[]{parameters.getInterlocking().getName(), e.getChannel().getName(),
                            String.valueOf(e.getDate()), format.format(new Date(e.getDate())), String.valueOf(e.getValue())});
                });
            }


            // Close writer
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCSVFile(AnalogueReportParameters parameters) {
        try {
            Files.deleteIfExists(Paths.get(parametersToFilename(parameters)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parametersToFilename(AnalogueReportParameters parameters) {
        return "analogue_report_" + parameters.getInterlocking().getId() + "_" + parameters.getChannel().getId()
                + "_" + parameters.getDateFrom() + "-" + parameters.getDateTo() + ".csv";
    }
}
