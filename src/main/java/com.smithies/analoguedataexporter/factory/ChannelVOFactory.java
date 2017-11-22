package com.smithies.analoguedataexporter.factory;

import com.smithies.analoguedataexporter.entities.Channel;
import com.smithies.analoguedataexporter.valueobjects.ChannelVO;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ChannelVOFactory {

    public static ChannelVO generateVO(final Channel channel) {
        return new ChannelVO(channel.getId(), channel.getName(),
                InterlockingVOFactory.generateVO(channel.getInterlocking()));
    }

    public static List<ChannelVO> generateVO(List<Channel> channels) {
        return channels.stream().map(c -> generateVO(c)).collect(Collectors.toList());
    }
}
