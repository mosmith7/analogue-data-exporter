package com.smithies.analoguedataexporter.factory;

import com.smithies.analoguedataexporter.entities.AnalogueReportParameters;
import com.smithies.analoguedataexporter.valueobjects.RawAnalogueEventsReportParametersVO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AnalogueReportVOFactory {

    public static RawAnalogueEventsReportParametersVO generateVO(final AnalogueReportParameters reports) {
        return new RawAnalogueEventsReportParametersVO(reports.getId(), InterlockingVOFactory.generateVO(reports.getInterlocking()),
                ChannelVOFactory.generateVO(reports.getChannel()), reports.getDateFrom(), reports.getDateTo());
    }

    public static List<RawAnalogueEventsReportParametersVO> generateVO(Iterable<AnalogueReportParameters> reports) {
        return StreamSupport.stream(reports.spliterator(), false).map(c -> generateVO(c)).collect(Collectors.toList());
    }
}
