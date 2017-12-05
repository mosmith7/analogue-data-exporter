package com.smithies.angularreports.valueobjects;

public class InterlockingVO {

    private Short id;
    private String name;
    private String code;
    private String postcode;

    public InterlockingVO(Short id, String name, String code, String postcode) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.postcode = postcode;
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
}
