package com.smithies.angularreports.factory;

import com.smithies.angularreports.entities.Channel;
import com.smithies.angularreports.valueobjects.ChannelVO;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ChannelVOFactory {

    public static ChannelVO generateVO(final Channel channel) {
        return new ChannelVO(channel.getId(), channel.getName());
    }

    public static List<ChannelVO> generateVO(List<Channel> channels) {
        return channels.stream().map(c -> generateVO(c)).collect(Collectors.toList());
    }
}
