package com.smithies.analoguedataexporter.entities;

public enum ChannelType
{
    ANALOGUE("A"),
    CAPTURE("C"),
    DIGITAL("D"),
    INFO("I"),
    SSI_ALARM("S"),
    TEXT("T");

    private final String discriminatorValue;

    ChannelType(String discriminatorValue)
    {
        this.discriminatorValue = discriminatorValue;
    }

    public static ChannelType fromDiscriminatorValue(String discriminatorValue)
    {
        for (ChannelType channelType : values()) {
            if (discriminatorValue.equals(channelType.discriminatorValue)) {
                return channelType;
            }
        }
        return null;
    }
}
