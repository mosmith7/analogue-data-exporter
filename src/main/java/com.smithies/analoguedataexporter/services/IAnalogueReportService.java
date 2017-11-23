package com.smithies.analoguedataexporter.services;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface IAnalogueReportService {
    void downloadCsv(UUID id, HttpServletResponse response);

    UUID saveParameters(Short siteId, Integer channelId, long from, long to);
}
