package com.smithies.angularreports.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParametersRequest {

    private Short siteId;
    private Integer channelId;
    private String from;
    private String to;

    @JsonCreator
    public ParametersRequest(@JsonProperty("siteId") Short siteId,
                             @JsonProperty("channelId") Integer channelId,
                             @JsonProperty("from") String from,
                             @JsonProperty("to") String to) {
        this.siteId = siteId;
        this.channelId = channelId;
        this.from = from;
        this.to = to;
    }

    public Short getSiteId() {
        return siteId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
