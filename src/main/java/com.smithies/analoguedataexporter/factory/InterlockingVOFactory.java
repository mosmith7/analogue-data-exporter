package com.smithies.analoguedataexporter.factory;

import com.smithies.analoguedataexporter.entities.Channel;
import com.smithies.analoguedataexporter.entities.Interlocking;
import com.smithies.analoguedataexporter.valueobjects.InterlockingVO;

import java.util.List;
import java.util.stream.Collectors;

public abstract class InterlockingVOFactory {

    public static InterlockingVO generateVO(final Interlocking site) {
        return new InterlockingVO(site.getId(), site.getName(), site.getCode(), site.getPostcode(),
                getChannelNames(site.getChannels()));
    }

    public static List<InterlockingVO> generateVO(List<Interlocking> sites) {
        return sites.stream().map(s -> generateVO(s)).collect(Collectors.toList());
    }

    private static List<String> getChannelNames(List<Channel> channels) {
        return channels.stream().map(c -> c.getName()).collect(Collectors.toList());
    }
}
