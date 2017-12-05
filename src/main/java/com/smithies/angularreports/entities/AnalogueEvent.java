package com.smithies.angularreports.entities;

import javax.persistence.*;

@Entity
@Table(name = "event_analogue")
public class AnalogueEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false)
    @javax.validation.constraints.NotNull
    @JoinColumn(name="channel")
    private Channel channel;

    @Column(nullable = false)
    private long date; // The time the event happened, as a unix timestamp in milliseconds

    @Column(nullable = true)
    private float value;

    // this is set to the maximum value by default so that it will show up on the graph,
    // and set to a more correct value later by the significance populator task
    @Column(nullable = false)
    private short significance = Short.MAX_VALUE;

    public AnalogueEvent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public short getSignificance() {
        return significance;
    }

    public void setSignificance(short significance) {
        this.significance = significance;
    }
}
