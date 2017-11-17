package com.smithies.analoguedataexporter.entities;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "interlocking")
public class Interlocking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @javax.validation.constraints.NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 6)
    @Nullable
    private String code;

    @Size(max = 255)
    private String controlArea;
    @Size(max = 255)
    private String osRef;
    @Size(max = 255)
    private String postcode;

    @Size(max = 10000)
    private String engineerDetails;

    @javax.validation.constraints.NotNull
    private TimeZone timeZone = TimeZone.getDefault();

    @Column(nullable = false)
    private boolean adjustForDaylightSavings = true;

    @Column(nullable = false)
    private boolean inUse = true;

    @Column(nullable = false)
    private Long dateDisabled = 0L;

    // Do not use @JoinColumn as this is already defined on the Channel object
    @OneToMany(mappedBy = "interlocking")
    private List<Channel> channels = new ArrayList<>();

    public Interlocking() {
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getControlArea() {
        return controlArea;
    }

    public void setControlArea(String controlArea) {
        this.controlArea = controlArea;
    }

    public String getOsRef() {
        return osRef;
    }

    public void setOsRef(String osRef) {
        this.osRef = osRef;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEngineerDetails() {
        return engineerDetails;
    }

    public void setEngineerDetails(String engineerDetails) {
        this.engineerDetails = engineerDetails;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isAdjustForDaylightSavings() {
        return adjustForDaylightSavings;
    }

    public void setAdjustForDaylightSavings(boolean adjustForDaylightSavings) {
        this.adjustForDaylightSavings = adjustForDaylightSavings;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public Long getDateDisabled() {
        return dateDisabled;
    }

    public void setDateDisabled(Long dateDisabled) {
        this.dateDisabled = dateDisabled;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
