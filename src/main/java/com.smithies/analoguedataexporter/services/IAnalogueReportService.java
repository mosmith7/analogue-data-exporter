package com.smithies.analoguedataexporter.services;

import com.smithies.analoguedataexporter.valueobjects.RawAnalogueEventsReportParametersVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IAnalogueReportService {
    void downloadCsv(Integer id, HttpServletResponse response) throws IOException;

    void generateReport(Integer id);

    Integer saveParameters(Short siteId, Integer channelId, long from, long to);

    List<RawAnalogueEventsReportParametersVO> getReports();

    RawAnalogueEventsReportParametersVO getReport(Integer id);
}
