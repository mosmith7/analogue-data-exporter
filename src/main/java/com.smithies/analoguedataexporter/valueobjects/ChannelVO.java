package com.smithies.analoguedataexporter.valueobjects;

public class ChannelVO {

    private Integer id;
    private String name;

    public ChannelVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
