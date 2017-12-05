package com.smithies.angularreports.valueobjects;

public class RawAnalogueEventsReportParametersVO {

    private Integer id;
    private InterlockingVO site;
    private ChannelVO channel;
    private long from;
    private long to;

    public RawAnalogueEventsReportParametersVO(Integer id, InterlockingVO site, ChannelVO channel, long from, long to) {
        this.id = id;
        this.site = site;
        this.channel = channel;
        this.from = from;
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InterlockingVO getSite() {
        return site;
    }

    public void setSite(InterlockingVO site) {
        this.site = site;
    }

    public ChannelVO getChannel() {
        return channel;
    }

    public void setChannel(ChannelVO channel) {
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
