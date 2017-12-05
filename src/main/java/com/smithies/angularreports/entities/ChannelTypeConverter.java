package com.smithies.angularreports.entities;

import javax.persistence.AttributeConverter;


//@Converter(autoApply = true)
public class ChannelTypeConverter implements AttributeConverter<ChannelType, String>
{
    @Override
    public String convertToDatabaseColumn(ChannelType attribute)
    {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public ChannelType convertToEntityAttribute(String dbData)
    {
        return ChannelType.fromDiscriminatorValue(dbData);
    }
}