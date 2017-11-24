package com.smithies.analoguedataexporter.services;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IAnalogueReportService {
    void downloadCsv(Integer id, HttpServletResponse response) throws IOException;

    Integer saveParameters(Short siteId, Integer channelId, long from, long to);
}
