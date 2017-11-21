package com.smithies.analoguedataexporter.valueobjects;

public class RawAnalogueEventsReportParametersVO {

    private String site;
    private String channel;
    private long from;
    private long to;

    public RawAnalogueEventsReportParametersVO(String site, String channel, long from, long to) {
        this.site = site;
        this.channel = channel;
        this.from = from;
        this.to = to;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
}
