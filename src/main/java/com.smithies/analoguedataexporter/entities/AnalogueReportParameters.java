package com.smithies.analoguedataexporter.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="report_parameters_aips")
public class AnalogueReportParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name="interlocking")
    private Interlocking interlocking;

    @ManyToOne(optional = false)
    @JoinColumn(name="channel")
    private Channel channel;

    @Column(nullable=false)
    private long from;

    @Column(nullable=false)
    private long to;

    public AnalogueReportParameters() {
    }

    public AnalogueReportParameters(Interlocking interlocking, Channel channel, long from, long to) {
        this.interlocking = interlocking;
        this.channel = channel;
        this.from = from;
        this.to = to;
    }

    public UUID getId() {
        return id;
    }

    public Interlocking getInterlocking() {
        return interlocking;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }
}
