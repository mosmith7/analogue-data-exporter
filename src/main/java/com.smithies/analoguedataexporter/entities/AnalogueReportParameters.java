package com.smithies.analoguedataexporter.entities;

import javax.persistence.*;

@Entity
@Table(name="report_parameters_analogue")
public class AnalogueReportParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name="interlocking")
    private Interlocking interlocking;

    @ManyToOne(optional = false)
    @JoinColumn(name="channel")
    private Channel channel;

    @Column
    private long dateFrom;

    @Column
    private long dateTo;

    public AnalogueReportParameters() {
    }

    public AnalogueReportParameters(Interlocking interlocking, Channel channel, long dateFrom, long dateTo) {
        this.interlocking = interlocking;
        this.channel = channel;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Integer getId() {
        return id;
    }

    public Interlocking getInterlocking() {
        return interlocking;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }
}
