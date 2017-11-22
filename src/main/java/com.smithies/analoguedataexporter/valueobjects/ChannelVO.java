package com.smithies.analoguedataexporter.valueobjects;

public class ChannelVO {

    private Integer id;
    private String name;
    private InterlockingVO site;

    public ChannelVO(Integer id, String name, InterlockingVO site) {
        this.id = id;
        this.name = name;
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InterlockingVO getSite() {
        return site;
    }
}
