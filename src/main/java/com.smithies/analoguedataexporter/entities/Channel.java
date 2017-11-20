package com.smithies.analoguedataexporter.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="interlocking")
    private Interlocking interlocking;

    @Column(nullable = false)
    @javax.validation.constraints.NotNull
    @Size(min = 1, max = 255)
    private String name;

    // Set to true if this represents a Centrix internal channel rather than one that comes
    // from an external source
    @Column(nullable = false)
    private boolean internal = false;

    // Archived channels do not show up in most of the channel selectors in Centrix, but
    // they can be un-archived by site admins on the Channels tab to bring them back
    @Column(nullable = false)
    private boolean archived = false;

    @Column(name = "channel_type", nullable = false)
    // Point hibernate to a converter class so it knows how to convert from SQL varchar to ChannelType
    @Convert(converter = ChannelTypeConverter.class)
    private ChannelType channelType;

    protected Channel()
    {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Interlocking getInterlocking() {
        return interlocking;
    }

    public void setInterlocking(Interlocking interlocking) {
        this.interlocking = interlocking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }
}
