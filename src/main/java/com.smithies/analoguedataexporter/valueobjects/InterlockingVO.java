package com.smithies.analoguedataexporter.valueobjects;

import java.util.List;

public class InterlockingVO {

    private Short id;
    private String name;
    private String code;
    private String postcode;
    private List<String> channels;

    public InterlockingVO(Short id, String name, String code, String postcode, List<String> channels) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.postcode = postcode;
        this.channels = channels;
    }

    public Short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<String> getChannels() {
        return channels;
    }
}
